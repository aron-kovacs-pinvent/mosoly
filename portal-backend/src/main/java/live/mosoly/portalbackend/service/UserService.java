package live.mosoly.portalbackend.service;

import live.mosoly.portalbackend.auth.security.JwtTokenGenerator;
import live.mosoly.portalbackend.model.RegInfoObject;
import live.mosoly.portalbackend.model.Mentoree;
import live.mosoly.portalbackend.model.MentoreeKey;
import live.mosoly.portalbackend.model.User;
import live.mosoly.portalbackend.model.dto.*;
import live.mosoly.portalbackend.repository.MentoreeRepository;
import live.mosoly.portalbackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MentoreeRepository mentoreeRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private HashingService hashingService;

    @Autowired
    private JwtTokenGenerator generator;

    public String checkUser(String attr) {

        log.info("Checking user: {}", attr);

        User u = userRepository.findByEmail(attr);
        if (u == null){
            u = userRepository.findByAccount(attr);
            if(u == null){
                log.info("Not found");
                return null;
            }
            return "BLOCKCHAINID";
        }
        return "EMAIL";
    }

    @Transactional
    public RestResponse create(RegInfoObject regInfoObject) {

        log.info("Registering user: {}", regInfoObject);

        User user = userRepository.findByAccount(regInfoObject.getAccount());

        if(user == null && regInfoObject.getEmail() != null){
            user = userRepository.findByEmail(regInfoObject.getEmail().toLowerCase());
        }
        if(user == null){
            user = userRepository.findByInviteUrlHash(hashingService.hash(regInfoObject));
        }

        if (user != null) {
            log.info("Already existing user, skipping");
            return RestResponse.failed();
        }

        user = new User(regInfoObject);


        user.setPasswordDigest(bCryptPasswordEncoder.encode(regInfoObject.getPassword()));
        user.setInviteUrlHash(hashingService.hash(regInfoObject));

        user = userRepository.saveAndFlush(user);

        handleMentorHash(user.getId(), regInfoObject.getMentor1Hash());
        handleMentorHash(user.getId(), regInfoObject.getMentor2Hash());

        return RestResponse.builder().success(true).build();

    }

    private void handleMentorHash(Integer userId, String hash){
        if (!StringUtils.isEmpty(hash)) {
            User mentor = userRepository.findByInviteUrlHash(hash);
            if (mentor != null) {
                log.info("Registering {} as a mentor for the new user", mentor.getId());
                MentoreeKey key = new MentoreeKey(userId, mentor.getId());
                Mentoree mentoree = new Mentoree(key, new Date());
                mentoreeRepository.saveAndFlush(mentoree);
            }
        }
    }

    public void addMentor(String mentorHash){
        User user = getCurrentUser();
        User mentor = userRepository.findByInviteUrlHash(mentorHash);
        if(user.equals(mentor)){
            throw new UnsupportedOperationException();
        }
        log.info("Registering '{}' as mentor for the current user '{}'", mentor.getId(), user.getId());
        MentoreeKey key = new MentoreeKey(user.getId(), mentor.getId());
        Mentoree mentoree = mentoreeRepository.findOne(key);
        if(mentoree != null){
            log.info("These two users are not linked as mentor-mentoree");
            return;
        }
        mentoree = new Mentoree(key, new Date());
        mentoreeRepository.saveAndFlush(mentoree);
    }

    public User getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    public void changeNickName(String customName, String ethId) {
        User user = userRepository.findByAccount(ethId);
        if(user == null){
            return;
        }
        MentoreeKey key = new MentoreeKey(user.getId(), getCurrentUser().getId());
        Mentoree mentoree = mentoreeRepository.findOne(key);
        mentoree.setCustomNameForMentor(customName);
        mentoreeRepository.saveAndFlush(mentoree);
    }

    public void validate(String ethId) {

        log.info("Validating user: {}", ethId);

        User user = userRepository.findByAccount(ethId);
        MentoreeKey key = new MentoreeKey(user.getId(), getCurrentUser().getId());
        Mentoree mentoree = mentoreeRepository.findOne(key);

        if(mentoree == null){
            mentoree = new Mentoree(key, new Date());
            mentoree = mentoreeRepository.saveAndFlush(mentoree);
        }

        mentoree.setValidated(true);
        mentoreeRepository.saveAndFlush(mentoree);

        List<Mentoree> mentorees = mentoreeRepository.findByKey_UserId(user.getId());

        boolean allValidated = mentorees.stream()
                .allMatch(mentoree1 -> Boolean.TRUE.equals(mentoree1.getValidated()));

        if(allValidated && mentorees.size() > 1) {

            log.info("Second validation for the user, setting to validated");

            user.setPhotoIdType(null);
            user.setPhotoIdNumber(null);
            user.setValidated(true);
            userRepository.saveAndFlush(user);
        }

    }

    private List<MentoreeInterface> getMentorees(User currentUser) {
        List<Mentoree> mentorees = mentoreeRepository.findByKey_MentorId(currentUser.getId());

        if(CollectionUtils.isEmpty(mentorees)){
            return Collections.emptyList();
        }

        return mentorees.stream()
                .map(mentoree -> {
                    User user = userRepository.findOne(mentoree.getKey().getUserId());

                    MentoreeDTO mentoreeDTO = null;

                    if(mentoree.getCustomNameForMentor() != null){
                        mentoreeDTO = MentoreeDTO.builder()
                                .account(user.getAccount())
                                .customNameForMentor(mentoree.getCustomNameForMentor())
                                .validated(mentoree.getValidated())
                                .mentorshipStarted(mentoree.getMentorshipStarted())
                                .build();
                    }
                    else {
                        mentoreeDTO = MentoreeDTO.builder()
                                .userId(user.getId())
                                .account(user.getAccount())
                                .email(user.getEmail())
                                .customNameForMentor(user.getName())
                                .validated(mentoree.getValidated())
                                .photoIdType(user.getPhotoIdType())
                                .photoIdNumber(user.getPhotoIdNumber())
                                .build();
                        mentoreeDTO.setMentorshipStarted(mentoree.getMentorshipStarted());
                    }

                    return mentoreeDTO;
                }).collect(Collectors.toList());

    }

    public List<MentoreeInterface> getForSyncMentorees(User currentUser) {
        List<Mentoree> mentorees = mentoreeRepository.findByKey_MentorId(currentUser.getId());

        if(CollectionUtils.isEmpty(mentorees)){
            return Collections.emptyList();
        }

        return mentorees.stream()
                .map(mentoree -> {
                    User user = userRepository.findOne(mentoree.getKey().getUserId());
                    SyncMentoreeDTO mentoreeDTO = SyncMentoreeDTO.builder()
                            .userId(user.getId())
                            .account(user.getAccount())
                            .customNameForMentor(Boolean.TRUE.equals(user.getValidated()) ? mentoree.getCustomNameForMentor() : user.getName())
                            .validated(mentoree.getValidated())
                            .build();
                    mentoreeDTO.setMentorshipStarted(mentoree.getMentorshipStarted());
                    return mentoreeDTO;
                }).collect(Collectors.toList());

    }

    public UserDTO transform(User user, Boolean jwt){

        int validationCount = 0;

        if(!Boolean.TRUE.equals(user.getValidated())){
            List<Mentoree> validations = mentoreeRepository.findByKey_UserId(user.getId());
            for(Mentoree m : validations){
                if(Boolean.TRUE.equals(m.getValidated())){
                    validationCount++;
                }
            }
        }

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getAccount(),
                user.getRole(),
                user.getEmail(),
                user.getPhotoIdType(),
                user.getPhotoIdNumber(),
                user.getTokenBalance(),
                user.getValidated() ? user.getInviteUrlHash(): null,
                user.getValidated(),
                validationCount,
                user.getJoined(),
                jwt ? Base64Utils.encodeToString(generator.generateToken(user).getBytes(StandardCharsets.UTF_8)): null,
                jwt ? getForSyncMentorees(user) : getMentorees(user)
        );
    }

    public SyncUserDTO transform(User user){

        return new SyncUserDTO(
                user.getId(),
                user.getAccount(),
                user.getTokenBalance(),
                user.getInviteUrlHash(),
                user.getValidated(),
                user.getJoined(),
                user.getUpdatedAt(),
                user.getCreatedAt(),
                getForSyncMentorees(user)
        );
    }

}

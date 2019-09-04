package live.mosoly.portalbackend.controller;

import live.mosoly.portalbackend.model.User;
import live.mosoly.portalbackend.model.dto.RestResponse;
import live.mosoly.portalbackend.repository.UserRepository;
import live.mosoly.portalbackend.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MentorController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/api/add-mentor")
    @PreAuthorize("!hasRole('ROLE_APP')")
    public RestResponse addMentor(@RequestBody Payload payload) {

        userService.addMentor(payload.getHash());
        User mentorUser = userRepository.findByInviteUrlHash(payload.getHash());

        return RestResponse
                .builder()
                .success(true)
                .name(mentorUser.getName())
                .build();
    }

    @GetMapping(value = "/api/mentors")
    @PreAuthorize("hasRole('ROLE_APP')")
    public RestResponse getMentorName(@RequestParam("hash") String hash) {

        User mentorUser = userRepository.findByInviteUrlHash(hash);

        if(mentorUser == null){
            return RestResponse.failed();
        }

        return RestResponse
                .builder()
                .success(true)
                .name(mentorUser.getName())
                .build();
    }

    @Data
    private static class Payload {
        private String hash;
    }

}

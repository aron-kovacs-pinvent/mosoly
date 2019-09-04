package live.mosoly.portalbackend.controller;

import live.mosoly.portalbackend.model.User;
import live.mosoly.portalbackend.model.dto.UserDTO;
import live.mosoly.portalbackend.repository.UserRepository;
import live.mosoly.portalbackend.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@PreAuthorize("!hasRole('ROLE_APP')")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/api/login")
    public UserDTO login(@RequestBody LoginPayload payload, HttpServletResponse response) throws IOException {

        User user = null;

        if(AttrType.BLOCKCHAINID.equals(payload.getAttr())){
            user = userRepository.findByAccount(payload.getPrincipal());
        } else if(AttrType.EMAIL.equals(payload.getAttr())){
            user = userRepository.findByEmail(payload.getPrincipal());
        }

        if(user == null){
            log.debug("User not found: {}", payload.getPrincipal());
        }

        if(user != null && bCryptPasswordEncoder.matches(payload.getCredential(), user.getPasswordDigest())){
            return userService.transform(user, true);
        }

        response.sendError(401, "Invalid credentials");
        return null;
    }

    private enum AttrType {
        BLOCKCHAINID,EMAIL
    }

    @Data
    private static class LoginPayload {
        private String principal;
        private String credential;
        private AttrType attr;
    }

}

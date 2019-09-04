package live.mosoly.portalbackend.controller;

import live.mosoly.portalbackend.model.User;
import live.mosoly.portalbackend.model.dto.RestResponse;
import live.mosoly.portalbackend.model.dto.UserDTO;
import live.mosoly.portalbackend.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/users")
@PreAuthorize("!hasRole('ROLE_APP')")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public UserDTO getCurrentUser(){
        User user = userService.getCurrentUser();
        return userService.transform(user, true);
    }

    @GetMapping(value = "/check")
    public RestResponse checkAttribute(@RequestParam("attr") String loginAttr) {

        String attribute = userService.checkUser(loginAttr);

        if(attribute == null){
            return RestResponse.failed();
        }

        return RestResponse
                .builder()
                .success(true)
                .attribute(attribute.toUpperCase())
                .build();
    }

    @PostMapping(value = "/validate")
    public RestResponse  validate(@RequestBody Payload payload) {
        userService.validate(payload.getAccount());
        return RestResponse.builder().success(true).build();
    }

    @PostMapping(value = "/nickname")
    public RestResponse changeNickName(@RequestBody Payload payload) {
        userService.changeNickName(payload.getValue(), payload.getAccount());
        return RestResponse.builder().success(true).build();
    }

    @Data
    private static class Payload {
        private String account;
        private String value;
    }

}

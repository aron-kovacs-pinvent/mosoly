package live.mosoly.portalbackend.controller;

import live.mosoly.portalbackend.model.RegInfoObject;
import live.mosoly.portalbackend.model.dto.RestResponse;
import live.mosoly.portalbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ROLE_APP')")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/api/register")
    public RestResponse register(@RequestBody RegInfoObject regInfoObject) {
        return userService.create(regInfoObject);
    }


}

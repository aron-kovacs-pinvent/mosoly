package live.mosoly.portalbackend.controller;

import live.mosoly.portalbackend.model.User;
import live.mosoly.portalbackend.model.dto.SyncUserDTO;
import live.mosoly.portalbackend.model.dto.UserDTO;
import live.mosoly.portalbackend.model.project.Project;
import live.mosoly.portalbackend.repository.ProjectRepository;
import live.mosoly.portalbackend.repository.UserRepository;
import live.mosoly.portalbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@RestController
@PreAuthorize("hasRole('ROLE_APP')")
public class SyncController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/api/users/sync")
    public List<SyncUserDTO> list(@RequestParam(value = "updatedAfter") Long seconds) {
        List<User> users = userRepository.findByUpdatedAtIsAfter(new Date(seconds * 1000));
        return users.stream().map(user -> userService.transform(user)).collect(Collectors.toList());
    }

    @GetMapping("/api/projects/sync")
    public List<Project> projects(@RequestParam(value = "updatedAfter") Long seconds) {
        Date updatedAfter = new Date(seconds * 1000);
        return projectRepository.findByUpdatedAtIsAfter(updatedAfter);
    }

}

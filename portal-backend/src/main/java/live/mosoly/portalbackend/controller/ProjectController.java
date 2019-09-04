package live.mosoly.portalbackend.controller;

import live.mosoly.portalbackend.model.User;
import live.mosoly.portalbackend.model.dto.RestResponse;
import live.mosoly.portalbackend.model.project.*;
import live.mosoly.portalbackend.repository.*;
import live.mosoly.portalbackend.service.ProjectService;
import live.mosoly.portalbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/projects")
@PreAuthorize("!hasRole('ROLE_APP')")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @PostMapping
    public RestResponse create(@RequestBody ProjectDTO dto) {
        return projectService.create(dto);
    }

    @GetMapping("/get/{id}")
    public Project projects(@PathVariable("id") String id) {
        return projectRepository.findOne(id);
    }

    @GetMapping("/mine")
    public List<Project> projects() {
        User appUser = userService.getCurrentUser();
        return projectRepository.findByCreatedBy(appUser.getId());
    }

    @GetMapping("/supported")
    public Set<Project> supported() {
        return projectService.supported();
    }

    @PostMapping("/changeTokensOnProject")
    public RestResponse support(@RequestBody ProjectSupportDTO support) {
        return projectService.addSupport(support);
    }

    @PostMapping("/boost")
    public RestResponse boost(@RequestBody Map<String, String> payload) {

        Project project = projectRepository.findOne(payload.get("projectId"));

        if(project == null){
            return RestResponse.failed();
        }

        User user = userService.getCurrentUser();

        String action = payload.get("action");

        if(user.getTokenBalance() < 1L){
            return RestResponse.failed();
        }

        return projectService.changeTokensOnProject(action, user, project);

    }
}

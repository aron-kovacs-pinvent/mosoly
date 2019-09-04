package live.mosoly.portalbackend.service;

import live.mosoly.portalbackend.model.User;
import live.mosoly.portalbackend.model.dto.RestResponse;
import live.mosoly.portalbackend.model.project.*;
import live.mosoly.portalbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectTokenSupportRepository projectTokenSupportRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectSupportRepository projectSupportRepository;

    @Autowired
    private ProjectToolsNeededRepository projectToolsNeededRepository;

    @Autowired
    private ProjectPeopleNeededRepository projectPeopleNeededRepository;

    public RestResponse create(ProjectDTO dto){
        User user = userRepository.getCurrentUser();

        Project project;

        if (dto.getId() == null) {

            project = new Project(
                    dto.getPicture(),
                    dto.getName(),
                    dto.getDescription(),
                    dto.getEstimatedImpact(),
                    dto.getPlace(),
                    dto.getMinimumToGoLive(),
                    user.getId(),
                    dto.getPlannedDate(),
                    null,
                    null,
                    dto.getLongitude(),
                    dto.getLatitude()
            );

        } else {


            project = projectRepository.findOne(dto.getId());

            if (!user.getId().equals(project.getCreatedBy())) {
                return RestResponse.failed();
            }

            project.setPicture(dto.getPicture());
            project.setName(dto.getName());
            project.setDescription(dto.getDescription());
            project.setEstimatedImpact(dto.getEstimatedImpact());
            project.setPlace(dto.getPlace());
            project.setMinimumToGoLive(dto.getMinimumToGoLive());
            project.setPlannedDate(dto.getPlannedDate());
            project.setLongitude(dto.getLongitude());
            project.setLatitude(dto.getLatitude());
        }

        project = this.projectRepository.saveAndFlush(project);

        for(ProjectToolsNeeded t : dto.getToolsNeeded()){
            t.setProject(project);
        }

        for(ProjectPeopleNeeded p : dto.getPeopleNeeded()){
            p.setProject(project);
        }

        projectToolsNeededRepository.save(dto.getToolsNeeded());
        projectPeopleNeededRepository.save(dto.getPeopleNeeded());

        return RestResponse.builder().success(true).build();
    }

    public Set<Project> supported(){
        User user = userRepository.getCurrentUser();

        return projectSupportRepository.findByUserId(user.getId())
                .stream()
                .map(projectSupport -> {
                    ProjectPeopleNeeded people = projectSupport.getPeopleNeeded();

                    if (people != null) {
                        return people.getProject();
                    }
                    ProjectToolsNeeded tool = projectSupport.getToolNeeded();
                    if (tool != null) {
                        return tool.getProject();
                    }

                    return null;

                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public RestResponse addSupport(ProjectSupportDTO support){
        User user = userRepository.getCurrentUser();

        if(!StringUtils.isEmpty(support.getToolNeededId())){
            ProjectToolsNeeded t = projectToolsNeededRepository.findOne(support.getToolNeededId());
            ProjectSupport support1 = new ProjectSupport();
            support1.setUserId(user.getId());
            support1.setToolNeeded(t);

            projectSupportRepository.saveAndFlush(support1);
            return RestResponse.builder().success(true).build();
        }
        if(!StringUtils.isEmpty(support.getPeopleNeededId())){
            ProjectPeopleNeeded p = projectPeopleNeededRepository.findOne(support.getPeopleNeededId());
            ProjectSupport support1 = new ProjectSupport();
            support1.setUserId(user.getId());
            support1.setPeopleNeeded(p);

            projectSupportRepository.saveAndFlush(support1);
            return RestResponse.builder().success(true).build();
        }

        return RestResponse.failed();
    }

    public RestResponse changeTokensOnProject(String action, User user, Project project){
        if(action.equals("PLUS")){
            return plus(user, project);

        } else if(action.equals("MINUS")){
            return minus(user, project);
        }
        return RestResponse.failed();
    }

    @Transactional(rollbackOn = Exception.class)
    public RestResponse plus(User user, Project project){

        user.setTokenBalance(user.getTokenBalance() - 1);
        ProjectTokenSupport tokenSupport = new ProjectTokenSupport();
        tokenSupport.setUserId(user.getId());
        tokenSupport.setProject(project);
        user = userRepository.saveAndFlush(user);
        projectTokenSupportRepository.saveAndFlush(tokenSupport);

        return RestResponse
                .builder()
                .tokens(user.getTokenBalance())
                .build();

    }

    @Transactional(rollbackOn = Exception.class)
    public RestResponse minus(User user, Project project){

        List<ProjectTokenSupport> tokens = projectTokenSupportRepository.findByProjectAndUserId(project, user.getId());
        if(CollectionUtils.isEmpty(tokens)){
            return RestResponse.failed();
        }
        projectTokenSupportRepository.delete(tokens.get(0));
        user.setTokenBalance(user.getTokenBalance() + 1L);
        user = userRepository.save(user);

        return RestResponse
                .builder()
                .tokens(user.getTokenBalance())
                .build();
    }

}

package live.mosoly.portalbackend.repository;

import live.mosoly.portalbackend.model.project.Project;
import live.mosoly.portalbackend.model.project.ProjectTokenSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTokenSupportRepository extends JpaRepository<ProjectTokenSupport, String> {

    List<ProjectTokenSupport> findByProjectAndUserId(Project project, Integer userId);
}

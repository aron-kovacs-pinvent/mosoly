package live.mosoly.portalbackend.repository;

import live.mosoly.portalbackend.model.project.ProjectToolsNeeded;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectToolsNeededRepository extends JpaRepository<ProjectToolsNeeded, String> {

}

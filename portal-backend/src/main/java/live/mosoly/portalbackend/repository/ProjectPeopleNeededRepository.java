package live.mosoly.portalbackend.repository;

import live.mosoly.portalbackend.model.project.ProjectPeopleNeeded;
import live.mosoly.portalbackend.model.project.ProjectSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectPeopleNeededRepository extends JpaRepository<ProjectPeopleNeeded, String > {

}

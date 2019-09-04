package live.mosoly.portalbackend.repository;

import live.mosoly.portalbackend.model.project.ProjectSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectSupportRepository extends JpaRepository<ProjectSupport, String > {

    List<ProjectSupport> findByUserId(Integer userId);

}

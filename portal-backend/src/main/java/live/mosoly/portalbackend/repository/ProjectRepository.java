package live.mosoly.portalbackend.repository;

import live.mosoly.portalbackend.model.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

    List<Project> findByUpdatedAtIsAfter(Date date);

    List<Project> findByCreatedBy(Integer userId);

    List<Project> findByLive(Boolean live);
}

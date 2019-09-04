package live.mosoly.portalbackend.repository;

import live.mosoly.portalbackend.model.Mentoree;
import live.mosoly.portalbackend.model.MentoreeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentoreeRepository extends JpaRepository<Mentoree, MentoreeKey> {

    List<Mentoree> findByKey_MentorId(Integer mentorId);

    List<Mentoree> findByKey_UserId(Integer userId);

}

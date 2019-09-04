package live.mosoly.portalbackend.repository;

import live.mosoly.portalbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "Select u From User u where u.account = ?#{principal.username}")
    User getCurrentUser();

    User findByEmail(String email);

    User findByAccount(String account);

    User findByInviteUrlHash(String inviteUrlHash);

    List<User> findByUpdatedAtIsAfter(Date date);

}

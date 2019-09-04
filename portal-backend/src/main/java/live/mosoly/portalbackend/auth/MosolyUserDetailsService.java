package live.mosoly.portalbackend.auth;

import live.mosoly.portalbackend.model.User;
import live.mosoly.portalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Transactional
public class MosolyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);

        if (user == null) {
            user = userRepository.findByAccount(username);

            if(user == null) {
                throw new UsernameNotFoundException(
                        "No user found with username: " + username);
            }
        }
        return  new org.springframework.security.core.userdetails.User
                (username,
                        user.getPasswordDigest(),
                        true,
                        true,
                        true,
                        true,
                        Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }

}

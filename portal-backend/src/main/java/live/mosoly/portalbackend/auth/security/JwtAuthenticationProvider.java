package live.mosoly.portalbackend.auth.security;

import live.mosoly.portalbackend.model.User;
import live.mosoly.portalbackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtTokenValidator jwtTokenValidator;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws JwtAuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;

        String token = jwtAuthenticationToken.getToken();

        String account = jwtTokenValidator.parseToken(token);

        if (account == null) {
            throw new JwtAuthenticationException("Invalid JWT token");
        }

        if (account.equals("APPUSER")){
            return new AuthenticatedUser(
                    "APPUSER",
                    "APPUSER",
                    token,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_APP")));
        }

        User user = userRepository.findByAccount(account);

        if(user == null){
            throw new JwtAuthenticationException("User not found for JWT token");
        }

        if(Boolean.TRUE.equals(user.getAccountLocked())){
            throw new JwtAuthenticationException("Account disabled");
        }

        log.trace("Parsed user from request JWT: {}", user);

        return new AuthenticatedUser(
                user.getAccount(),
                user.getEmail(),
                token,
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }

}

package live.mosoly.portalbackend.auth.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Holds the info for a authenticated user (Principal)
 */
@Data
public class AuthenticatedUser implements UserDetails {

    private final String id;
    private final String email;
    private final String token;
    private final Collection<? extends GrantedAuthority> authorities;

    AuthenticatedUser(String id, String email, String token, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    public boolean isAdmin(){
        for(GrantedAuthority authority : this.authorities){
            if(authority.getAuthority().equals("ROLE_ADMIN")){
                return true;
            }
        }
        return false;
    }
}


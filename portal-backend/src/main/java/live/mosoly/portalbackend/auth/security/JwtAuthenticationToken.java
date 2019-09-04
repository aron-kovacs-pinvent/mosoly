package live.mosoly.portalbackend.auth.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String token;

    JwtAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}

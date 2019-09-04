package live.mosoly.portalbackend.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import live.mosoly.portalbackend.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenGenerator {

    @Value("${jwt.secret}")
    private String jwtSecretKey = "SE0Bp@Ks5HdISeC1g]N9?W";

    @PostConstruct
    public void checkSecretKey(){
        Assert.hasLength(jwtSecretKey, "JWT secret key must be provided.");
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param user the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(User user) {

        Claims claims = Jwts.claims()
                .setSubject(user.getAccount())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)));

        claims.put("userId", user.getId());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, jwtSecretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public static void main(String[] args) {
        User user = new User();
        user.setAccount("APPUSER");
        System.out.println(Base64Utils.encodeToUrlSafeString(new JwtTokenGenerator().generateToken(user).getBytes()));
    }

}

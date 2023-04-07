package weblux.jwt.auth.utils;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtService {

    private static final int TOKEN_EXPIRATION_DATE = 7;
    private final SecretKey key;
    private final JwtParser parser;


    public JwtService(@Value("${jwt_secret}") String key) {
        this.key = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        parser = Jwts.parserBuilder().setSigningKey(this.key).build();
    }

    public String generate(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(TOKEN_EXPIRATION_DATE, ChronoUnit.DAYS)))
                .signWith(key)
                .compact();
    }

    public String getUserName(String token) {
        try {
            return parser
                    .parseClaimsJws(token)
                    .getBody().getSubject();
        } catch (Exception e) {
            return "";
        }

    }

    public boolean isValid(String token, String username) {
        try {
            return username.equalsIgnoreCase(getUserName(token)) &&
                    parser
                            .parseClaimsJws(token)
                            .getBody()
                            .getExpiration()
                            .after(Date.from(Instant.now()));
        } catch (Exception e) {
            return false;
        }
    }
}

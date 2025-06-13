package technofutur.backend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import technofutur.backend.dal.entities.security.User;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;
    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;

    public JwtUtil(JwtConfig jwtConfig) {
        Key key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
        this.jwtBuilder = Jwts.builder().signWith(key);
        this.jwtParser = Jwts.parser().setSigningKey(key).build();
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(User user) {
        return jwtBuilder
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .claim("login", user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 1000L))
                .compact();
    }

    public Claims getClaims(String token) { return jwtParser.parseClaimsJws(token).getBody(); }

    public String getUsername(String token) { return getClaims(token).getSubject(); }

    public Long getId(String token) { return getClaims(token).get("id", Long.class); }

    public boolean validateToken(String token) {

        Claims claims = getClaims(token);
        Date now = new Date();
        return now.after(claims.getIssuedAt()) && now.before(claims.getExpiration());
    }
}

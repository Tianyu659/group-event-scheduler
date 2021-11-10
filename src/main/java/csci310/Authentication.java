package csci310;

import csci310.exception.RequestException;
import csci310.models.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.sql.SQLException;

public class Authentication {
    public static final Authentication instance = new Authentication(Configuration.load());
    private final Key secret;

    public Authentication(Configuration configuration) {
        String secret = configuration.value("authentication.secret");
        this.secret = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String key(User user) {
        return Jwts.builder().setSubject(String.valueOf(user.getId())).signWith(this.secret).compact();
    }

    public User user(String token) throws SQLException {
        String subject = Jwts.parserBuilder().setSigningKey(this.secret).build().parseClaimsJws(token).getBody().getSubject();
        return Database.load().users.dao().queryForId(Integer.valueOf(subject));
    }

    public User authenticate(HttpServletRequest request) throws RequestException {
        String token = request.getHeader("Authorization");
        if (token != null) {
            try {
                User user = RequestException.wrap(() -> this.user(token), "unable to access database!");
                if (user != null) {
                    return user;
                } else {
                    throw new RequestException(HttpServletResponse.SC_UNAUTHORIZED, "invalid authentication!");
                }
            } catch (JwtException exception) {
                throw new RequestException(HttpServletResponse.SC_BAD_REQUEST, "invalid JWT format!");
            }
        }

        throw new RequestException(HttpServletResponse.SC_UNAUTHORIZED, "user authentication is required!");
    }

    public static Authentication get() {
        return Authentication.instance;
    }
}

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
import java.util.Map;

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
        String[] token = request.getParameterMap().get("Authorization");
        if (token != null && token.length == 1) {
            try {
                User user = RequestException.wrap(() -> this.user(token[0]), "unable to access database!");
                if (user != null) {
                    return user;
                } else {
                    throw new RequestException(HttpServletResponse.SC_UNAUTHORIZED, "invalid authentication!");
                }
            } catch (JwtException exception) {
                throw new RequestException(HttpServletResponse.SC_BAD_REQUEST, "invalid JWT format!");
            }
        } else {
            if(token == null) System.err.println("token is null");
            else {
                for(final String s : token) System.err.print(s+' ');
                System.err.println();
            }
        }

        throw new RequestException(HttpServletResponse.SC_UNAUTHORIZED, "user authentication is required!");
    }

    public static Authentication get() {
        return Authentication.instance;
    }
}

package csci310;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import csci310.exception.NotImplementedError;
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
        throw new NotImplementedError();
    }

    public String key(User user) {
        throw new NotImplementedError();
    }

    public User user(String token) throws SQLException {
        throw new NotImplementedError();
    }

    public User authenticate(HttpServletRequest request) throws RequestException {
        throw new NotImplementedError();
    }

    public static Authentication get() {
        throw new NotImplementedError();
    }
}

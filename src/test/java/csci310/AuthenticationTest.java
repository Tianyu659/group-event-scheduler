package csci310;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import csci310.exception.RequestException;
import csci310.mock.MockHttpServletRequestBuilder;
import csci310.models.User;
import csci310.models.UserTest;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class AuthenticationTest {
    private static JdbcConnectionSource connectionSource;
    private static Dao<User, Integer> userDao;
    private static User user;

    @BeforeClass
    public static void setupTestDatabase() throws SQLException {
        AuthenticationTest.connectionSource = Database.connect();
        TableUtils.dropTable(AuthenticationTest.connectionSource, User.class, true);
        TableUtils.createTable(AuthenticationTest.connectionSource, User.class);
        AuthenticationTest.userDao = DaoManager.createDao(AuthenticationTest.connectionSource, User.class);
        AuthenticationTest.user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        AuthenticationTest.userDao.create(user);
    }

    @Test
    public void testUser() throws SQLException {
        String token = Authentication.get().key(AuthenticationTest.user);
        User user = Authentication.get().user(token);
        Assert.assertEquals(AuthenticationTest.user.getId(), user.getId());
    }

    @Test
    public void testAuthenticate() throws RequestException {
        String token = Authentication.get().key(AuthenticationTest.user);
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .build();

        User user = Authentication.get().authenticate(request);
        Assert.assertEquals(AuthenticationTest.user.getId(), user.getId());
    }

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        TableUtils.dropTable(AuthenticationTest.connectionSource, User.class, true);
    }
}

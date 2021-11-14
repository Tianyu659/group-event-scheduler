package csci310;

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
    private static Database database;
    private static User user;

    @BeforeClass
    public static void setupTestDatabase() throws SQLException {
        Configuration.load("test");
        database = Database.load();
        user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        database.users.dao().create(user);
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
        database.users.clear();
    }
}

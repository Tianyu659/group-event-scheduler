package csci310.servlets;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import csci310.Database;
import csci310.mock.MockHttpServletRequestBuilder;
import csci310.mock.MockHttpServletResponseTarget;
import csci310.models.User;
import csci310.models.UserTest;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

public class SessionServletTest {
    private static JdbcConnectionSource connectionSource;
    private static Dao<User, Integer> userDao;
    private static User user;

    @BeforeClass
    public static void setupTestDatabase() throws SQLException {
        SessionServletTest.connectionSource = Database.connect();
        TableUtils.dropTable(SessionServletTest.connectionSource, User.class, true);
        TableUtils.createTable(SessionServletTest.connectionSource, User.class);
        SessionServletTest.userDao = DaoManager.createDao(SessionServletTest.connectionSource, User.class);
        SessionServletTest.user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        SessionServletTest.userDao.create(user);
    }

    @Test
    public void testLogin() throws IOException {
        SessionServlet servlet = new SessionServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"ttrojan\", \"password\": \"secret\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(201));

        Assert.assertTrue(response.getBody().toString().trim().startsWith("{\"token\": \""));
    }

    @Test
    public void testLoginIncorrect() throws IOException {
        SessionServlet servlet = new SessionServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"ttrojan\", \"password\": \"invalid\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(400));

        Assert.assertEquals("{\"error\": \"incorrect credentials!\"}", response.getBody().toString().trim());
    }

    @Test
    public void testLoginInvalidUsername() throws IOException {
        SessionServlet servlet = new SessionServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"ttrojan\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(400));

        Assert.assertEquals("{\"error\": \"form data did not conform to schema!\"}", response.getBody().toString().trim());
    }

    @Test
    public void testLoginInvalidPassword() throws IOException {
        SessionServlet servlet = new SessionServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"password\": \"secret\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(400));

        Assert.assertEquals("{\"error\": \"form data did not conform to schema!\"}", response.getBody().toString().trim());
    }

    @Test
    public void testLoginUnknown() throws IOException {
        SessionServlet servlet = new SessionServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"nkim\", \"password\": \"secret\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(400));

        Assert.assertEquals("{\"error\": \"incorrect credentials!\"}", response.getBody().toString().trim());
    }

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        TableUtils.dropTable(SessionServletTest.connectionSource, User.class, true);
    }
}

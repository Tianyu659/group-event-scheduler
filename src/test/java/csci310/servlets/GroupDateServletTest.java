package csci310.servlets;

import csci310.Authentication;
import csci310.Configuration;
import csci310.Database;
import csci310.Resources;
import csci310.mock.MockHttpServletRequestBuilder;
import csci310.mock.MockHttpServletResponseTarget;
import csci310.models.GroupDate;
import csci310.models.GroupDateTest;
import csci310.models.User;
import csci310.models.UserTest;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class GroupDateServletTest {
    private static Database database;
    private static User user;
    private static String token;
    private static GroupDate groupDate;

    @BeforeClass
    public static void setupTestDatabase() throws SQLException {
        database = new Database(Configuration.load("test"));
        user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        database.users.dao().create(user);
        groupDate = GroupDateTest.createGroupDate(user, "Test Group Date", "Super fun event!");
        database.groupDates.dao().create(groupDate);
        token = Authentication.get().key(user);
    }

    @Test
    public void testDoGet() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_OK));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoGetOne() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withPathInfo("/1")
                .withHeader("Authorization", token)
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_OK));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoPost() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withBody(Resources.read("json/GroupDateServletTest.testDoPost.json"))
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(HttpServletResponse.SC_CREATED));
        Assert.assertNotNull(response);
    }

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        database.users.clear();
    }
}

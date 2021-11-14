package csci310.servlets;

import csci310.Authentication;
import csci310.Configuration;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UserServletTest {
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
    public void testDoGet() throws IOException {
        String token = Authentication.get().key(UserServletTest.user);

        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_OK));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoGetInvalid() throws IOException {
        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", "ayayayaya")
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_BAD_REQUEST));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoGetMissing() throws IOException {
        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", null)
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_UNAUTHORIZED));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoGetNonexistent() throws IOException {
        User user = UserTest.createUser("nkim", "secret", "Noah", "Kim");
        String token = Authentication.get().key(user);

        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_UNAUTHORIZED));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoPost() throws IOException {
        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"nkim\", \"password\": \"secret\", \"firstName\": \"Noah\", \"lastName\": \"Kim\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(HttpServletResponse.SC_CREATED));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoPostExisting() throws IOException {
        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"ttrojan\", \"password\": \"secret\", \"firstName\": \"Tommy\", \"lastName\": \"Trojan\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(HttpServletResponse.SC_BAD_REQUEST));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoPostInvalid() throws IOException {
        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"ttrojan\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(HttpServletResponse.SC_BAD_REQUEST));
        Assert.assertNotNull(response);
    }

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        database.users.clear();
    }
}

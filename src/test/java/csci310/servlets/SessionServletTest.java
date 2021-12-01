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
import java.io.IOException;
import java.sql.SQLException;

public class SessionServletTest {
    private static Database database;
    private static String token;

    @BeforeClass
    public static void setupDatabase() throws SQLException {
        Configuration.load("test");
        database = Database.load(true);
        User user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        database.users.dao().create(user);
        token = Authentication.get().key(user);
    }

    @Test
    public void testDoGet() throws IOException {
        SessionServlet servlet = new SessionServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(200));
    }

    @Test
    public void testDoGetUnauthorized() throws IOException {
        SessionServlet servlet = new SessionServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", null)
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(401));
    }

    @Test
    public void testDoPost() throws IOException {
        SessionServlet servlet = new SessionServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"ttrojan\", \"password\": \"secret\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(201));

        Assert.assertTrue(response.getBody().toString().trim().contains("\"token\":"));
    }

    @Test
    public void testDoPostIncorrect() throws IOException {
        SessionServlet servlet = new SessionServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"ttrojan\", \"password\": \"invalid\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(400));

        Assert.assertTrue(response.getBody().toString().contains("\"error\":"));
    }

    @Test
    public void testDoPostInvalidUsername() throws IOException {
        SessionServlet servlet = new SessionServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"ttrojan\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(400));

        Assert.assertTrue(response.getBody().toString().contains("\"error\":"));
    }

    @Test
    public void testDoPostInvalidPassword() throws IOException {
        SessionServlet servlet = new SessionServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"password\": \"secret\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(400));

        Assert.assertTrue(response.getBody().toString().contains("\"error\":"));
    }

    @Test
    public void testDoPostUnknown() throws IOException {
        SessionServlet servlet = new SessionServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"nkim\", \"password\": \"secret\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(400));

        Assert.assertTrue(response.getBody().toString().contains("\"error\":"));
    }

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        database.drop();
    }
}

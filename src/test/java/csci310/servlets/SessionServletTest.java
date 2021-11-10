package csci310.servlets;

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

    @BeforeClass
    public static void setupDatabase() throws SQLException {
        database = new Database(Configuration.load("test"));
        User user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        database.users.dao().create(user);
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

        Assert.assertEquals("{\"error\": \"incorrect credentials!\"}", response.getBody().toString().trim());
    }

    @Test
    public void testDoPostInvalidUsername() throws IOException {
        SessionServlet servlet = new SessionServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"ttrojan\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(400));

        Assert.assertEquals("{\"error\": \"form data did not conform to schema!\"}", response.getBody().toString().trim());
    }

    @Test
    public void testDoPostInvalidPassword() throws IOException {
        SessionServlet servlet = new SessionServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"password\": \"secret\"}")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(400));

        Assert.assertEquals("{\"error\": \"form data did not conform to schema!\"}", response.getBody().toString().trim());
    }

    @Test
    public void testDoPostUnknown() throws IOException {
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
        database.users.clear();
    }
}

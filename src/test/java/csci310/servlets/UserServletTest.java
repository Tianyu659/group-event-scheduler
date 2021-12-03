package csci310.servlets;

import csci310.Authentication;
import csci310.Configuration;
import csci310.Database;
import csci310.mock.MockHttpServletRequestBuilder;
import csci310.mock.MockHttpServletResponseTarget;
import csci310.models.Blackout;
import csci310.models.Block;
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
    private static String token;

    @BeforeClass
    public static void setupTestDatabase() throws SQLException {
        Configuration.load("test");
        database = Database.load(true);
        user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        database.users.dao().create(user);
        token = Authentication.get().key(user);
    }

    @Test
    public void testDoGet() throws IOException {
        String token = Authentication.get().key(UserServletTest.user);

        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/")
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
                .withPathInfo("/")
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
                .withPathInfo("/")
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
                .withPathInfo("/")
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_UNAUTHORIZED));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoPost() throws IOException {
        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"nkim2\", \"password\": \"secret\", \"firstName\": \"Noah\", \"lastName\": \"Kim\"}")
                .withPathInfo("/")
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
                .withPathInfo("/")
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
                .withPathInfo("/")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(HttpServletResponse.SC_BAD_REQUEST));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoPostBlock() throws IOException, SQLException {
        UserServlet servlet = new UserServlet();
        int beforeSize = user.getBlocked().size();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"ttrojan\"}")
                .withHeader("Authorization", token)
                .withPathInfo("/" + user.getId() + "/blocks/")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(HttpServletResponse.SC_CREATED));
        Assert.assertNotNull(response);
        Assert.assertEquals(beforeSize + 1, user.getBlocked().size());
    }

    @Test
    public void testDoPostBlockNonexistent() throws IOException, SQLException {
        UserServlet servlet = new UserServlet();
        int beforeSize = user.getBlocked().size();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"nobody\"}")
                .withHeader("Authorization", token)
                .withPathInfo("/" + user.getId() + "/blocks/")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(HttpServletResponse.SC_NO_CONTENT));
        Assert.assertNotNull(response);
        Assert.assertEquals(beforeSize, user.getBlocked().size());
    }

    @Test
    public void testDoPostBlackout() throws IOException, SQLException {
        UserServlet servlet = new UserServlet();
        int beforeSize = user.getBlackouts().size();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"start\": 1, \"end\": 2}")
                .withHeader("Authorization", token)
                .withPathInfo("/" + user.getId() + "/blackouts/")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(HttpServletResponse.SC_CREATED));
        Assert.assertNotNull(response);
        Assert.assertEquals(beforeSize + 1, user.getBlackouts().size());
    }

    @Test
    public void testDoPostNotFound() throws IOException {
        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"start\": 1, \"end\": 2}")
                .withHeader("Authorization", token)
                .withPathInfo("/" + user.getId() + "/")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
    }

    @Test
    public void testDoPostNotFoundNested() throws IOException {
        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"start\": 1, \"end\": 2}")
                .withHeader("Authorization", token)
                .withPathInfo("/" + user.getId() + "/blah/")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
    }

    @Test
    public void testDoDeleteBlock() throws SQLException, IOException {
        User user = new User();
        database.users.dao().create(user);
        Block block = new Block();
        block.setCreator(user);
        block.setBlocked(user);
        database.blocks.dao().create(block);
        int size = user.getBlocked().size();

        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/" + user.getId() + "/blocks/" + block.getId())
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doDelete(request, response.bind(HttpServletResponse.SC_NO_CONTENT));
        Assert.assertEquals(size - 1, user.getBlocked().size());
    }

    @Test
    public void testDoDeleteBlackout() throws SQLException, IOException {
        User user = new User();
        database.users.dao().create(user);
        Blackout blackout = new Blackout();
        blackout.setCreator(user);
        database.blackouts.dao().create(blackout);
        int size = user.getBlackouts().size();

        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/" + user.getId() + "/blackouts/" + blackout.getId())
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doDelete(request, response.bind(HttpServletResponse.SC_NO_CONTENT));
        Assert.assertEquals(size - 1, user.getBlackouts().size());
    }

    @Test
    public void testDoDeleteNotFound() throws IOException {
        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/1/blah/")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doDelete(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
    }

    @Test
    public void testDoDeleteNotFoundNested() throws IOException {
        UserServlet servlet = new UserServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/1/blah/1")
                .build();
        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doDelete(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
    }

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        database.users.drop();
    }
}

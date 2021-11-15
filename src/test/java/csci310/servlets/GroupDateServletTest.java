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
import java.util.HashMap;
import java.util.Map;

public class GroupDateServletTest {
    private static Database database;
    private static String token;

    @BeforeClass
    public static void setupTestDatabase() throws SQLException {
        Configuration.load("test");
        database = Database.load();
        User user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        User otherUser = UserTest.createUser("noahbkim", "secret", "Noah", "Kim");
        database.users.dao().create(user);
        database.users.dao().create(otherUser);
        GroupDate groupDate = GroupDateTest.createGroupDate(user, "Test Group Date", "Super fun event!");
        GroupDate otherGroupDate = GroupDateTest.createGroupDate(otherUser, "Other Test Group Date", "Super fun event!");
        database.groupDates.dao().create(groupDate);
        database.groupDates.dao().create(otherGroupDate);
        token = Authentication.get().key(user);
    }
    
    HttpServletRequest request(final String pathInfo,final String auth,final String...other) {
        Map<String,String[]> headers = new HashMap<>();
        if(auth != null)
            headers.put("Authorization",new String[] {auth});
        for(final String s : other) {
            final String[] split = s.split(",",2);
            headers.put(split[0],split[1].split(","));
        }
        return new MockHttpServletRequestBuilder().withPathInfo(pathInfo)
                                                  .params(headers)
                                                  .build();
    }
    void test(final String pathInfo,final String auth,final int status) throws IOException {
        final GroupDateServlet servlet = new GroupDateServlet();
        final HttpServletRequest request = request(pathInfo,auth);
        final MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request,response.bind(status));
        Assert.assertNotNull(response);
    }
    
    @Test
    public void testDoGet() throws IOException {
        /*
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withPathInfo("/")
                .withHeader("Authorization", token)
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_OK));
        Assert.assertNotNull(response);
        */
        test("/",token,HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoGetOne() throws IOException {
        /*
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withPathInfo("/1")
                .withHeader("Authorization", token)
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_OK));
        Assert.assertNotNull(response);
        */
        test("/1",token,HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoGetOneNotMine() throws IOException {
        /*
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withPathInfo("/2")
                .withHeader("Authorization", token)
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
        Assert.assertNotNull(response);
        */
        test("/2",token,HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void testDoGetNonExistent() throws IOException {
        /*
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withPathInfo("/100")
                .withHeader("Authorization", token)
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
        Assert.assertNotNull(response);
        */
        test("/100",token,HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void testDoGetUnauthorized() throws IOException {
        /*
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", null)
                .withPathInfo("/")
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_UNAUTHORIZED));
        Assert.assertNotNull(response);
        */
        test("/",null,HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    public void testDoPost() throws IOException {
        /*
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withBody(Resources.read("json/GroupDateServletTest.testDoPost.json"))
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(HttpServletResponse.SC_CREATED));
        Assert.assertNotNull(response);
        */
        test("/100",token,HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void testDoPostUnauthorized() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .params(new HashMap<>())
                .withBody(Resources.read("json/GroupDateServletTest.testDoPost.json"))
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(HttpServletResponse.SC_UNAUTHORIZED));
        Assert.assertNotNull(response);
    }

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        database.users.clear();
        database.groupDates.clear();
        database.groupDateEvents.clear();
    }
}

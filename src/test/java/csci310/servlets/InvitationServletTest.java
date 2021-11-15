package csci310.servlets;

import csci310.Authentication;
import csci310.Configuration;
import csci310.Database;
import csci310.mock.MockHttpServletRequestBuilder;
import csci310.mock.MockHttpServletResponseTarget;
import csci310.models.*;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class InvitationServletTest {
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
        database.groupDates.dao().create(groupDate);
        Invitation invitation = new Invitation();
        invitation.setGroupDate(groupDate);
        invitation.setUser(otherUser);
        database.invitations.dao().create(invitation);
        token = Authentication.get().key(otherUser);
    }

    @Test
    public void testDoGet() throws IOException {
        InvitationServlet servlet = new InvitationServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withPathInfo("/")
                .withHeader("Authorization", token)
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_OK));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoPost() throws IOException {
        InvitationServlet servlet = new InvitationServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withPathInfo("/1/responses/")
                .withHeader("Authorization", token)
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(HttpServletResponse.SC_CREATED));
        Assert.assertNotNull(response);
    }

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        database.users.clear();
        database.groupDates.clear();
        database.invitations.clear();
    }
}

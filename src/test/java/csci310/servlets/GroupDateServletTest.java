package csci310.servlets;

import csci310.Authentication;
import csci310.Configuration;
import csci310.Database;
import csci310.Resources;
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

public class GroupDateServletTest {
    private static Database database;
    private static String token;
    private static User user;
    private static GroupDate groupDate;

    @BeforeClass
    public static void setupTestDatabase() throws SQLException {
        Configuration.load("test");
        database = Database.load(true);
        user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        User otherUser = UserTest.createUser("noahbkim", "secret", "Noah", "Kim");
        database.users.dao().create(user);
        database.users.dao().create(otherUser);
        groupDate = GroupDateTest.createGroupDate(user, "Test Group Date", "Super fun event!");
        GroupDate otherGroupDate = GroupDateTest.createGroupDate(otherUser, "Other Test Group Date", "Super fun event!");
        database.groupDates.dao().create(groupDate);
        database.groupDates.dao().create(otherGroupDate);
        Invitation invitation = new Invitation();
        invitation.setGroupDate(groupDate);
        invitation.setUser(user);
        database.invitations.dao().create(invitation);
        token = Authentication.get().key(user);
    }

    @Test
    public void testDoGet() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withPathInfo("/")
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

//    @Test
//    public void testDoGetOneNotMine() throws IOException {
//        GroupDateServlet servlet = new GroupDateServlet();
//        HttpServletRequest request = new MockHttpServletRequestBuilder()
//                .withPathInfo("/2")
//                .withHeader("Authorization", token)
//                .build();
//
//        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
//        servlet.doGet(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
//        Assert.assertNotNull(response);
//    }

//    @Test
//    public void testDoGetOneNotMineInvitations() throws IOException {
//        GroupDateServlet servlet = new GroupDateServlet();
//        HttpServletRequest request = new MockHttpServletRequestBuilder()
//                .withPathInfo("/2/invitations/")
//                .withHeader("Authorization", token)
//                .build();
//
//        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
//        servlet.doGet(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
//        Assert.assertNotNull(response);
//    }

    @Test
    public void testDoGetNonExistent() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withPathInfo("/100")
                .withHeader("Authorization", token)
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoGetInvitations() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withPathInfo("/1/invitations/")
                .withHeader("Authorization", token)
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_OK));
        Assert.assertNotNull(response);
    }


    @Test
    public void testDoGetUnauthorized() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", null)
                .withPathInfo("/")
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_UNAUTHORIZED));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoGetNotFound() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/1/invitations/blah/")
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoGetNonExistentInvitations() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/100/invitations/")
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doGet(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
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

    @Test
    public void testDoPostUnauthorized() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", null)
                .withBody(Resources.read("json/GroupDateServletTest.testDoPost.json"))
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPost(request, response.bind(HttpServletResponse.SC_UNAUTHORIZED));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoDelete() throws SQLException, IOException {
        GroupDate groupDate = new GroupDate();
        database.groupDates.dao().create(groupDate);

        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/" + groupDate.getId())
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doDelete(request, response.bind(HttpServletResponse.SC_NO_CONTENT));
        Assert.assertNotNull(response);
        Assert.assertNull(database.groupDates.dao().queryForId(groupDate.getId()));
    }

    @Test
    public void testDoDeleteNonExistent() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/")
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doDelete(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoDeleteInvitation() throws SQLException, IOException {
        GroupDate groupDate = new GroupDate();
        database.groupDates.dao().create(groupDate);
        Invitation invitation = new Invitation();
        invitation.setGroupDate(groupDate);
        invitation.setUser(user);
        database.invitations.dao().create(invitation);
        InvitationResponse invitationResponse = new InvitationResponse();
        invitationResponse.setInvitation(invitation);
        invitationResponse.setAccepted(true);
        database.invitationResponses.dao().create(invitationResponse);

        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/" + groupDate.getId() + "/invitations/" + invitation.getId())
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doDelete(request, response.bind(HttpServletResponse.SC_NO_CONTENT));
        Assert.assertNotNull(response);
        Assert.assertNull(database.invitations.dao().queryForId(invitation.getId()));
    }

    @Test
    public void testDoDeleteEvent() throws SQLException, IOException {
        GroupDate groupDate = new GroupDate();
        database.groupDates.dao().create(groupDate);
        GroupDateEvent groupDateEvent = new GroupDateEvent();
        groupDateEvent.setGroupDate(groupDate);
        database.groupDateEvents.dao().create(groupDateEvent);

        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/" + groupDate.getId() + "/events/" + groupDateEvent.getId())
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doDelete(request, response.bind(HttpServletResponse.SC_NO_CONTENT));
        Assert.assertNotNull(response);
        Assert.assertNull(database.groupDateEvents.dao().queryForId(groupDateEvent.getId()));
    }

    @Test
    public void testDoDeleteInvalid() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/1/blah/")
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doDelete(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoDeleteNonexistent() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/1/blah/1")
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doDelete(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoDeleteUnauthorized() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", null)
                .withPathInfo("/1")
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doDelete(request, response.bind(HttpServletResponse.SC_UNAUTHORIZED));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoPut() throws IOException, SQLException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/" + groupDate.getId())
                .withBody("{\"finalized\": true, \"live\": true}")
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPut(request, response.bind(HttpServletResponse.SC_OK));
        Assert.assertNotNull(response);

        GroupDate latest = Database.load().groupDates.dao().queryForId(groupDate.getId());
        Assert.assertTrue(latest.getFinalized());
    }

    @Test
    public void testDoPutNotFound() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/")
                .withBody("{\"finalized\": true, \"live\": true}")
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPut(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
        Assert.assertNotNull(response);
    }

    @Test
    public void testDoPutNotInvalid() throws IOException {
        GroupDateServlet servlet = new GroupDateServlet();
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withHeader("Authorization", token)
                .withPathInfo("/100")
                .withBody("{\"finalized\": true, \"live\": true}")
                .build();

        MockHttpServletResponseTarget response = new MockHttpServletResponseTarget();
        servlet.doPut(request, response.bind(HttpServletResponse.SC_NOT_FOUND));
        Assert.assertNotNull(response);
    }

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        database.drop();
    }
}

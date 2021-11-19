package csci310.models;

import csci310.Configuration;
import csci310.Database;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

public class InvitationTest {
    private static Database database;
    private static Invitation invitation;

    @BeforeClass
    public static void setupTestDatabase() throws SQLException {
        Configuration.load("test");
        database = Database.load();
        User user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        User otherUser = UserTest.createUser("noahbkim", "secret", "Noah", "Kim");
        database.users.dao().create(user);
        database.users.dao().create(otherUser);
        GroupDate groupDate = GroupDateTest.createGroupDate(user, "Test Group Date", "Awesome group date!");
        database.groupDates.dao().create(groupDate);
        GroupDateEvent groupDateEvent = GroupDateTest.createGroupDateEvent(groupDate, "Event 1", "Super fun event!");
        database.groupDateEvents.dao().create(groupDateEvent);
        invitation = new Invitation();
        invitation.setGroupDate(groupDate);
        invitation.setUser(otherUser);
        database.invitations.dao().create(invitation);
    }

    @Test
    public void testGetResponse() throws SQLException {
        Assert.assertNull(invitation.getResponse());
        InvitationResponse invitationResponse = new InvitationResponse();
        invitationResponse.setInvitation(invitation);
        invitationResponse.setAccepted(true);
        database.invitationResponses.dao().create(invitationResponse);
        Assert.assertNotNull(invitation.getResponse());
    }

    @AfterClass
    public static void cleanup() throws SQLException {
        database.drop();
    }
}

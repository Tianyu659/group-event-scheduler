package csci310.models;

import csci310.Configuration;
import csci310.Database;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.sql.SQLException;

public class InvitationResponseTest {
    private static Database database;
    private static GroupDateEvent groupDateEvent;
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
        groupDateEvent = GroupDateTest.createGroupDateEvent(groupDate, "Event 1", "Super fun event!");
        database.groupDateEvents.dao().create(groupDateEvent);
        invitation = new Invitation();
        invitation.setGroupDate(groupDate);
        invitation.setUser(otherUser);
        database.invitations.dao().create(invitation);
    }

    @Test
    public void testGetEventResponses() throws SQLException {
        InvitationResponse invitationResponse = new InvitationResponse();
        invitationResponse.setInvitation(invitation);
        assertEquals(invitation, invitationResponse.getInvitation());
        invitationResponse.setAccepted(true);
        assertTrue(invitationResponse.isAccepted());
        database.invitationResponses.dao().create(invitationResponse);
        InvitationEventResponse invitationEventResponse = new InvitationEventResponse();
        invitationEventResponse.setEvent(groupDateEvent);
        invitationEventResponse.setInvitationResponse(invitationResponse);
        assertEquals(invitationResponse, invitationEventResponse.getInvitationResponse());
        assertEquals(0, invitationEventResponse.getId());
        database.invitationEventResponses.dao().create(invitationEventResponse);
        Assert.assertEquals(1, invitationResponse.getEventResponses().size());
    }

    @AfterClass
    public static void cleanup() throws SQLException {
        database.drop();
    }
}

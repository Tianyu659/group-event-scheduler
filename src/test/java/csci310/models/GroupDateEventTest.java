package csci310.models;

import csci310.Configuration;
import csci310.Database;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Vector;

public class GroupDateEventTest {
    private static Database database;
    private static User user;

    @BeforeClass
    public static void setupTestDatabase() throws SQLException {
        Configuration.load("test");
        database = Database.load(true);
        user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        database.users.dao().create(user);
    }

    @Test
    public void testGetGroupDate() {
        GroupDateEvent groupDateEvent = new GroupDateEvent();
        groupDateEvent.setGroupDate(new GroupDate());
        Assert.assertNotNull(groupDateEvent.getGroupDate());
    }

    @Test
    public void testGetInterest() throws SQLException {
        GroupDate groupDate = GroupDateTest.createGroupDate(user, "My Event", "Very fun event!");
        database.groupDates.dao().create(groupDate);
        GroupDateEvent groupDateEvent = new GroupDateEvent();
        groupDateEvent.setGroupDate(groupDate);
        database.groupDateEvents.dao().create(groupDateEvent);

        User other = UserTest.createUser("noahbkim", "secret", "Noah", "Kim");
        Invitation invitation = new Invitation();
        invitation.setGroupDate(groupDate);
        invitation.setUser(other);
        database.invitations.dao().create(invitation);
        InvitationResponse invitationResponse = new InvitationResponse();
        invitationResponse.setInvitation(invitation);
        invitationResponse.setAccepted(true);
        database.invitationResponses.dao().create(invitationResponse);
        InvitationEventResponse invitationEventResponse = new InvitationEventResponse();
        invitationEventResponse.setInvitationResponse(invitationResponse);
        invitationEventResponse.setEvent(groupDateEvent);
        invitationEventResponse.setAvailable(true);
        invitationEventResponse.setInterest(5);
        database.invitationEventResponses.dao().create(invitationEventResponse);

        invitation = new Invitation();
        invitation.setGroupDate(groupDate);
        invitation.setUser(user);
        database.invitations.dao().create(invitation);
        invitationResponse = new InvitationResponse();
        invitationResponse.setInvitation(invitation);
        invitationResponse.setAccepted(true);
        database.invitationResponses.dao().create(invitationResponse);
        invitationEventResponse = new InvitationEventResponse();
        invitationEventResponse.setInvitationResponse(invitationResponse);
        invitationEventResponse.setEvent(groupDateEvent);
        invitationEventResponse.setAvailable(false);
        invitationEventResponse.setInterest(0);
        database.invitationEventResponses.dao().create(invitationEventResponse);

        Vector<Integer> interest = groupDateEvent.getInterest();
        Assert.assertEquals(5, (int)interest.get(0));
        Assert.assertEquals(-1, (int)interest.get(1));
    }

    @AfterClass
    public static void cleanup() throws SQLException {
        database.drop();
    }
}

package csci310.models;

import csci310.Configuration;
import csci310.Database;
import io.cucumber.java.bs.I;
import org.junit.*;

import java.sql.SQLException;

public class GroupDateTest {
    private static Database database;
    private static User user;

    public static GroupDate createGroupDate(User creator, String name, String description) {
        GroupDate groupDate = new GroupDate();
        groupDate.setCreator(creator);
        groupDate.setName(name);
        groupDate.setDescription(description);
        groupDate.setLive(true);
        return groupDate;
    }

    public static GroupDateEvent createGroupDateEvent(GroupDate groupDate, String name, String description) {
        GroupDateEvent groupDateEvent = new GroupDateEvent();
        groupDateEvent.setGroupDate(groupDate);
        groupDateEvent.setName(name);
        groupDateEvent.setDescription(description);
        groupDateEvent.setTime(System.currentTimeMillis() / 1000);
        return groupDateEvent;
    }

    @BeforeClass
    public static void setupTestDatabase() throws SQLException {
        Configuration.load("test");
        database = Database.load();
        user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        database.users.dao().create(user);
    }
    
    @Test
    public void testGetEvents() throws SQLException {
        GroupDate groupDate = createGroupDate(user, "My Event", "Very fun event!");
        database.groupDates.dao().create(groupDate);
        Assert.assertEquals(0, groupDate.getEvents().size());
    }
    
    static GroupDateEvent makeGDE(final GroupDate gd,final int e) throws SQLException {
        final GroupDateEvent o = new GroupDateEvent();
        o.setGroupDate(gd);
        o.setUrl("");
        o.setTime(0);
        o.setName(gd.getName());
        o.setDuration(0);
        o.setEid(""+e);
        o.setDescription(gd.getDescription());
        o.setLocation("");
        database.groupDateEvents.dao().create(o);
        return o;
    }
    static Invitation makeI(final GroupDate gd) throws SQLException {
        final Invitation i = new Invitation();
        i.setGroupDate(gd);
        i.setUser(user);
        database.invitations.dao().create(i);
        return i;
    }
    static InvitationResponse makeIR(final Invitation i) throws SQLException {
        final InvitationResponse ir = new InvitationResponse();
        ir.setAccepted(false);
        ir.setInvitation(i);
        database.invitationResponses.dao().create(ir);
        return ir;
    }
    static void makeIER(final GroupDateEvent gde,
                        final InvitationResponse ir,
                        final int interest)
                        throws SQLException {
        final InvitationEventResponse o = new InvitationEventResponse();
        o.setInterest(interest);
        o.setAvailable(interest != 0);
        o.setEvent(gde);
        o.setInvitationResponse(ir);
        database.invitationEventResponses.dao().create(o);
    }
    @Test
    public void testSelectEventAllAvailable() throws SQLException {
        final int nDates = 10;
        final GroupDate gd = createGroupDate(user,"gd","");
        gd.setLive(false);
        database.groupDates.dao().create(gd);
        final GroupDateEvent[] dates = new GroupDateEvent[nDates];
        for(int i = 0;i < nDates;++i) dates[i] = makeGDE(gd,i);
        final InvitationResponse[] invitations = new InvitationResponse[nDates];
        for(int i = 0;i < nDates;++i) invitations[i] = makeIR(makeI(gd));
        
        final int nUsers = 6;
        final int nResponses = nUsers*nDates;
        final int[] sum = new int[nDates];
        for(int i = 0;i < nResponses;++i) {
            final int date = i % nDates,rate = (i % 5) + 1;
            makeIER(dates[date],invitations[date],rate);
            sum[date] += rate;
        }
        
        GroupDateEvent max = dates[0];
        double ms = (double)sum[0]/nDates;
        for(int i = 1;i < nDates;++i) {
            final double s = (double)sum[i]/nDates;
            if(ms < s) {
                ms = s;
                max = dates[i];
            }
        }
        
        final GroupDateEvent x = gd.selectEvent();
        Assert.assertEquals(max,x);
    }
    @Test
    public void testSelectEventImpossible() throws SQLException {
        final int nDates = 10;
        final GroupDate gd = createGroupDate(user,"gd","");
        database.groupDates.dao().create(gd);
        final GroupDateEvent[] dates = new GroupDateEvent[nDates];
        for(int i = 0;i < nDates;++i) dates[i] = makeGDE(gd,i);
        final InvitationResponse[] invitations = new InvitationResponse[nDates];
        for(int i = 0;i < nDates;++i) invitations[i] = makeIR(makeI(gd));
    
        final int nUsers = 6;
        final int nResponses = nUsers*nDates;
        for(int i = 0;i < nResponses;++i)
            makeIER(dates[i % nDates],invitations[i % nDates],0);
        
        Assert.assertNull(gd.selectEvent());
    }

    @Test
    public void testGetInvitations() throws SQLException {
        GroupDate groupDate = createGroupDate(user, "My Event", "Very fun event!");
        database.groupDates.dao().create(groupDate);
        User other = UserTest.createUser("noahbkim", "secret", "Noah", "Kim");
        Invitation invitation = new Invitation();
        invitation.setGroupDate(groupDate);
        invitation.setUser(other);
        database.invitations.dao().create(invitation);
        Assert.assertEquals(1, groupDate.getInvitations().size());
    }

    @Test
    public void testGetInvitationCount() throws SQLException {
        GroupDate groupDate = createGroupDate(user, "My Event", "Very fun event!");
        database.groupDates.dao().create(groupDate);
        User other = UserTest.createUser("noahbkim", "secret", "Noah", "Kim");
        Invitation invitation = new Invitation();
        invitation.setGroupDate(groupDate);
        invitation.setUser(other);
        database.invitations.dao().create(invitation);
        invitation = new Invitation();
        invitation.setGroupDate(groupDate);
        invitation.setUser(user);
        database.invitations.dao().create(invitation);
        InvitationResponse invitationResponse = new InvitationResponse();
        invitationResponse.setInvitation(invitation);
        invitationResponse.setAccepted(true);
        database.invitationResponses.dao().create(invitationResponse);
        int[] count = groupDate.getInvitationCount();
        Assert.assertEquals(1, count[0]);
        Assert.assertEquals(2, count[1]);
    }

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        database.drop();
    }
}

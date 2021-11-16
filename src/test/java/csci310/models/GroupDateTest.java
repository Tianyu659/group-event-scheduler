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
    
    @Before
    public void before() throws SQLException {
        database.groupDates.clear();
        database.groupDateEvents.clear();
        database.invitations.clear();
        database.invitationResponses.clear();
        database.invitationEventResponses.clear();
    }

    @Test
    public void testGetEvents() throws SQLException {
        GroupDate groupDate = createGroupDate(user, "My Event", "Very fun event!");
        database.groupDates.dao().create(groupDate);
        Assert.assertEquals(0, groupDate.getEvents().size());
    }
    
    static GroupDateEvent makeGDE(final GroupDate gd) {
        final GroupDateEvent o = new GroupDateEvent();
        o.setGroupDate(gd);
        return o;
    }
    static void makeIER(final GroupDateEvent gde,final int interest) throws SQLException {
        final InvitationEventResponse o = new InvitationEventResponse();
        o.available = (o.interest = interest) != 0;
        o.event = gde;
        database.invitationEventResponses.dao().create(o);
    }
    @Test
    public void testSelectEventAllAvailable() throws SQLException {
        final int nDates = 10;
        final GroupDate gd = createGroupDate(user,"gd","");
        database.groupDates.dao().create(gd);
        final GroupDateEvent[] dates = new GroupDateEvent[nDates];
        for(int i = 0;i < nDates;++i) dates[i] = makeGDE(gd);
        
        final int nUsers = 6;
        final int nResponses = nUsers*nDates;
        final int[] sum = new int[nDates];
        for(int i = 0;i < nResponses;++i) {
            final int date = i % nDates,rate = (i % 5) + 1;
            makeIER(dates[date],rate);
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
        for(int i = 0;i < nDates;++i) dates[i] = makeGDE(gd);
    
        final int nUsers = 6;
        final int nResponses = nUsers*nDates;
        for(int i = 0;i < nResponses;++i)
            makeIER(dates[i % nDates],i % 6);
        
        Assert.assertNull(gd.selectEvent());
    }

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        database.drop();
    }
}

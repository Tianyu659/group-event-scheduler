package csci310.models;

import csci310.Configuration;
import csci310.Database;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

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

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        database.drop();
    }
}

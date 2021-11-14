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

    @BeforeClass
    public static void setupTestDatabase() throws SQLException {
        database = new Database(Configuration.load("test"));
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
        database.users.clear();
        database.groupDates.clear();
    }
}

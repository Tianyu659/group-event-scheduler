package csci310.models;

import csci310.Configuration;
import csci310.Database;
import org.junit.*;

import java.sql.*;

public class UserTest {
    private static Database database;

    @BeforeClass
    public static void setupTestDatabase() {
        Configuration.load("test");
        database = Database.load();
    }

    public static User createUser(String username, String password, String firstName, String lastName) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    @Test
    public void testCreateUser() throws SQLException {
        User user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        database.users.dao().create(user);
        Assert.assertEquals(1, user.getId());
    }

    @Test
    public void testSetPassword() {
        User user = new User();
        String password = "hello, world!";
        user.setPassword(password);
        Assert.assertTrue(user.comparePassword(password));
    }

    @Test
    public void testSetPasswordIncorrect() {
        User user = new User();
        String password = "hello, world!";
        user.setPassword(password);
        Assert.assertFalse(user.comparePassword(password + " incorrect"));
    }

    @Test
    public void testPasswordOperation() {
        String result = User.passwordOperation("asdfjkl;", (a, b) -> a.hash(10, 65535, 1, b));
        Assert.assertTrue(result.startsWith("$argon2i$v=19$m=65535,t=10,p=1"));
    }

    @Test
    public void testComparePassword() {
        User user = new User();
        user.setPassword("asdfjkl;");
        Assert.assertTrue(user.comparePassword("asdfjkl;"));
    }

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        database.users.clear();
    }
}

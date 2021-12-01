package csci310.models;

import csci310.Configuration;
import csci310.Database;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

public class BlockTest {
    private static Database database;
    private static User user;
    private static User other;

    @BeforeClass
    public static void setupTestDatabase() throws SQLException {
        Configuration.load("test");
        database = Database.load();
        user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        other = UserTest.createUser("noahbkim", "secret", "Noah", "Kim");
        database.users.dao().create(user);
        database.users.dao().create(other);
    }

    @Test
    public void testGetBlocked() {
        Block block = new Block();
        block.setCreator(user);
        block.setBlocked(other);
        Assert.assertEquals(other.getId(), block.getBlocked().getId());
    }

    @Test
    public void testGetBlockedCache() throws SQLException {
        Block block = new Block();
        block.setCreator(user);
        block.setBlocked(other);
        Assert.assertEquals(other.getId(), block.getBlockedCache().getId());
    }

    @Test
    public void testGetBlockedCacheCached() throws SQLException {
        Block block = new Block();
        block.setCreator(user);
        block.setBlocked(other);
        Assert.assertEquals(other.getId(), block.getBlockedCache().getId());
        Assert.assertEquals(other.getId(), block.getBlockedCache().getId());
    }

    @Test
    public void testGetUserUsername() throws SQLException {
        Block block = new Block();
        block.setCreator(user);
        block.setBlocked(other);
        Assert.assertEquals(other.getUsername(), block.getUserUsername());
    }

    @Test
    public void testGetUserFirstName() throws SQLException {
        Block block = new Block();
        block.setCreator(user);
        block.setBlocked(other);
        Assert.assertEquals(other.getFirstName(), block.getUserFirstName());
    }

    @Test
    public void testGetUserLastName() throws SQLException {
        Block block = new Block();
        block.setCreator(user);
        block.setBlocked(other);
        Assert.assertEquals(other.getLastName(), block.getUserLastName());
    }

    @AfterClass
    public static void cleanup() throws SQLException {
        database.drop();
    }
}

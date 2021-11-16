package csci310.api;

import com.j256.ormlite.dao.Dao;
import csci310.Configuration;
import csci310.Database;
import csci310.models.User;
import csci310.models.UserTest;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;

public class EndpointTest {
    private static Database database;

    @BeforeClass
    public static void setupTestDatabase() {
        Configuration.load("test");
        database = Database.load();
    }

    @Test
    public void testConstructor() {
        Endpoint endpoint = new Endpoint();
        Assert.assertNotNull(endpoint);
    }

    @Test
    public void testListEmpty() throws SQLException, IOException {
        StringWriter writer = new StringWriter();
        Endpoint.list(database.users.dao().queryForAll(), writer);
        Assert.assertEquals("[]", writer.toString());
    }

    @Test
    public void testList() throws SQLException, IOException {
        User a = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        User b = UserTest.createUser("nkim", "secret", "Noah", "Kim");
        Dao<User, Integer> userDao = database.users.dao();
        userDao.create(a);
        userDao.create(b);
        StringWriter writer = new StringWriter();
        Endpoint.list(userDao.queryForAll(), writer);
        Assert.assertEquals("[{\"id\":1,\"username\":\"ttrojan\",\"firstName\":\"Tommy\",\"lastName\":\"Trojan\"},{\"id\":2,\"username\":\"nkim\",\"firstName\":\"Noah\",\"lastName\":\"Kim\"}]", writer.toString());
        userDao.delete(a);
        userDao.delete(b);
    }

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        database.drop();
    }
}

package csci310.api;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
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
    private static JdbcConnectionSource connectionSource;
    private static Dao<User, Integer> userDao;

    @BeforeClass
    public static void setupTestDatabase() throws SQLException {
        EndpointTest.connectionSource = Database.connect();
        TableUtils.dropTable(EndpointTest.connectionSource, User.class, true);
        TableUtils.createTable(EndpointTest.connectionSource, User.class);
        EndpointTest.userDao = DaoManager.createDao(EndpointTest.connectionSource, User.class);
    }

    @Test
    public void testConstructor() {
        Endpoint endpoint = new Endpoint();
        Assert.assertNotNull(endpoint);
    }

    @Test
    public void testListEmpty() throws SQLException, IOException {
        StringWriter writer = new StringWriter();
        Endpoint.list(EndpointTest.userDao.queryForAll(), writer);
        Assert.assertEquals("[]", writer.toString());
    }

    @Test
    public void testList() throws SQLException, IOException {
        User a = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        User b = UserTest.createUser("nkim", "secret", "Noah", "Kim");
        EndpointTest.userDao.create(a);
        EndpointTest.userDao.create(b);
        StringWriter writer = new StringWriter();
        Endpoint.list(EndpointTest.userDao.queryForAll(), writer);
        Assert.assertEquals("[{\"id\":1,\"username\":\"ttrojan\",\"firstName\":\"Tommy\",\"lastName\":\"Trojan\"},{\"id\":2,\"username\":\"nkim\",\"firstName\":\"Noah\",\"lastName\":\"Kim\"}]", writer.toString());
        EndpointTest.userDao.delete(a);
        EndpointTest.userDao.delete(b);
    }

    @AfterClass
    public static void teardownTestDatabase() throws SQLException {
        TableUtils.dropTable(EndpointTest.connectionSource, User.class, true);
    }
}

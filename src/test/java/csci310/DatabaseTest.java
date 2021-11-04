package csci310;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import csci310.exception.ConfigurationException;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class DatabaseTest {
    @Test
    public void testDatabaseConstructor() {
        Database database = new Database();
        Assert.assertNotNull(database);
    }

    @Test
    public void testDatabaseConnect() {
        JdbcConnectionSource connectionSource = Database.connect();
        Assert.assertNotNull(connectionSource);
    }

    @Test
    public void testDatabaseConnectSqlite() {
        Configuration configuration = EasyMock.mock(Configuration.class);
        Map<String, String> values = Map.of("type", "sqlite", "url", "jdbc:sqlite:test.sqlite3");
        EasyMock.expect(configuration.values("database")).andReturn(values);
        EasyMock.replay(configuration);
        Database.createConnectionSource(configuration);
    }

    @Test
    public void testDatabaseConnectSqliteInvalid() {
        Configuration configuration = EasyMock.mock(Configuration.class);
        Map<String, String> values = new HashMap<>();
        values.put("type", "sqlite");
        values.put("url", null);

        EasyMock.expect(configuration.values("database")).andReturn(values);
        EasyMock.replay(configuration);

        try {
            Database.createConnectionSource(configuration);
            Assert.fail();
        } catch (ConfigurationException exception) {
            Assert.assertNotNull(exception);
        }
    }

    @Test
    public void testDatabaseConnectInvalid() {
        Configuration configuration = EasyMock.mock(Configuration.class);
        Map<String, String> values = new HashMap<>();
        values.put("type", "invalid");
        values.put("url", null);

        EasyMock.expect(configuration.values("database")).andReturn(values);
        EasyMock.replay(configuration);

        try {
            Database.createConnectionSource(configuration);
            Assert.fail();
        } catch (ConfigurationException exception) {
            Assert.assertNotNull(exception);
        }
    }

    @Test
    public void testDatabaseSetup() {
        Configuration configuration = EasyMock.mock(Configuration.class);
        Map<String, String> values = Map.of("type", "sqlite", "url", "jdbc:sqlite:test.sqlite3");
        EasyMock.expect(configuration.values("database")).andReturn(values);
        EasyMock.replay(configuration);
        Database.setup(configuration);
    }
}

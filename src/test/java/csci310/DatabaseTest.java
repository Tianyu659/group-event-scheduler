package csci310;

import csci310.exception.ConfigurationException;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.Assert;

import java.sql.SQLException;

public class DatabaseTest {
    @Test
    public void testConstructor() {
        Database database = new Database(Configuration.load("test"));
        Assert.assertNotNull(database);
    }

    @Test
    public void testLoad() {
        Configuration.load("test");
        Database database = Database.load();
        Assert.assertNotNull(database);
    }

    @Test
    public void testConnectSqlite() {
        Configuration configuration = EasyMock.mock(Configuration.class);
        EasyMock.expect(configuration.value("database.type")).andReturn("sqlite");
        EasyMock.expect(configuration.value("database.url")).andReturn("jdbc:sqlite:test.sqlite3");
        EasyMock.replay(configuration);
        Database.createConnectionSource(configuration);
    }

    @Test
    public void testTableDao() throws SQLException {
        Configuration configuration = EasyMock.mock(Configuration.class);
        EasyMock.expect(configuration.value("database.type")).andReturn("sqlite");
        EasyMock.expect(configuration.value("database.url")).andReturn("jdbc:sqlite:test.sqlite3");
        EasyMock.replay(configuration);
        Database database = new Database(configuration);
        Assert.assertNotNull(database.users.dao());
    }

    @Test
    public void testTableClear() throws SQLException {
        Configuration configuration = EasyMock.mock(Configuration.class);
        EasyMock.expect(configuration.value("database.type")).andReturn("sqlite");
        EasyMock.expect(configuration.value("database.url")).andReturn("jdbc:sqlite:test.sqlite3");
        EasyMock.replay(configuration);
        Database database = new Database(configuration);
        database.users.clear();
    }

    @Test
    public void testConnectSqliteInvalid() {
        Configuration configuration = EasyMock.mock(Configuration.class);
        EasyMock.expect(configuration.value("database.type")).andReturn("sqlite");
        EasyMock.expect(configuration.value("database.url")).andReturn(null);
        EasyMock.replay(configuration);

        try {
            Database.createConnectionSource(configuration);
            Assert.fail();
        } catch (ConfigurationException exception) {
            Assert.assertNotNull(exception);
        }
    }

    @Test
    public void testConnectInvalid() {
        Configuration configuration = EasyMock.mock(Configuration.class);
        EasyMock.expect(configuration.value("database.type")).andReturn("invalid");
        EasyMock.expect(configuration.value("database.url")).andReturn(null);
        EasyMock.replay(configuration);

        try {
            Database.createConnectionSource(configuration);
            Assert.fail();
        } catch (ConfigurationException exception) {
            Assert.assertNotNull(exception);
        }
    }

    @Test
    public void testCreateConnectionSource() {
        Configuration configuration = EasyMock.mock(Configuration.class);
        EasyMock.expect(configuration.value("database.type")).andReturn("sqlite");
        EasyMock.expect(configuration.value("database.url")).andReturn("jdbc:sqlite:test.sqlite3");
        EasyMock.replay(configuration);
        new Database(configuration);
    }
}

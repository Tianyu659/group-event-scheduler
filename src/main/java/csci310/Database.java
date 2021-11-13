package csci310;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import csci310.exception.ConfigurationException;
import csci310.models.*;

import java.sql.SQLException;

public class Database {
    private static final Database instance = new Database(Configuration.load());

    JdbcConnectionSource connectionSource;

    public class Table<T> {
        private final Class<T> tClass;

        public Table(Class<T> tClass) {
            this.tClass = tClass;
        }

        public Dao<T, Integer> dao() throws SQLException {
            ConfigurationException.wrap(() -> {
                TableUtils.createTableIfNotExists(connectionSource, this.tClass);
                return null;
            }, "unable to create table for " + tClass.getName());
            return DaoManager.createDao(connectionSource, tClass);
        }

        public void clear() throws SQLException {
            TableUtils.dropTable(connectionSource, this.tClass, true);
            TableUtils.createTable(connectionSource, this.tClass);
        }
    }

    public Table<User> users;
    public Table<GroupDate> groupDates;
    public Table<GroupDateEvent> groupDateEvents;
    public Table<Invitation> invitations;

    public Database(Configuration configuration) {
        this.connectionSource = Database.createConnectionSource(configuration);
        this.users = new Table<>(User.class);
        this.groupDates = new Table<>(GroupDate.class);
        this.groupDateEvents = new Table<>(GroupDateEvent.class);
        this.invitations = new Table<>(Invitation.class);
    }

    public static Database load() {
        return Database.instance;
    }

    public static JdbcConnectionSource createConnectionSource(Configuration configuration) {
        String type = configuration.value("database.type");
        if (type.equals("sqlite")) {
            return ConfigurationException.wrap(
                    () -> new JdbcPooledConnectionSource(configuration.value("database.url")),
                    "unable to initialize SQLite connector!");
        } else {
            throw new ConfigurationException("unknown database type " + type);
        }
    }
}

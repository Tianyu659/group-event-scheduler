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
    private static Database instance;

    JdbcConnectionSource connectionSource;

    public class Table<T> {
        private final Class<T> tClass;
        private Dao<T,Integer> dao = null;

        public Table(Class<T> tClass) {
            this.tClass = tClass;
        }

        public Dao<T, Integer> dao() throws SQLException {
            if(dao != null) return dao;
            return dao = DaoManager.createDao(connectionSource, tClass);
        }

        public void create() {
            ConfigurationException.wrap(() -> {
                TableUtils.createTableIfNotExists(connectionSource, this.tClass);
                return null;
            }, "unable to create table for " + tClass.getName());
        }

        public void drop() throws SQLException {
            dao = null;
            TableUtils.dropTable(connectionSource, this.tClass, true);
        }
    }

    public final Table<User> users;
    public final Table<GroupDate> groupDates;
    public final Table<GroupDateEvent> groupDateEvents;
    public final Table<Invitation> invitations;
    public final Table<InvitationResponse> invitationResponses;
    public final Table<InvitationEventResponse> invitationEventResponses;
    public final Table<Block> blocks;
    public final Table<Blackout> blackouts;

    public Database(Configuration configuration) {
        this.connectionSource = Database.createConnectionSource(configuration);
        this.users = new Table<>(User.class);
        this.groupDates = new Table<>(GroupDate.class);
        this.groupDateEvents = new Table<>(GroupDateEvent.class);
        this.invitations = new Table<>(Invitation.class);
        this.invitationResponses = new Table<>(InvitationResponse.class);
        this.invitationEventResponses = new Table<>(InvitationEventResponse.class);
        this.blocks = new Table<>(Block.class);
        this.blackouts = new Table<>(Blackout.class);
    }

    public static Database load(boolean create) {
        if (Database.instance == null) {
            Database.instance = new Database(Configuration.load());
            Database.instance.create();
        } else if (create) {
            Database.instance.create();
        }
        return Database.instance;
    }

    public static Database load() {
        return load(false);
    }

    public void create() {
        this.users.create();
        this.groupDates.create();
        this.groupDateEvents.create();
        this.invitations.create();
        this.invitationResponses.create();
        this.invitationEventResponses.create();
        this.blocks.create();
        this.blackouts.create();
    }

    public void drop() throws SQLException {
        this.users.drop();
        this.groupDates.drop();
        this.groupDateEvents.drop();
        this.invitations.drop();
        this.invitationResponses.drop();
        this.invitationEventResponses.drop();
        this.blocks.drop();
        this.blackouts.drop();
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

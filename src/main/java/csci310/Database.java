package csci310;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import csci310.exception.ConfigurationException;
import csci310.exception.NotImplementedError;
import csci310.models.User;

import java.sql.SQLException;
import java.util.Map;

public class Database {
    private static final JdbcConnectionSource connectionSource = Database.setup(Configuration.load());

    public static JdbcConnectionSource connect() {
        return Database.connectionSource;
    }

    public static JdbcConnectionSource setup(Configuration configuration) {
        throw new NotImplementedError();
    }

    public static JdbcConnectionSource createConnectionSource(Configuration configuration) {
        Map<String, String> values = configuration.values("database");
        if (values.get("type").equals("sqlite")) {
            return ConfigurationException.wrap(
                    () -> new JdbcPooledConnectionSource(values.get("url")),
                    "Unable to initialize SQLite connector!");
        } else {
            throw new ConfigurationException("unknown database type " + values.get("type"));
        }
    }
}

package csci310;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import csci310.exception.ConfigurationException;

import java.sql.SQLException;
import java.util.Map;

public class Database {
    private static final JdbcConnectionSource connectionSource = Database.createConnectionSource(Configuration.load());

    public static JdbcConnectionSource connect() {
        return Database.connectionSource;
    }

    public static JdbcConnectionSource createConnectionSource(Configuration configuration) {
        Map<String, String> values = configuration.values("database");
        if (values.get("type").equals("sqlite")) {
            try {
                return new JdbcPooledConnectionSource(values.get("url"));
            } catch (SQLException exception) {
                throw new ConfigurationException("Unable to initialize SQLite connector!");
            }
        } else {
            throw new ConfigurationException("unknown database type " + values.get("type"));
        }
    }
}

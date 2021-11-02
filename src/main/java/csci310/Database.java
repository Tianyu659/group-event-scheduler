package csci310;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import csci310.exception.ConfigurationException;
import csci310.exception.NotImplementedError;

import java.sql.SQLException;
import java.util.Map;

public class Database {
    private static final JdbcConnectionSource connectionSource = Database.createConnectionSource(Configuration.load());

    public static JdbcConnectionSource connect() {
        throw new NotImplementedError();
    }

    public static JdbcConnectionSource createConnectionSource(Configuration configuration) {
        throw new NotImplementedError();
    }
}

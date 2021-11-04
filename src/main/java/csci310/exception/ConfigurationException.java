package csci310.exception;

import java.sql.SQLException;

/** Identifies exceptions related to one-time startup. */
public class ConfigurationException extends RuntimeException {
    public ConfigurationException(String message) {
        super(message);
    }

    public interface ThrowsSQLException<T> {
        T call() throws SQLException;
    }

    public static <T> T wrap(ThrowsSQLException<T> lambda, String message) throws ConfigurationException {
        try {
            return lambda.call();
        } catch (SQLException exception) {
            throw new ConfigurationException(message);
        }
    }
}

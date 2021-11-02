package csci310.exception;

/** Identifies exceptions related to one-time startup. */
public class ConfigurationException extends RuntimeException {
    public ConfigurationException(String message) {
        super(message);
    }
}

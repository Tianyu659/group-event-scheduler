package csci310.exception;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RequestException extends Exception {
    private final int status;
    private final String message;

    public RequestException(int status, String message) {
        throw new NotImplementedError();
    }

    public void apply(HttpServletResponse response) throws IOException {
        throw new NotImplementedError();
    }

    public interface ThrowsSQLException<T> {
        T call() throws SQLException;
    }

    public static <T> T wrap(ThrowsSQLException<T> lambda, String message) throws RequestException {
        throw new NotImplementedError();
    }
}

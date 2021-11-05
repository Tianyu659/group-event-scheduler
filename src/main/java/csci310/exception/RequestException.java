package csci310.exception;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RequestException extends Exception {
    private final int status;
    private final String message;

    public RequestException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public void apply(HttpServletResponse response) throws IOException {
        response.setStatus(this.status);
        response.setContentType("application/json");
        response.getWriter().println("{\"error\": \"" + this.message + "\"}");
    }

    public interface ThrowsSQLException<T> {
        T call() throws SQLException;
    }

    public static <T> T wrap(ThrowsSQLException<T> lambda, String message) throws RequestException {
        try {
            return lambda.call();
        } catch (SQLException exception) {
            throw new RequestException(500, message);
        }
    }
}

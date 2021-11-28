package csci310.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class RequestException extends Exception {
    private final int status;
    private final String message;
    private final String details;

    public RequestException(int status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public RequestException(int status, String message) {
        this(status, message, null);
    }

    public void apply(HttpServletResponse response) throws IOException {
        response.setStatus(this.status);
        response.setContentType("application/json");

        Map<String, String> data;
        if (this.details != null) {
            data = Map.of("error", this.message, "details", this.details);
        } else {
            data = Map.of("error", this.message);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), data);
    }

    public interface ThrowsSQLException<T> {
        T call() throws SQLException, RequestException;
    }

    public static <T> T wrap(ThrowsSQLException<T> lambda, String message) throws RequestException {
        try {
            return lambda.call();
        } catch (SQLException exception) {
            throw new RequestException(500, message);
        }
    }
}

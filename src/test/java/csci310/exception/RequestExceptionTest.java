package csci310.exception;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class RequestExceptionTest {
    @Test
    public void testWrapSQLException() {
        try {
            RequestException.wrap(() -> { throw new SQLException(); }, "error!");
            Assert.fail();
        } catch (RequestException exception) {
            Assert.assertNotNull(exception);
        }
    }
}

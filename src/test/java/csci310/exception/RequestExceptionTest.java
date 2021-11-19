package csci310.exception;

import csci310.mock.MockHttpServletResponseTarget;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RequestExceptionTest {
    @Test
    public void testWrap() {
        try {
            RequestException.wrap(() -> { throw new SQLException(); }, "error!");
            Assert.fail();
        } catch (RequestException exception) {
            Assert.assertNotNull(exception);
        }
    }

    @Test
    public void testApply() throws IOException {
        RequestException exception = new RequestException(200, "hello!");
        HttpServletResponse response = new MockHttpServletResponseTarget()
                .bind(200);
        exception.apply(response);
    }

    @Test
    public void testApplyDetails() throws IOException {
        RequestException exception = new RequestException(200, "hello!", "details");
        HttpServletResponse response = new MockHttpServletResponseTarget()
                .bind(200);
        exception.apply(response);
    }
}

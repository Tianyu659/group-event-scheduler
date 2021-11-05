package csci310.mock;

import org.easymock.EasyMock;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MockHttpServletResponseTarget {
    private final HttpServletResponse mock = EasyMock.mock(HttpServletResponse.class);
    private final StringWriter writer = new StringWriter();

    public HttpServletResponse bind(int status) throws IOException {
        return this.bind(status, "application/json");
    }

    public HttpServletResponse bind(int status, String contentType) throws IOException {
        EasyMock.expect(this.mock.getWriter()).andReturn(new PrintWriter(this.writer));
        this.mock.setStatus(status);
        this.mock.setContentType(contentType);
        EasyMock.replay(this.mock);
        return this.mock;
    }

    public StringBuffer getBody() {
        return this.writer.getBuffer();
    }
}

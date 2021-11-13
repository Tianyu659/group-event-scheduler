package csci310.mock;

import org.easymock.EasyMock;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class MockHttpServletRequestBuilder {
    private final HttpServletRequest mock = EasyMock.mock(HttpServletRequest.class);

    public MockHttpServletRequestBuilder withPathInfo(String pathInfo) {
        EasyMock.expect(this.mock.getPathInfo()).andReturn(pathInfo);
        return this;
    }

    public MockHttpServletRequestBuilder withBody(String body) throws IOException {
        EasyMock.expect(this.mock.getReader()).andReturn(new BufferedReader(new StringReader(body)));
        return this;
    }

    public MockHttpServletRequestBuilder withHeader(String key, String value) {
        EasyMock.expect(this.mock.getHeader(key)).andReturn(value);
        return this;
    }

    public HttpServletRequest build() {
        EasyMock.replay(this.mock);
        return this.mock;
    }
}

package csci310.forms;

import csci310.exception.RequestException;
import csci310.mock.MockHttpServletRequestBuilder;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class FormTest {
    @Test
    public void testParse() throws IOException {
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"nkim\", \"password\": \"secret\", \"firstName\": \"Noah\", \"lastName\": \"Kim\"}")
                .build();
        UserForm form = Form.parse(request.getReader(), UserForm.class);
        Assert.assertNotNull(form);
    }

    @Test
    public void testRead() throws IOException, RequestException {
        HttpServletRequest request = new MockHttpServletRequestBuilder()
                .withBody("{\"username\": \"nkim\", \"password\": \"secret\", \"firstName\": \"Noah\", \"lastName\": \"Kim\"}")
                .build();
        UserForm form = Form.read(request, UserForm.class);
        Assert.assertNotNull(form);
    }
}

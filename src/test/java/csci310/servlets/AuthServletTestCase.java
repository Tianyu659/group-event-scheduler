package csci310.servlets;

import org.junit.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

import static org.mockito.Mockito.*;

interface AuthServletTestCase<R extends Enum<R>> {
    String rsa();
    String joined();
    default void exec(final AuthServletBase<R> servlet) {
        try {
            final HashMap<String,Object> map = new HashMap<>();
            map.put(
                "keys",
                KeyFactory.getInstance("RSA").generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsa()))
                )
            );
            final HttpSession session = mock(HttpSession.class);
            doAnswer(a -> map.get(a.<String>getArgument(0))).when(session).getAttribute("keys");
            final HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getSession()).thenReturn(session);
            final BufferedReader br = new BufferedReader(new StringReader(joined()));
            when(request.getReader()).thenReturn(br);
            when(request.getContentLength()).thenReturn(1);
        
            final HttpServletResponse response = mock(HttpServletResponse.class);
            final StringWriter sw = new StringWriter();
            when(response.getWriter()).thenReturn(new PrintWriter(sw));
        
            servlet.doPost(request,response);
            Assert.assertEquals(toString(),sw.toString());
        } catch(final Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }
}
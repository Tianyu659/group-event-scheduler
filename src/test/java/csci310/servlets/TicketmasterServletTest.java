package csci310.servlets;

import csci310.Authentication;
import csci310.Configuration;
import csci310.Database;
import csci310.models.User;
import csci310.models.UserTest;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class TicketmasterServletTest {
    private static Database database;

    private static String userToken;
    private static String eventKwd;

    @BeforeClass
    public static void setUp() throws SQLException {
        Configuration.load("test");
        database = Database.load();
        User user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        database.users.dao().create(user);

        userToken = Authentication.get().key(user);
        eventKwd = "keyword,Football";
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        database.drop();
    }
    
    @InjectMocks TicketmasterServlet servlet;
    @Before public void before() {MockitoAnnotations.initMocks(this);}
    @After public void after() {}
    
    static HttpServletRequest request(final String auth) {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        Map<String, String[]> parameters = new HashMap<>();
        when(request.getParameterMap()).thenReturn(parameters);
        when(request.getHeader("Authorization")).thenReturn(auth);
        return request;
    }

    static HttpServletResponse response(final StringWriter sw) throws IOException {
        final HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(new PrintWriter(sw));
        return response;
    }

    String exec(final String auth) {
        try {
            final HttpServletRequest request = request(auth);
            final StringWriter sw = new StringWriter();
            final HttpServletResponse response = response(sw);
        
            servlet.doPost(request, response);
            return sw.toString();
        } catch(final Exception e) {
            e.printStackTrace();
            fail("Encountered unexpected exception.");
        }
        return null;
    }

    private void testToken(final String token,final String result) {
        assertEquals(result.trim(),exec(token).trim());
    }

    @Test
    public void testDoPostNoToken() {
        testToken(null,"{\"error\":\"user authentication is required!\"}");
    }
    
    @Test
    public void testDoPostQuery() {
        // The scope of this test only cares that the servlet doesn't throw.
        exec(userToken);
    }
}
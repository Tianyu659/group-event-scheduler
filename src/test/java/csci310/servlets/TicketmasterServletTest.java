package csci310.servlets;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import csci310.Authentication;
import csci310.Database;
import csci310.models.User;
import csci310.models.UserTest;
import org.junit.*;
import org.mockito.InjectMocks;
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
    private static JdbcConnectionSource connectionSource;
    private static Dao<User, Integer> userDao;
    private static User user;
    private static String userToken;
    private static String eventKwd;
    @BeforeClass
    public static void setUp() throws SQLException {
        connectionSource = Database.connect();
        TableUtils.dropTable(connectionSource, User.class, true);
        TableUtils.createTable(connectionSource, User.class);
        userDao = DaoManager.createDao(connectionSource, User.class);
        user = UserTest.createUser("ttrojan", "secret", "Tommy", "Trojan");
        userDao.create(user);
        userToken = Authentication.get().key(user);
        eventKwd = "keyword,Football";
    }
    @AfterClass
    public static void tearDown() throws SQLException {
        TableUtils.dropTable(connectionSource, User.class, true);
    }
    
    @InjectMocks TicketmasterServlet servlet;
    @Before public void before() {MockitoAnnotations.initMocks(this);}
    @After public void after() {}
    
    static HttpServletRequest request(final String auth,final String...other) {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization",auth);
        for(final String s : other) {
            final String[] split = s.split(",",2);
            headers.put(split[0],split[1]);
        }
        doAnswer(a -> headers.get(a.<String>getArgument(0))).when(request).getHeader("Authorization");
        return request;
    }
    static HttpServletResponse response(final StringWriter sw) throws IOException {
        final HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(new PrintWriter(sw));
        return response;
    }
    String exec(final String auth,final String...other) {
        try {
            final HttpServletRequest request = request(auth,other);
            final StringWriter sw = new StringWriter();
            final HttpServletResponse response = response(sw);
        
            servlet.doPost(request,response);
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
    public void testdoPostBadToken() {
        testToken(
            Authentication.get().key(
                UserTest.createUser(
                    "invalid",
                    "user",
                    "invalid",
                    "user"
                )
            ),
            "{\"error\": \"invalid authentication!\"}"
        );
    }
    @Test
    public void testdoPostNoToken() {
        testToken(null,"{\"error\": \"user authentication is required!\"}");
    }
    
    @Test
    public void testdoPostQuery() {
        // The scope of this test only cares that the servlet doesn't throw.
        exec(userToken,eventKwd);
    }
}
package csci310.servlets;

import csci310.security.EncryptionUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    /**
     * An interface which specifies whether {@code HttpServletRequest::getContentLength()}
     * represents a particular operation.
     */
    @FunctionalInterface
    private interface Operator {boolean apply(int charsRead);}
    /**An enumeration of possible operations which can be requested by the client.*/
    public enum Operations {
        getKey(charsRead -> charsRead == 0),
        login(charsRead -> charsRead != 0);
        
        private final Operator op;
        Operations(final Operator op) {this.op = op;}
        boolean isOperation(final int cb) {return op.apply(cb);}
    }
    private static final byte[] TEST_USERNAME,TEST_PASSWORD,TEST_SALT;
    static {
        TEST_USERNAME = "test_username".getBytes(StandardCharsets.UTF_8);
        TEST_SALT = new byte[]{(byte)0x32,(byte)0x1E,(byte)0x6E,(byte)0xBD,
                               (byte)0x5A,(byte)0x53,(byte)0xB1,(byte)0xBD,
                               (byte)0x88,(byte)0x71,(byte)0x09,(byte)0x76,
                               (byte)0xB1,(byte)0x51,(byte)0x04,(byte)0x6E};
        TEST_PASSWORD = EncryptionUtil.hash("test_password".getBytes(StandardCharsets.UTF_8),TEST_SALT);
    }
    /**
     * @param username Database key.
     *
     * @return The salt and hashed password, respectively.
     */
    private byte[][] saltAndPwdHash(final byte[] username) {
        //TODO database stuff
        if(
            new String(TEST_USERNAME,StandardCharsets.UTF_8).contentEquals(
                new String(username,StandardCharsets.UTF_8)
            )
        ) return new byte[][] {TEST_SALT,TEST_PASSWORD};
        return new byte[2][0];
    }
    /**@return {@code true} iff the username and password pair are in the database.*/
    private boolean auth(final byte[] username,final byte[] password) {
        // Get the hashed password and its salt keyed with the username.
        final byte[][] dbResponse = saltAndPwdHash(username);
        // Hash the argument with the provided salt.
        final byte[] test = EncryptionUtil.hash(password,dbResponse[0]);
        final byte[] dbPwd = dbResponse[1];
        // Compare the database entry and the hashed argument.
        if(test.length != dbPwd.length) return false;
        for(int i = 0;i < test.length;++i)
            if(test[i] != dbPwd[i])
                return false;
        return true;
    }
    @Override
    protected void doPost(final HttpServletRequest req,final HttpServletResponse resp) throws IOException {
        final int op = req.getContentLength();
        if(Operations.getKey.isOperation(op)) EncryptionUtil.initRSA(req,resp);
        else if(Operations.login.isOperation(op)) {
            // Decrypt the message from the client.
            final String[] credentials = new String(
                EncryptionUtil.decryptHybrid(
                    req.getReader(),
                    SessionAttributes.keys.getAttribute(req.getSession())
                ),
                StandardCharsets.UTF_8
            ).split("\n",2);
            
            //TODO DEBUG
            System.out.println(credentials[0]+","+credentials[1]);
            
            // Attempt to log in with the provided credentials.
            final PrintWriter pw = resp.getWriter();
            if(
                auth(
                    credentials[0].getBytes(StandardCharsets.UTF_8),
                    credentials[1].getBytes(StandardCharsets.UTF_8)
                )
            ) {
                pw.println("success");
                //TODO send info
            } else {
                pw.println("failure");
            }
        }
    }
}
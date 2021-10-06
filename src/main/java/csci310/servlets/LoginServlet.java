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
    /**
     * @param username Database key.
     *
     * @return The salt and hashed password, respectively.
     */
    private byte[][] saltAndPwdHash(final byte[] username) {
        //TODO database stuff
        return new byte[2][16];
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
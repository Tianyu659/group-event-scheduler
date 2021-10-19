package csci310.servlets;

import csci310.security.EncryptionUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * A base for servlets which perform authentication.
 *
 * @param <R> The enumeration type which specifies the result.
 */
public abstract class AuthServletBase<R extends Enum<R>> extends HttpServlet {
    /**
     * An interface which specifies whether {@code HttpServletRequest::getContentLength()}
     * represents a particular operation.
     */
    @FunctionalInterface
    private interface Operator {boolean apply(int charsRead);}
    /**An enumeration of possible operations which can be requested by the client.*/
    public enum Operations {
        getKey(charsRead -> charsRead == 0),
        register(charsRead -> charsRead != 0);
        
        private final Operator op;
        Operations(final Operator op) {this.op = op;}
        boolean isOperation(final int cb) {return op.apply(cb);}
    }
    
    protected abstract R getSuccess();
    protected abstract R doAuth(final String[] credentials);
    
    @Override
    protected void doPost(final HttpServletRequest req,
                          final HttpServletResponse resp)
                          throws IOException {
        final int op = req.getContentLength();
        if(Operations.getKey.isOperation(op)) EncryptionUtil.initRSA(req,resp);
        else if(Operations.register.isOperation(op)) {
            final HttpSession session = req.getSession();
            // Decrypt the message from the client.
            final String[] credentials = new String(
                EncryptionUtil.decryptHybrid(
                    req.getReader(),
                    SessionAttributes.keys.getAttribute(session)
                ),
                StandardCharsets.UTF_8
            ).split("\n",2);
        
            //TODO DEBUG
            System.out.println(credentials[0]+","+credentials[1]);
        
            // Use the credentials.
            final R result = doAuth(credentials);
            SessionAttributes.isAuthed.setAttribute(session,result == getSuccess());
            resp.getWriter().print(result);
        }
    }
}
package csci310.servlets;

import csci310.dbms.DBConnector.AuthResult;
import javax.servlet.annotation.WebServlet;

import static csci310.dbms.DBConnector.AuthResult.success;
import static csci310.dbms.DBConnector.auth;

@WebServlet("/login")
public class LoginServlet extends AuthServletBase<AuthResult> {
    @Override protected AuthResult getSuccess() {return success;}
    @Override protected AuthResult doAuth(final String[] credentials) {return auth(credentials);}
}
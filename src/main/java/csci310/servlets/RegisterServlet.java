package csci310.servlets;

import csci310.dbms.DBConnector.RegResult;
import javax.servlet.annotation.WebServlet;

import static csci310.dbms.DBConnector.RegResult.success;
import static csci310.dbms.DBConnector.register;

@WebServlet("/register")
public class RegisterServlet extends AuthServletBase<RegResult> {
    @Override protected RegResult getSuccess() {return success;}
    @Override protected RegResult doAuth(final String[] credentials) {return register(credentials);}
}
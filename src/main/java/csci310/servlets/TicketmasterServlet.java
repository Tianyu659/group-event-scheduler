package csci310.servlets;

import csci310.Authentication;
import csci310.exception.RequestException;
import csci310.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TicketmasterServlet extends HttpServlet {
    static void verify(HttpServletRequest request) throws RequestException {
    
    }
    static void queryTicketmaster(HttpServletRequest request,HttpServletResponse response) throws RequestException {
    
    }
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try {
            verify(request);
            queryTicketmaster(request,response);
        } catch(final RequestException e) {e.apply(response);}
    }
}






















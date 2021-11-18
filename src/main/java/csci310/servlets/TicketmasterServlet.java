package csci310.servlets;

import csci310.Authentication;
import csci310.exception.RequestException;
import csci310.models.EventSearch;
import csci310.util.TicketmasterManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TicketmasterServlet extends HttpServlet {
    static void verify(HttpServletRequest request) throws RequestException {
        Authentication.get().authenticate(request);
    }
    static void queryTicketmaster(HttpServletRequest request,HttpServletResponse response) throws IOException, InterruptedException {
        response.setContentType("application/json");
        response.getWriter().print(
            TicketmasterManager.searchEvent(new EventSearch(request.getParameterMap()))
        );
    }
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try {
            verify(request);
            queryTicketmaster(request,response);
        } 
        catch(final RequestException e) {e.apply(response);} 
        catch (InterruptedException e) {}
    }
}
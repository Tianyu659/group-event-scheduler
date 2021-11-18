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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Authentication.get().authenticate(request);
            response.setContentType("application/json");
            response.getWriter().print(TicketmasterManager.searchEvent(new EventSearch(request.getParameterMap())));
        } catch (final RequestException exception) {
            exception.apply(response);
        }
    }
}
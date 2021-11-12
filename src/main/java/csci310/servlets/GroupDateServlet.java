package csci310.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import csci310.Authentication;
import csci310.Database;
import csci310.exception.RequestException;
import csci310.forms.Form;
import csci310.forms.GroupDateForm;
import csci310.models.GroupDate;
import csci310.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GroupDateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            User user = Authentication.get().authenticate(request);
            Dao<GroupDate, Integer> dao = RequestException.wrap(
                    () -> Database.load().groupDates.dao(),
                    "cannot connect to database!");
            List<GroupDate> groupDates = RequestException.wrap(
                    () -> dao.queryForEq("creator_id", user.getId()),
                    "cannot connect to database!");

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), groupDates);
        } catch (RequestException exception) {
            exception.apply(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            GroupDateForm form = Form.read(request, GroupDateForm.class);
            User user = Authentication.get().authenticate(request);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CREATED);
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.writeValue(response.getWriter(), user);
            response.getWriter().println("{}");
        } catch (RequestException exception) {
            exception.apply(response);
        }
    }
}

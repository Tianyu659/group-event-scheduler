package csci310.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import csci310.Authentication;
import csci310.Database;
import csci310.exception.NotImplementedError;
import csci310.exception.RequestException;
import csci310.forms.Form;
import csci310.forms.SessionForm;
import csci310.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        throw new NotImplementedError();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            SessionForm form = Form.read(request, SessionForm.class);
            Dao<User, Integer> userDao = RequestException.wrap(
                    () -> Database.load().users.dao(),
                    "cannot connect to database!");
            List<User> results = RequestException.wrap(
                    () -> userDao.queryForFieldValues(Map.of("username", form.getUsername())),
                    "cannot query database!");

            if (results.size() > 0 && results.get(0).comparePassword(form.getPassword())) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setContentType("application/json");
                Map<String, Object> data = Map.of(
                        "token", Authentication.get().key(results.get(0)),
                        "user", results.get(0));
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(response.getWriter(), data);
            } else {
                throw new RequestException(HttpServletResponse.SC_BAD_REQUEST, "incorrect credentials!");
            }
        } catch (RequestException exception) {
            exception.apply(response);
        }
    }
}

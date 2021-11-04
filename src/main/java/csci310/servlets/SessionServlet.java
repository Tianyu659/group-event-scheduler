package csci310.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import csci310.Authentication;
import csci310.Database;
import csci310.exception.RequestException;
import csci310.forms.Form;
import csci310.forms.SessionForm;
import csci310.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            SessionForm form = Form.read(request, SessionForm.class);
            Dao<User, Integer> userDao = RequestException.wrap(
                    () -> DaoManager.createDao(Database.connect(), User.class),
                    "cannot connect to database!");
            List<User> results = RequestException.wrap(
                    () -> userDao.queryForFieldValues(Map.of("username", form.getUsername())),
                    "cannot query database!");

            if (results.size() > 0 && results.get(0).comparePassword(form.getPassword())) {
                response.setStatus(201);
                response.setContentType("application/json");
                Map<String, Object> data = Map.of(
                        "token", Authentication.get().key(results.get(0)),
                        "user", results.get(0));
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(response.getWriter(), data);
            } else {
                throw new RequestException(400, "incorrect credentials!");
            }
        } catch (RequestException exception) {
            exception.apply(response);
        }
    }
}

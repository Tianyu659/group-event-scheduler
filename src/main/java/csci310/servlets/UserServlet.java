package csci310.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import csci310.Authentication;
import csci310.Database;
import csci310.exception.RequestException;
import csci310.forms.Form;
import csci310.forms.UserForm;
import csci310.models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			Authentication.get().authenticate(request);

			Dao<User, Integer> userDao = RequestException.wrap(
					() -> Database.load().users.dao(),
					"cannot connect to database!");
			List<User> users =  RequestException.wrap(
					userDao::queryForAll,
					"cannot connect to database");

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(response.getWriter(), users);
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (RequestException exception) {
			exception.apply(response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			UserForm form = Form.read(request, UserForm.class);
			Dao<User, Integer> userDao = RequestException.wrap(
					() -> Database.load().users.dao(),
					"cannot connect to database!");
			User user = form.validate();

			boolean exists = RequestException.wrap(
					() -> !userDao.queryForEq("username", user.getUsername()).isEmpty(),
					"cannot connect to database!");
			if (exists) {
				throw new RequestException(
						HttpServletResponse.SC_BAD_REQUEST,
						"username is already taken");
			}

			RequestException.wrap(
					() -> userDao.create(user),
					"cannot connect to database!");

			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_CREATED);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(response.getWriter(), user);
		} catch (RequestException exception) {
			exception.apply(response);
		}
	}
}

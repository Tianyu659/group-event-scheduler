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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			User user = Authentication.get().authenticate(request);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(response.getWriter(), user);
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

			try {
				userDao.create(user);
			} catch (SQLException exception) {
				throw new RequestException(
						HttpServletResponse.SC_BAD_REQUEST,
						"could not create user account as specified: " + exception);
			}

			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_CREATED);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(response.getWriter(), user);
		} catch (RequestException exception) {
			exception.apply(response);
		}
	}
}

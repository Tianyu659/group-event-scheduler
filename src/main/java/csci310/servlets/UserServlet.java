package csci310.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import csci310.Authentication;
import csci310.Database;
import csci310.api.Path;
import csci310.exception.RequestException;
import csci310.forms.BlackoutForm;
import csci310.forms.BlockForm;
import csci310.forms.Form;
import csci310.forms.UserForm;
import csci310.models.Blackout;
import csci310.models.Block;
import csci310.models.User;

import java.io.IOException;
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
			List<User> users = RequestException.wrap(
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
			Path path = new Path(request.getPathInfo());
			if (path.size() == 0) {
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
			} else if (path.size() == 2) {
				User user = Authentication.get().authenticate(request);
				if (path.at(1).equals("blocks")) {
					BlockForm form = Form.read(request, BlockForm.class);
					Dao<User, Integer> userDao = RequestException.wrap(
							() -> Database.load().users.dao(),
							"cannot connect to database!");
					List<User> users = RequestException.wrap(
							() -> userDao.queryForEq("username", form.getUsername()),
							"cannot connect to database!");

					if (users.size() > 0) {
						Dao<Block, Integer> blockDao = RequestException.wrap(
								() -> Database.load().blocks.dao(),
								"cannot connect to database");
						Block block = new Block();
						block.setCreator(user);
						block.setBlocked(users.get(0));
						RequestException.wrap(
								() -> blockDao.create(block),
								"cannot connect to database");
						response.setStatus(HttpServletResponse.SC_CREATED);
					} else {
						response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					}
					response.setContentType("application/json");
					response.getWriter().println("{}");
				} else if (path.at(1).equals("blackouts")) {
					BlackoutForm form = Form.read(request, BlackoutForm.class);
					Dao<Blackout, Integer> blackoutDao = RequestException.wrap(
							() -> Database.load().blackouts.dao(),
							"cannot connect to database");
					Blackout blackout = new Blackout();
					blackout.setCreator(user);
					blackout.setStart(form.getStart());
					blackout.setEnd(form.getEnd());
					RequestException.wrap(
							() -> blackoutDao.create(blackout),
							"cannot connect to database");
					response.setContentType("application/json");
					response.setStatus(HttpServletResponse.SC_CREATED);
					response.getWriter().println("{}");
				} else {
					throw new RequestException(HttpServletResponse.SC_NOT_FOUND, "not found!");
				}
			} else {
				throw new RequestException(HttpServletResponse.SC_NOT_FOUND, "not found!");
			}
		} catch (RequestException exception) {
			exception.apply(response);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			Path path = new Path(request.getPathInfo());
			Authentication.get().authenticate(request);

			if (path.size() == 3) {
				if (path.at(1).equals("blocks")) {
					Dao<Block, Integer> blockDao = RequestException.wrap(
							() -> Database.load().blocks.dao(),
							"cannot connect to database!");
					RequestException.wrap(
							() -> blockDao.deleteById(path.id(2)),
							"cannot connect to database");
					response.setContentType("application/json");
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					response.getWriter().println("{}");
				} else if (path.at(1).equals("blackouts")) {
					Dao<Blackout, Integer> blackoutDao = RequestException.wrap(
							() -> Database.load().blackouts.dao(),
							"cannot connect to database!");
					RequestException.wrap(
							() -> blackoutDao.deleteById(path.id(2)),
							"cannot connect to database");
					response.setContentType("application/json");
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					response.getWriter().println("{}");
				} else {
					throw new RequestException(HttpServletResponse.SC_NOT_FOUND, "not found!");
				}
			} else {
				throw new RequestException(HttpServletResponse.SC_NOT_FOUND, "not found!");
			}
		} catch (RequestException exception) {
			exception.apply(response);
		}
	}
}
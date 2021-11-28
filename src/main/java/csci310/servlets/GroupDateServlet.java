package csci310.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import csci310.Authentication;
import csci310.Database;
import csci310.api.Path;
import csci310.exception.NotImplementedError;
import csci310.exception.RequestException;
import csci310.forms.Form;
import csci310.forms.GroupDateEventForm;
import csci310.forms.GroupDateForm;
import csci310.forms.InvitationForm;
import csci310.models.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
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

            Path path = new Path(request.getPathInfo());

            if (path.size() == 0) {
                List<GroupDate> groupDates = RequestException.wrap(
                        () -> dao.queryForEq("creator_id", user.getId()),
                        "cannot connect to database!");
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(response.getWriter(), groupDates);
            } else if (path.size() == 1) {
                int id = path.id(0);
                GroupDate groupDate = RequestException.wrap(
                        () -> dao.queryForId(id),
                        "cannot connect to database!");
                if (groupDate == null || groupDate.getCreator().getId() != user.getId()) {
                    throw new RequestException(404, "group date does not exist!");
                } else {
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_OK);
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.writeValue(response.getWriter(), groupDate);
                }
            } else if (path.size() == 2) {
                int id = path.id(0);
                GroupDate groupDate = RequestException.wrap(
                        () -> dao.queryForId(id),
                        "cannot connect to database!");
                if (groupDate == null || groupDate.getCreator().getId() != user.getId()) {
                    throw new RequestException(404, "group date does not exist!");
                } else {
                    List<Invitation> invitations = RequestException.wrap(
                            groupDate::getInvitations,
                            "cannot connect to database!");
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_OK);
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.writeValue(response.getWriter(), invitations);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (RequestException exception) {
            exception.apply(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            GroupDateForm form = Form.read(request, GroupDateForm.class);
            User user = Authentication.get().authenticate(request);

            Dao<GroupDate, Integer> dao = RequestException.wrap(
                    () -> Database.load().groupDates.dao(),
                    "cannot connect to database!");
            Dao<GroupDateEvent, Integer> eventDao = RequestException.wrap(
                    () -> Database.load().groupDateEvents.dao(),
                    "cannot connect to database!");
            Dao<User, Integer> userDao = RequestException.wrap(
                    () -> Database.load().users.dao(),
                    "cannot connect to database!");
            Dao<Invitation, Integer> invitationDao = RequestException.wrap(
                    () -> Database.load().invitations.dao(),
                    "cannot connect to database!");

            GroupDate groupDate = form.validate();
            groupDate.setCreator(user);
            RequestException.wrap(
                    () -> dao.create(groupDate),
                    "cannot connect to database!");

            for (GroupDateEventForm eventForm : form.getEventForms()) {
                GroupDateEvent groupDateEvent = eventForm.validate(groupDate);
                RequestException.wrap(
                        () -> eventDao.create(groupDateEvent),
                        "cannot connect to database!");
            }

            for (InvitationForm invitationForm : form.getInvitationForms()) {
                List<User> invitee = RequestException.wrap(
                        () -> userDao.queryForEq("username", invitationForm.getUsername()),
                        "cannot connect to database!");
                if (!invitee.isEmpty()) {
                    Invitation invitation = new Invitation();
                    invitation.setGroupDate(groupDate);
                    invitation.setUser(invitee.get(0));
                    RequestException.wrap(
                            () -> invitationDao.create(invitation),
                            "cannot connect to database!");
                }
            }

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CREATED);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), groupDate);
        } catch (RequestException exception) {
            exception.apply(response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Authentication.get().authenticate(request);
            Path path = new Path(request.getPathInfo());

            if (path.size() == 1) {
                // TODO: should actually check if the requesting user is the creator lmfao
                Dao<GroupDate, Integer> dao = RequestException.wrap(
                        () -> Database.load().groupDates.dao(),
                        "cannot connect to database!");
                int id = path.id(0);
                RequestException.wrap(
                        () -> dao.deleteById(id),
                        "cannot connect to database!");
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                response.getWriter().println("{}");
            } else if (path.size() == 3) {
                if (path.at(1).equals("invitations")) {
                    Dao<Invitation, Integer> dao = RequestException.wrap(
                            () -> Database.load().invitations.dao(),
                            "cannot connect to database!");
                    int id = path.id(2);
                    RequestException.wrap(
                            () -> dao.deleteById(id),
                            "cannot connect to database!");
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    response.getWriter().println("{}");
                } else if (path.at(1).equals("events")) {
                    Dao<GroupDateEvent, Integer> dao = RequestException.wrap(
                            () -> Database.load().groupDateEvents.dao(),
                            "cannot connect to database!");
                    int id = path.id(2);
                    RequestException.wrap(
                            () -> dao.deleteById(id),
                            "cannot connect to database!");
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

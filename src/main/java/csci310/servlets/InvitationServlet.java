package csci310.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import csci310.Authentication;
import csci310.Database;
import csci310.api.Path;
import csci310.exception.RequestException;
import csci310.forms.Form;
import csci310.forms.InvitationEventResponseForm;
import csci310.forms.InvitationResponseForm;
import csci310.models.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class InvitationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            User user = Authentication.get().authenticate(request);
            Dao<Invitation, Integer> dao = RequestException.wrap(
                    () -> Database.load().invitations.dao(),
                    "cannot connect to database!");

            List<Invitation> invitations = RequestException.wrap(
                    () -> dao.queryForEq("user_id", user.getId()),
                    "cannot connect to database!");
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), invitations);
        } catch (RequestException exception) {
            exception.apply(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // To do: technically we should check if we're responding to the right response
            Authentication.get().authenticate(request);
            InvitationResponseForm form = Form.read(request, InvitationResponseForm.class);

            Dao<Invitation, Integer> dao = RequestException.wrap(
                    () -> Database.load().invitations.dao(),
                    "cannot connect to database!");

            Path path = new Path(request.getPathInfo());
            if (path.size() == 2) {
                int id = path.id(0);
                Invitation invitation = RequestException.wrap(
                        () -> dao.queryForId(id),
                        "cannot connect to database!");

                Dao<GroupDateEvent, Integer> groupDateEventDao = RequestException.wrap(
                        () -> Database.load().groupDateEvents.dao(),
                        "cannot connect to database!");
                Dao<InvitationResponse, Integer> invitationResponseDao = RequestException.wrap(
                        () -> Database.load().invitationResponses.dao(),
                        "cannot connect to database!");
                Dao<InvitationEventResponse, Integer> invitationEventResponseDao = RequestException.wrap(
                        () -> Database.load().invitationEventResponses.dao(),
                        "cannot connect to database!");

                InvitationResponse invitationResponse = new InvitationResponse();
                invitationResponse.setAccepted(form.accepted);
                invitationResponse.setInvitation(invitation);
                RequestException.wrap(
                        () -> invitationResponseDao.create(invitationResponse),
                        "cannot connect to database!");

                for (InvitationEventResponseForm eventResponseForm : form.events) {
                    GroupDateEvent groupDateEvent = RequestException.wrap(
                            () -> groupDateEventDao.queryForId(eventResponseForm.eventId),
                            "cannot connect to database!");

                    InvitationEventResponse invitationEventResponse = new InvitationEventResponse();
                    invitationEventResponse.setInvitationResponse(invitationResponse);
                    invitationEventResponse.setEvent(groupDateEvent);
                    invitationEventResponse.setAvailable(eventResponseForm.available);
                    invitationEventResponse.setInterest(eventResponseForm.interest);
                    RequestException.wrap(
                            () -> invitationEventResponseDao.create(invitationEventResponse),
                            "cannot connect to database!");
                }
            }

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().println("{}");
        } catch (RequestException exception) {
            exception.printStackTrace();
            exception.apply(response);
        }
    }
}

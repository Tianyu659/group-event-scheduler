package csci310.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import csci310.Database;

import java.sql.SQLException;
import java.util.List;

@DatabaseTable(tableName = "invitationResponse")
public class InvitationResponse {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, foreign = true, foreignColumnName = "id")
    private Invitation invitation;

    @DatabaseField()
    private boolean accepted;

    public int getId() {
        return id;
    }

    @JsonIgnore
    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @JsonProperty("events")
    public List<InvitationEventResponse> getEventResponses() throws SQLException {
        Dao<InvitationEventResponse, Integer> dao = Database.load().invitationEventResponses.dao();
        return dao.queryForEq("invitationResponse_id", this.getId());
    }
}

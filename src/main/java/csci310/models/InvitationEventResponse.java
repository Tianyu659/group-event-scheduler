package csci310.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "invitationEventResponse")
public class InvitationEventResponse {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, foreign = true, foreignColumnName = "id")
    private GroupDateEvent event;

    @DatabaseField(canBeNull = false, foreign = true, foreignColumnName = "id")
    private InvitationResponse invitationResponse;

    @DatabaseField()
    private boolean available;

    @DatabaseField()
    private int interest;

    public int getId() {
        return id;
    }

    public void setEvent(GroupDateEvent event) {
        this.event = event;
    }

    public GroupDateEvent getEvent() {
        return event;
    }

    public void setInvitationResponse(InvitationResponse invitationResponse) {
        this.invitationResponse = invitationResponse;
    }

    public InvitationResponse getInvitationResponse() {
        return invitationResponse;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public int getInterest() {
        return interest;
    }
}

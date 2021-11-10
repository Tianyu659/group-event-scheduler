package csci310.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "invitationEventResponse")
public class InvitationEventResponse {
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false, foreign = true, foreignColumnName = "id")
    public GroupDateEvent event;

    @DatabaseField(canBeNull = false, foreign = true, foreignColumnName = "id")
    public InvitationResponse invitationResponse;

    @DatabaseField()
    public boolean available;

    @DatabaseField()
    public int interest;
}

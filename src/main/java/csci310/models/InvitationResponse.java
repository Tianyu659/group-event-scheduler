package csci310.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "invitationResponse")
public class InvitationResponse {
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false, foreign = true, foreignColumnName = "id")
    public Invitation invitation;
}

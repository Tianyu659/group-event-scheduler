package csci310.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "groupDateInvitations")
public class Invitation {
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false, foreign = true, foreignColumnName = "id")
    public GroupDate groupDate;

    @DatabaseField(canBeNull = false, foreign = true, foreignColumnName = "id")
    public User user;
}

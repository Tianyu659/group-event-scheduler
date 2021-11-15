package csci310.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    public int getId() {
        return id;
    }

    public void setGroupDate(GroupDate groupDate) {
        this.groupDate = groupDate;
    }

    @JsonProperty("groupDate")
    public GroupDate getGroupDate() {
        return groupDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}

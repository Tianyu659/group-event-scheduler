package csci310.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "blocks")
public class Block {
    @DatabaseField(generatedId = true, unique = true)
    private int id;

    @DatabaseField(foreign = true)
    @JsonIgnore
    private User creator;

    @DatabaseField(foreign = true)
    private User blocked;

    public int getId() {
        return id;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getCreator() {
        return creator;
    }

    public void setBlocked(User blocked) {
        this.blocked = blocked;
    }

    public User getBlocked() {
        return blocked;
    }
}

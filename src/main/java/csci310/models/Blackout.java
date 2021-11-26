package csci310.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "blackouts")
public class Blackout {
    @DatabaseField(generatedId = true, unique = true)
    private int id;

    @DatabaseField(foreign = true)
    private User creator;

    @DatabaseField()
    private long start;

    @DatabaseField()
    private long end;

    public int getId() {
        return id;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getCreator() {
        return creator;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getStart() {
        return start;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getEnd() {
        return end;
    }
}

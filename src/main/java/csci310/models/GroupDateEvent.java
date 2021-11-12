package csci310.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "groupDateEvents")
public class GroupDateEvent {
    @DatabaseField(generatedId = true, unique = true)
    private int id;

    @DatabaseField(canBeNull = false, foreign = true)
    private GroupDate groupDate;

    @DatabaseField()
    private String name;

    @DatabaseField()
    private String description;

    @DatabaseField()
    private long time;

    @DatabaseField()
    private int duration;

    public int getId() {
        return id;
    }

    public void setGroupDate(GroupDate groupDate) {
        this.groupDate = groupDate;
    }

    public GroupDate getGroupDate() {
        return groupDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}

package csci310.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import csci310.Database;

import java.sql.SQLException;
import java.util.List;

@DatabaseTable(tableName = "groupDates")
public class GroupDate {
    @DatabaseField(generatedId = true, unique = true)
    private int id;

    @DatabaseField(foreign = true)
    private User creator;

    @DatabaseField()
    private boolean live;

    @DatabaseField()
    private String name;

    @DatabaseField()
    private String description;

    public int getId() {
        return id;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getCreator() {
        return creator;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isLive() {
        return live;
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

    @JsonProperty("events")
    public List<GroupDateEvent> getEvents() throws SQLException {
        Dao<GroupDateEvent, Integer> dao = Database.load().groupDateEvents.dao();
        return dao.queryForEq("groupDate_id", this.getId());
    }
    
    public GroupDateEvent selectEvent() throws SQLException {
        return null;
    }
}

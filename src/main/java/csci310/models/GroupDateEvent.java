package csci310.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import csci310.Database;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import java.util.Objects;

@DatabaseTable(tableName = "groupDateEvents")
public class GroupDateEvent {
    @DatabaseField(generatedId = true, unique = true)
    private int id;

    @JsonIgnore
    @DatabaseField(foreign = true)
    private GroupDate groupDate;

    @DatabaseField()
    private String eid;

    @DatabaseField()
    private String url;

    @DatabaseField()
    private String name;

    @DatabaseField()
    private String description;

    @DatabaseField()
    private String location;

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

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getEid() {
        return eid;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
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

    public Vector<Integer> getInterest() throws SQLException {
        Dao<InvitationEventResponse, Integer> dao = Database.load().invitationEventResponses.dao();
        List<InvitationEventResponse> responses = dao.queryForEq("event_id", this.getId());
        Vector<Integer> interest = new Vector<>();
        for (InvitationEventResponse response : responses) {
            if (response.isAvailable()) {
                interest.add(response.getInterest());
            } else {
                interest.add(-1);
            }
        }
        return interest;
    }

    
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        return eid.contentEquals(((GroupDateEvent)o).eid);
    }
    
    @Override public int hashCode() {return eid.hashCode();}
}

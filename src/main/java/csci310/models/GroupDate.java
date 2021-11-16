package csci310.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import csci310.Database;

import java.sql.SQLException;
import java.util.*;

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
    
    @JsonIgnore
    public List<Invitation> getInvitations() throws SQLException {
        Dao<Invitation, Integer> dao = Database.load().invitations.dao();
        return dao.queryForEq("groupDate_id", this.getId());
    }
    
    public GroupDateEvent selectEvent() throws SQLException {
        final List<InvitationEventResponse> responses;
        {
            final List<Invitation> invitations = getInvitations();
            responses = invitations.get(0).getResponse().getEventResponses();
            invitations.remove(0);
            for(final Invitation i : invitations)
                responses.addAll(i.getResponse().getEventResponses());
        }
        {
            final Set<GroupDateEvent> impossible = new HashSet<>(responses.size());
            for(final InvitationEventResponse r : responses)
                if(!r.isAvailable()) // Assumes exactly 1 response per user-event pair.
                    impossible.add(r.getEvent());
            responses.removeIf(r -> impossible.contains(r.getEvent()));
        }
        if(responses.isEmpty()) return null;
        final Map<GroupDateEvent,Integer> events = new HashMap<>(responses.size());
        for(final InvitationEventResponse r : responses)
            events.put(r.getEvent(),events.getOrDefault(r.getEvent(),0)+r.getInterest());
        final int s = events.size();
        double interest = 0.;
        GroupDateEvent out = null;
        for(final Map.Entry<GroupDateEvent,Integer> e : events.entrySet()) {
            final double i = (double)e.getValue() / s;
            if(interest < i) {
                interest = i;
                out = e.getKey();
            }
        }
        return out;
    }
}

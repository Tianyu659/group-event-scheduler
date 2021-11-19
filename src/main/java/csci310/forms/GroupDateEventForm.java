package csci310.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import csci310.models.GroupDate;
import csci310.models.GroupDateEvent;

public class GroupDateEventForm extends Form {
    private final String name;
    private final String eid;
    private final String url;
    private final String description;
    private final String location;
    private final long time;
    private final int duration;

    @JsonCreator
    public GroupDateEventForm(
            @JsonProperty(value = "eid", required = true) String eid,
            @JsonProperty(value = "url", required = true) String url,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "description", required = true) String description,
            @JsonProperty(value = "location", required = true) String location,
            @JsonProperty(value = "time", required = true) long time,
            @JsonProperty(value = "duration", required = true) int duration
    ) {
        this.eid = eid;
        this.url = url;
        this.name = name;
        this.description = description;
        this.location = location;
        this.time = time;
        this.duration = duration;
    }

    public GroupDateEvent validate(GroupDate groupDate) {
        GroupDateEvent groupDateEvent = new GroupDateEvent();
        groupDateEvent.setGroupDate(groupDate);
        groupDateEvent.setEid(this.eid);
        groupDateEvent.setUrl(this.url);
        groupDateEvent.setName(this.name);
        groupDateEvent.setDescription(this.description);
        groupDateEvent.setLocation(this.location);
        groupDateEvent.setTime(this.time);
        groupDateEvent.setDuration(this.duration);
        return groupDateEvent;
    }
}

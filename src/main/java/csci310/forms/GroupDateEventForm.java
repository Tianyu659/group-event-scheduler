package csci310.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupDateEventForm extends Form {
    private final String name;
    private final String description;
    private final String location;
    private final long time;
    private final int duration;

    @JsonCreator
    public GroupDateEventForm(
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "description", required = true) String description,
            @JsonProperty(value = "location", required = true) String location,
            @JsonProperty(value = "time", required = true) long time,
            @JsonProperty(value = "duration", required = true) int duration
    ) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.time = time;
        this.duration = duration;
    }
}

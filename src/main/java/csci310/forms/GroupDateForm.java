package csci310.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Vector;

public class GroupDateForm extends Form {
    private final String name;
    private final String description;
    private final Vector<GroupDateEventForm> events;

    @JsonCreator
    public GroupDateForm(
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "description", required = true) String description,
            @JsonProperty(value = "events", required = true) Vector<GroupDateEventForm> events
    ) {
        this.name = name;
        this.description = description;
        this.events = events;
    }
}

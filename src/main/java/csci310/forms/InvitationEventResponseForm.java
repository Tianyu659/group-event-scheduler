package csci310.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InvitationEventResponseForm {
    public final int eventId;
    public final boolean available;
    public final int interest;

    @JsonCreator
    public InvitationEventResponseForm(
            @JsonProperty(value = "eventId", required = true) int eventId,
            @JsonProperty(value = "available", required = true) boolean available,
            @JsonProperty(value = "interest", required = true) int interest
    ) {
        this.eventId = eventId;
        this.available = available;
        this.interest = interest;
    }
}

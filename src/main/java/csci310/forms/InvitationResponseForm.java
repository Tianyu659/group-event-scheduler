package csci310.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Vector;

public class InvitationResponseForm extends Form {
    public final boolean accepted;
    public final Vector<InvitationEventResponseForm> events;

    @JsonCreator
    public InvitationResponseForm(
            @JsonProperty(value = "accepted", required = true) boolean accepted,
            @JsonProperty(value = "events", required = true) Vector<InvitationEventResponseForm> events
    ) {
        this.accepted = accepted;
        this.events = events;
    }
}

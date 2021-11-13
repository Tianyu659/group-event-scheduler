package csci310.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InvitationForm extends Form  {
    private final String username;

    @JsonCreator
    public InvitationForm(
            @JsonProperty(value = "username", required = true) String username
    ) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

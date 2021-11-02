package csci310.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionForm extends Form {
    private final String username;
    private final String password;

    @JsonCreator
    public SessionForm(
            @JsonProperty(value = "username", required = true) String username,
            @JsonProperty(value = "password", required = true) String password
    ) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}

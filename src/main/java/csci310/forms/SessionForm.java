package csci310.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import csci310.exception.NotImplementedError;

public class SessionForm extends Form {
    private final String username;
    private final String password;

    @JsonCreator
    public SessionForm(
            @JsonProperty(value = "username", required = true) String username,
            @JsonProperty(value = "password", required = true) String password
    ) {
        throw new NotImplementedError();
    }

    public String getUsername() {
        throw new NotImplementedError();
    }

    public String getPassword() {
        throw new NotImplementedError();
    }
}

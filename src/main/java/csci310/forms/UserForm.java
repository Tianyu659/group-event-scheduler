package csci310.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import csci310.exception.NotImplementedError;
import csci310.models.User;

public class UserForm extends Form {
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;

    @JsonCreator
    public UserForm(
            @JsonProperty(value = "username", required = true) String username,
            @JsonProperty(value = "password", required = true) String password,
            @JsonProperty(value = "firstName", required = true) String firstName,
            @JsonProperty(value = "lastName", required = true) String lastName
    ) {
        throw new NotImplementedError();
    }

    public User validate() {
        throw new NotImplementedError();
    }
}

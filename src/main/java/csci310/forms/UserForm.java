package csci310.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import csci310.exception.RequestException;
import csci310.models.User;

public class UserForm extends Form {
    private final String username;
    private final String password;
    private final String password2;
    private final String firstName;
    private final String lastName;

    @JsonCreator
    public UserForm(
            @JsonProperty(value = "username", required = true) String username,
            @JsonProperty(value = "password", required = true) String password,
            @JsonProperty(value = "password2", required = true) String password2,
            @JsonProperty(value = "firstName", required = true) String firstName,
            @JsonProperty(value = "lastName", required = true) String lastName
    ) {
        this.username = username;
        this.password = password;
        this.password2 = password2;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User validate() throws RequestException {

        if(!password.equals(password2))
        {
            throw new RequestException(403, "Confirm Password must match entered Password");
        }
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        return user;
    }
}

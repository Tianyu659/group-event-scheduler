package csci310.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import csci310.exception.NotImplementedError;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@DatabaseTable(tableName = "users")
public class User {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(unique = true)
    private String username;

    @JsonIgnore
    @DatabaseField
    private String password;

    @DatabaseField
    private String firstName;

    @DatabaseField
    private String lastName;

    public int getId() {
        throw new NotImplementedError();
    }

    public void setUsername(String username) {
        throw new NotImplementedError();
    }

    public String getUsername() {
        return this.username;
    }

    public boolean comparePassword(String password) {
        throw new NotImplementedError();
    }

    public void setPassword(String password) {
        throw new NotImplementedError();
    }

    public String getFirstName() {
        throw new NotImplementedError();
    }

    public void setFirstName(String firstName) {
        throw new NotImplementedError();
    }

    public String getLastName() {
        throw new NotImplementedError();
    }

    public void setLastName(String lastName) {
        throw new NotImplementedError();
    }
}

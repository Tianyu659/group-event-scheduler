package csci310.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
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
        return this.id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean comparePassword(String password) {
        Argon2 argon2 = Argon2Factory.create();
        char[] buffer = password.toCharArray();
        try {
            return argon2.verify(this.password, buffer);
        } finally {
            argon2.wipeArray(buffer);
        }
    }

    public void setPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();
        char[] buffer = password.toCharArray();
        try {
            this.password = argon2.hash(10, 65535, 1, buffer);
        } finally {
            argon2.wipeArray(buffer);
        }
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

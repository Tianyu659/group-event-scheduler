package csci310.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.util.Arrays;

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
    
    interface PasswordOp<T> {
        T run(Argon2 argon2,char[] bufer);
    }
    
    <T> T passwordOperation(String password,PasswordOp<T> op) {
        final Argon2 a2 = Argon2Factory.create();
        final char[] buf = password.toCharArray();
        try {return op.run(a2,buf);}
        finally {a2.wipeArray(buf);}
    }
    
    public boolean comparePassword(String password) {
        return passwordOperation(password,(a,b) -> a.verify(this.password,b));
    }
    
    public void setPassword(String password) {
        this.password = passwordOperation(password,(a,b) -> a.hash(10,65535,1,b));
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

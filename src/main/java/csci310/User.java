package csci310;

public class User {
    // member variables
    private String name;
    private String password;

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setName(String newname) {
        this.name = newname;
    }

    public void setPassword(String newpwd) {
        this.password = newpwd;
    }
}

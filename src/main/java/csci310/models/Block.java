package csci310.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import csci310.Database;

import java.sql.SQLException;

@DatabaseTable(tableName = "blocks")
public class Block {
    @DatabaseField(generatedId = true, unique = true)
    private int id;

    @DatabaseField(foreign = true)
    @JsonIgnore
    private User creator;

    @DatabaseField(foreign = true)
    @JsonIgnore
    private User blocked;

    @JsonIgnore
    private User blockedCache;

    public int getId() {
        return id;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getCreator() {
        return creator;
    }

    public void setBlocked(User blocked) {
        this.blocked = blocked;
    }

    public User getBlocked() {
        return blocked;
    }

    @JsonIgnore
    public User getBlockedCache() throws SQLException {
        if (this.blockedCache == null) {
            Dao<User, Integer> dao = Database.load().users.dao();
            this.blockedCache = dao.queryForId(this.blocked.getId());
        }
        return this.blockedCache;
    }

    public String getUserUsername() throws SQLException {
        return this.getBlockedCache().getUsername();
    }

    public String getUserFirstName() throws SQLException {
        return this.getBlockedCache().getFirstName();
    }

    public String getUserLastName() throws SQLException {
        return this.getBlockedCache().getLastName();
    }
}

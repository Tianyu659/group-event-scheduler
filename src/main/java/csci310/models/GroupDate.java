package csci310.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "groupDates")
public class GroupDate {
    @DatabaseField(generatedId = true, unique = true)
    public int id;

    @DatabaseField(foreign = true, foreignColumnName = "id")
    public User creator;

    @DatabaseField()
    public boolean live;

    @DatabaseField()
    public String name;

    @DatabaseField()
    public String description;
}

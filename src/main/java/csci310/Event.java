package csci310;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {
    private int id;
    private String title;
    private String description;
    private Date date;
    private String creator;
    private String start;
    private String end;
    private List<User> attendees = new ArrayList<>();

    // getters
    public int getEventID(){ return this.id;}
    public String getEventTitle() {
        return this.title;
    }
    public String getEventDescription() { return this.description;}
    public Date getEventDate(){ return this.date; }
    public String getEventCreator(){ return this.creator; }
    public String getEventStart(){ return this.start;}
    public String getEventEnd(){ return this.end;}
    public List<User> getAttendees() {return this.attendees;}

    // setters
    public void setEventID(int newid) { this.id = newid;}
    public void setTitle(String newtitle) {this.title = newtitle;}
    public void setDescription(String newdescription) {this.description = newdescription;}
    public void setCreator(String newcreator) {this.creator = newcreator;}
    public void setStart(String newstart) {this.start = newstart;}
    public void setEnd(String newend) {this.end = newend;}
    public void setDate(Date newdate) {this.date = newdate;}
    public void setAttendees(List<User> newattendees) {this.attendees = newattendees;}

    // add a user to the list of attendees
    public void addAttendee(User user_object) {
        this.attendees.add(user_object);
    }

    // remove a user from the list of attendees
    public void removeAttendee(User user_object) {
        this.attendees.remove(user_object);
    }

}

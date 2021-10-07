package csci310;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {
    private String title;
    private String description;
    private Date date;
    private String creator;
    private String start;
    private String end;
    private List<String> attendees = new ArrayList<>();

    // getters
    public String getTitle() {
        return this.title;
    }
    public String getDescription() { return this.description;}
    public Date getDate(){ return this.date; }
    public String getCreator(){ return this.creator; }
    public String getStart(){ return this.start;}
    public String getEnd(){ return this.end;}
    public List<String> getAttendees() {return this.attendees;}

    // setters
    public void setTitle(String newtitle) {this.title = newtitle;}
    public void setDescription(String newdescription) {this.description = newdescription;}
    public void setCreator(String newcreator) {this.creator = newcreator;}
    public void setStart(String newstart) {this.start = newstart;}
    public void setEnd(String newend) {this.end = newend;}
    public void setDate(Date newdate) {this.date = newdate;}
    public void setAttendees(List<String> newattendees) {this.attendees = newattendees;}

    // methods
    public void addAttendee(String attendee_name) {
        this.attendees.add(attendee_name);
    }

    public void removeAttendee(String attendee_name) {
        this.attendees.remove(attendee_name);
    }

}

package csci310;

import org.junit.Assert;
import org.junit.Test;

public class EventTest {

    @Test
    public void testaddAttendee() {
        Event event = new Event();
        int num_attendees = event.getAttendees().size();
        event.addAttendee("Tommy Trojan");
        int new_num_attendees = event.getAttendees().size();
        Assert.assertTrue(new_num_attendees>num_attendees);
    }

    @Test
    public void testremoveAttendee() {
        Event event = new Event();
        event.addAttendee("Tommy Trojan");
        event.addAttendee("Hecuba");
        int num_attendees = event.getAttendees().size();
        event.removeAttendee("Tommy Trojan");
        int new_num_attendees = event.getAttendees().size();
        Assert.assertTrue(new_num_attendees<num_attendees);
    }

}

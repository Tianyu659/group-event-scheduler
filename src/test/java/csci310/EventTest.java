package csci310;

import org.junit.Assert;
import org.junit.Test;

public class EventTest {

    @Test
    public void testaddAttendee() {
        Event event = new Event();
        int num_attendees = event.getAttendees().size();
        User user1 = new User();
        user1.setName("Tommy Trojan");
        event.addAttendee(user1);
        int new_num_attendees = event.getAttendees().size();
        Assert.assertTrue(new_num_attendees>num_attendees);
    }

    @Test
    public void testremoveAttendee() {
        Event event = new Event();
        User user1 = new User();
        user1.setName("Tommy Trojan");
        User user2 = new User();
        user2.setName("Hecuba");
        event.addAttendee(user1);
        event.addAttendee(user2);
        int num_attendees = event.getAttendees().size();
        event.removeAttendee(user1);
        int new_num_attendees = event.getAttendees().size();
        Assert.assertTrue(new_num_attendees<num_attendees);
    }

}

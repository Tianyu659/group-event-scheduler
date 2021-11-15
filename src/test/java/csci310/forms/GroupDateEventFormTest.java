package csci310.forms;

import csci310.models.GroupDate;
import csci310.models.GroupDateEvent;
import org.junit.Assert;
import org.junit.Test;

public class GroupDateEventFormTest {
    @Test
    public void testValidate() {
        GroupDate groupDate = new GroupDate();
        GroupDateEventForm groupDateEventForm = new GroupDateEventForm(
                "",
                "",
                "name",
                "description",
                "location",
                69,
                420);
        GroupDateEvent event = groupDateEventForm.validate(groupDate);
        Assert.assertEquals("name", event.getName());
        Assert.assertEquals("description", event.getDescription());
        Assert.assertEquals("location", event.getLocation());
        Assert.assertEquals(69, event.getTime());
        Assert.assertEquals(420, event.getDuration());
    }
}

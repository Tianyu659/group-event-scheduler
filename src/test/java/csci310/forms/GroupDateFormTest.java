package csci310.forms;

import csci310.models.GroupDate;
import org.junit.Assert;
import org.junit.Test;

import java.util.Vector;

public class GroupDateFormTest {
    @Test
    public void testValidate() {
        Vector<GroupDateEventForm> eventForms = new Vector<>();
        GroupDateForm groupDateForm = new GroupDateForm(
                "name",
                "description",
                eventForms,
                new Vector<>());
        GroupDate groupDate = groupDateForm.validate();
        Assert.assertEquals(groupDate.getName(), "name");
        Assert.assertEquals(groupDate.getDescription(), "description");
    }

    @Test
    public void testGetEventForms() {
        Vector<GroupDateEventForm> eventForms = new Vector<>();
        GroupDateForm groupDateForm = new GroupDateForm(
                "name",
                "description",
                eventForms,
                new Vector<>());
        Assert.assertEquals(groupDateForm.getEventForms(), eventForms);
    }
}

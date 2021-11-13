package csci310.forms;

import csci310.models.GroupDate;
import csci310.models.GroupDateEvent;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.util.Vector;

public class GroupDateFormTest {
    @Test
    public void testValidate() {
        Vector<GroupDateEventForm> eventForms = new Vector<>();
        GroupDateForm groupDateForm = new GroupDateForm(
                "name",
                "description",
                eventForms);
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
                eventForms);
        Assert.assertEquals(groupDateForm.getEventForms(), eventForms);
    }
}

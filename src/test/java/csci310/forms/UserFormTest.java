package csci310.forms;

import csci310.exception.RequestException;
import csci310.models.User;
import org.junit.Assert;
import org.junit.Test;

public class UserFormTest {
    @Test
    public void testValidate() throws RequestException {
        UserForm form = new UserForm("noahbkim", "asdfjkl;", "Noah", "Kim");
        User user = form.validate();
        Assert.assertEquals(user.getUsername(), "noahbkim");
        Assert.assertTrue(user.comparePassword("asdfjkl;"));
        Assert.assertEquals(user.getFirstName(), "Noah");
        Assert.assertEquals(user.getLastName(), "Kim");
    }
}

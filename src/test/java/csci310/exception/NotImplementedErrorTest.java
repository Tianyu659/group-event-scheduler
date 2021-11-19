package csci310.exception;

import org.junit.Assert;
import org.junit.Test;

public class NotImplementedErrorTest {
    @Test
    public void testConstructor() {
        try {
            throw new NotImplementedError();
        } catch (NotImplementedError e) {
            Assert.assertNotNull(e);
        }
    }
}

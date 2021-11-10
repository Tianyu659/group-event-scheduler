package csci310.exception;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class ConfigurationExceptionTest {
    @Test
    public void testWrap() {
        try {
            ConfigurationException.wrap(() -> {
                throw new SQLException();
            }, "oops!");
        } catch (ConfigurationException e) {
            Assert.assertNotNull(e);
        }
    }
}

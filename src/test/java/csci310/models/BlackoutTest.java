package csci310.models;

import org.junit.Assert;
import org.junit.Test;

public class BlackoutTest {
    @Test
    public void testGetStart() {
        Blackout blackout = new Blackout();
        blackout.setStart(100);
        Assert.assertEquals(100, blackout.getStart());
    }

    @Test
    public void testGetEnd() {
        Blackout blackout = new Blackout();
        blackout.setEnd(100);
        Assert.assertEquals(100, blackout.getEnd());
    }
}

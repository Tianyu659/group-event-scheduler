package csci310.api;

import csci310.exception.RequestException;
import org.junit.Assert;
import org.junit.Test;

public class PathTest {
    @Test
    public void testPath() {
        Path path = new Path(new String[] {"a", "b"});
        Assert.assertNotNull(path);
    }

    @Test
    public void testPathFrom() {
        Path path = new Path("a/b");
        Assert.assertEquals("a", path.at(0));
        Assert.assertEquals("b", path.at(1));
    }

    @Test
    public void testPathFromPrefix() {
        Path path = new Path("/a/b");
        Assert.assertEquals("a", path.at(0));
        Assert.assertEquals("b", path.at(1));
    }

    @Test
    public void testPathFromNone() {
        Path path = new Path("");
        Assert.assertEquals(0, path.size());
    }

    @Test
    public void testPathFromNonePrefix() {
        Path path = new Path("/");
        Assert.assertEquals(0, path.size());
    }

    @Test
    public void testSize() {
        Path path = new Path("/a/b");
        Assert.assertEquals(2, path.size());
    }

    @Test
    public void testAt() {
        Path path = new Path("a");
        Assert.assertEquals("a", path.at(0));
    }

    @Test
    public void testId() throws RequestException {
        Path path = new Path("/100/");
        Assert.assertEquals(100, path.id(0));
    }

    @Test
    public void testIdInvalid() {
        Path path = new Path("/a/");
        try {
            Assert.assertEquals(100, path.id(0));
            Assert.fail();
        } catch (RequestException exception) {
            Assert.assertNotNull(exception);
        }
    }
}

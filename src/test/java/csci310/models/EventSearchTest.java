package csci310.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EventSearchTest {
    @Test
    public void testsanitize() {
        assertEquals("test",EventSearch.sanitize("test"));
        assertEquals("test+string",EventSearch.sanitize(" test string "));
        assertNull(EventSearch.sanitize(""));
        assertNull(EventSearch.sanitize(" "));
    }
    @Test
    public void testarray() {
        assertEquals("a",EventSearch.array("a"));
        assertEquals("a,b",EventSearch.array("a","b"));
        assertEquals("a,b,c",EventSearch.array("a","b","c"));
        assertEquals("a,b",EventSearch.array(" a "," b "," "));
        assertEquals("",EventSearch.array(" "," "));
    }
    
    @Test
    public void testtoStringEmpty() {
        EventSearch test = new EventSearch();
        assertEquals("",test.toString());
        test.put(EventSearch.Parameter.id," ");
        test.put(EventSearch.Parameter.city," "," ");
        assertEquals("",test.toString());
    }
    @Test
    public void testtoStringFull() {
        EventSearch test = new EventSearch();
        test.put(EventSearch.Parameter.id,"test");
        test.put(EventSearch.Parameter.city,"a b"," c");
        assertEquals("&id=test&city=a+b,c",test.toString());
        test.put(EventSearch.Parameter.id);
        assertEquals("&city=a+b,c",test.toString());
    }
}
package csci310.models;

import org.junit.Test;

import csci310.models.Event.Wrapper;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class EventTest {
    @Test
    public void testsplit() {
        List<String> split = Event.split(
            "",
            ','
        );
        assertNotNull(split);
        assertTrue(split.isEmpty());
        
        split = Event.split(
            " a , b ",
            ','
        );
        assertNotNull(split);
        assertEquals(2,split.size());
        assertEquals("a",split.get(0));
        assertEquals("b",split.get(1));
        
        split = Event.split(
            "\"a,b\",c",
            ','
        );
        assertNotNull(split);
        assertEquals(2,split.size());
        assertEquals("\"a,b\"",split.get(0));
        assertEquals("c",split.get(1));
    
        split = Event.split(
            "{a,b},[c,d],\"e,f,{g,h},[i,j]\"",
            ','
        );
        assertNotNull(split);
        assertEquals(3,split.size());
        assertEquals("{a,b}",split.get(0));
        assertEquals("[c,d]",split.get(1));
        assertEquals("\"e,f,{g,h},[i,j]\"",split.get(2));
    
        split = Event.split(
            "{a,[b,{c},d],\"f}\\\"\",g}",
            ','
        );
        assertNotNull(split);
        assertEquals(1,split.size());
        assertEquals("{a,[b,{c},d],\"f}\\\"\",g}",split.get(0));
    
        split = Event.split(
            "a,b,c,d,e",
            ',',
            3
        );
        assertNotNull(split);
        assertEquals(3,split.size());
        assertEquals("a",split.get(0));
        assertEquals("b",split.get(1));
        assertEquals("c,d,e",split.get(2));
        
        split = Event.split(
            "a,b",
            ',',
            3
        );
        assertNotNull(split);
        assertEquals(2,split.size());
        assertEquals("a",split.get(0));
        assertEquals("b",split.get(1));
    }
    @Test
    public void testparseArray() {
        List<Object> test = Event.parseArray(
            " [ ] "
        );
        assertNotNull(test);
        assertTrue(test.isEmpty());
        
        test = Event.parseArray(
            "[\"a\"]"
        );
        assertNotNull(test);
        assertEquals(1,test.size());
        try {
            String x = (String)test.get(0);
            assertNotNull(x);
            assertEquals("a",x);
        } catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    
        test = Event.parseArray(
            "[ \"a\", \"b\" ]"
        );
        assertNotNull(test);
        assertEquals(2,test.size());
        try {
            String x = (String)test.get(0);
            assertNotNull(x);
            assertEquals("a",x);
            x = (String)test.get(1);
            assertNotNull(x);
            assertEquals("b",x);
        } catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    
        test = Event.parseArray(
            "[[1,2],[3]]"
        );
        assertNotNull(test);
        assertEquals(2,test.size());
        try {
            @SuppressWarnings("unchecked")
            List<Object> x = (List<Object>)test.get(0);
            assertNotNull(x);
            assertEquals(2,x.size());
            {
                Double a = (Double)x.get(0);
                assertNotNull(a);
                assertEquals(1,a.intValue());
            }
            {
                Double b = (Double)x.get(1);
                assertNotNull(b);
                assertEquals(2,b.intValue());
            }
            @SuppressWarnings("unchecked")
            List<Object> y = (List<Object>)test.get(1);
            assertNotNull(y);
            assertEquals(1,y.size());
            {
                Double c = (Double)y.get(0);
                assertNotNull(c);
                assertEquals(3,c.intValue());
            }
        } catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
        
        test = Event.parseArray(
            "[{\"a\":1,\"b\":2},{\"c\":3}]"
        );
        assertNotNull(test);
        assertEquals(2,test.size());
        try {
            @SuppressWarnings("unchecked")
            Map<String,Object> x = (Map<String,Object>)test.get(0);
            assertNotNull(x);
            assertEquals(2,x.size());
            {
                Double a = (Double)x.get("a");
                assertNotNull(a);
                assertEquals(1,a.intValue());
            }
            {
                Double b = (Double)x.get("b");
                assertNotNull(b);
                assertEquals(2,b.intValue());
            }
            @SuppressWarnings("unchecked")
            Map<String,Object> y = (Map<String,Object>)test.get(1);
            assertNotNull(y);
            assertEquals(1,y.size());
            {
                Double c = (Double)y.get("c");
                assertNotNull(c);
                assertEquals(3,c.intValue());
            }
        } catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
    @Test
    public void testparseObject() {
        Map<String,Object> test = Event.parseObject(
            " { } "
        );
        assertNotNull(test);
        assertTrue(test.isEmpty());
    
        test = Event.parseObject(
            "{\"test\":\"a\"}"
        );
        assertNotNull(test);
        assertEquals(1,test.size());
        try {
            String x = (String)test.get("test");
            assertNotNull(x);
            assertEquals("a",x);
        } catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    
        test = Event.parseObject(
            "{ \"test1\" :\"a\", \"test2\": \"b\" }"
        );
        assertNotNull(test);
        assertEquals(2,test.size());
        try {
            String x = (String)test.get("test1");
            assertNotNull(x);
            assertEquals("a",x);
            x = (String)test.get("test2");
            assertNotNull(x);
            assertEquals("b",x);
        } catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    
        test = Event.parseObject(
            "{\"test1\":{\"a\":1,\"b\":2},\"test2\":{\"c\":3}}"
        );
        assertNotNull(test);
        assertEquals(2,test.size());
        try {
            @SuppressWarnings("unchecked")
            Map<String,Object> x = (Map<String,Object>)test.get("test1");
            assertNotNull(x);
            assertEquals(2,x.size());
            {
                Double a = (Double)x.get("a");
                assertNotNull(a);
                assertEquals(1,a.intValue());
            }
            {
                Double b = (Double)x.get("b");
                assertNotNull(b);
                assertEquals(2,b.intValue());
            }
            @SuppressWarnings("unchecked")
            Map<String,Object> y = (Map<String,Object>)test.get("test2");
            assertNotNull(y);
            assertEquals(1,y.size());
            {
                Double c = (Double)y.get("c");
                assertNotNull(c);
                assertEquals(3,c.intValue());
            }
        } catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    
        test = Event.parseObject(
            "{\"test1\":[1,2],\"test2\":[3]}"
        );
        assertNotNull(test);
        assertEquals(2,test.size());
        try {
            @SuppressWarnings("unchecked")
            List<Object> x = (List<Object>)test.get("test1");
            assertNotNull(x);
            assertEquals(2,x.size());
            {
                Double a = (Double)x.get(0);
                assertNotNull(a);
                assertEquals(1,a.intValue());
            }
            {
                Double b = (Double)x.get(1);
                assertNotNull(b);
                assertEquals(2,b.intValue());
            }
            @SuppressWarnings("unchecked")
            List<Object> y = (List<Object>)test.get("test2");
            assertNotNull(y);
            assertEquals(1,y.size());
            {
                Double c = (Double)y.get(0);
                assertNotNull(c);
                assertEquals(3,c.intValue());
            }
        } catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
    @Test
    public void testparseString() {
        String test = Event.parseString(
            "\"\""
        );
        assertNotNull(test);
        assertEquals("",test);
        
        test = Event.parseString(
            "\"\\\"\""
        );
        assertNotNull(test);
        assertEquals("\\\"",test);
    }
    @Test
    public void testparseNull() {
        Object obj = Event.parseNull(
            "null"
        );
        assertNull(obj);
    }
    @Test
    public void testWrapperDetect() {
    	assertNull(Event.Wrapper.detect(""));
    	assertNull(Event.Wrapper.detect(" "));
    	assertNull(Event.Wrapper.detect("}"));
    	assertEquals(Event.Wrapper.arr, Event.Wrapper.detect(" ["));
    	assertEquals(Event.Wrapper.obj, Event.Wrapper.detect("{[]}"));
    }
}
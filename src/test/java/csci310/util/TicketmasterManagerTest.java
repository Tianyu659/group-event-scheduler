package csci310.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TicketmasterManagerTest {

	@Test
	public void testsearchEventByKeyword() throws IOException {
		String keyword = "eventnotfound";
		String expected = "{\"_links\":{\"self\":{\"href\":\"/discovery/v2/events.json?keyword=eventnotfound\"}},\"page\":{\"size\":20,\"totalElements\":0,\"totalPages\":0,\"number\":0}}";
		assertEquals(expected, TicketmasterManager.searchEventByKeyword(keyword));
	}
	
//	@Test
//	public void testgetEventDetails() throws IOException {
//		String eventID = "k7vGFpS8LxPSc";
//		assertEquals("", TicketmasterManager.getEventDetails(eventID));
//	}
	
}

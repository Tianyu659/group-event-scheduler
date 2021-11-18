package csci310.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import csci310.models.EventSearch;
import csci310.models.EventSearch.Parameter;

import org.junit.Assert;
import org.junit.Test;

public class TicketmasterManagerTest extends TicketmasterManager{
	
	@Test
	public void testSearchEventByKeyword() throws IOException, InterruptedException {
		String keyword = "eventnotfound";

		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
		Map<String, Object> map = objectMapper.readValue(TicketmasterManager.searchEventByKeyword(keyword), typeRef);

		Assert.assertNotNull(map.get("_links"));
		Assert.assertNotNull(map.get("page"));
		
	}
	
	@Test
	public void testGetEventDetails() throws IOException, InterruptedException {
		String eventID = "k7vGFpS8LxPSc";

		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
		Map<String, Object> map = objectMapper.readValue(TicketmasterManager.getEventDetails(eventID), typeRef);

		Assert.assertEquals(map.get("name"), "Mozart & Mendelssohn");
		Assert.assertEquals(map.get("type"), "event");
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchEventID() throws IOException, InterruptedException {
		EventSearch event = new EventSearch();
		event.put(Parameter.id, "k7vGFpS8LxPSc");

		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};

		Map<String, Object> map = objectMapper.readValue(TicketmasterManager.searchEvent(event), typeRef);
		ArrayList<Object> eventList = (ArrayList<Object>) ((Map<String, Object>) map.get("_embedded")).get("events");
		ArrayList<Map<String, Object>> eventList2 = new ArrayList<Map<String, Object>>();
		for (Object obj : eventList) {
			eventList2.add((Map<String, Object>) obj);
		}
		
		Assert.assertEquals(eventList2.get(0).get("name"), "Mozart & Mendelssohn");
		Assert.assertEquals(eventList2.get(0).get("type"), "event");
		Assert.assertEquals(eventList2.get(0).get("id"), "k7vGFpS8LxPSc");

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchEventStateCode() throws IOException, InterruptedException {
		EventSearch event = new EventSearch();
		event.put(Parameter.stateCode, "CA");

		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
		Map<String, Object> map = objectMapper.readValue(TicketmasterManager.searchEvent(event), typeRef);
		ArrayList<Map<String, Object>> eventList = (ArrayList<Map<String, Object>>) ((Map<String, Object>) map.get("_embedded")).get("events");

		for(Map<String, Object> e : eventList) {
			ArrayList<Map<String, Object>> venues = (ArrayList<Map<String, Object>>) ((Map<String, Object>) e.get("_embedded")).get("venues");
			boolean foundStateCode = false;
			for(Map<String, Object> v : venues) {
				if(((Map<String, Object>) v.get("state")).get("stateCode").equals("CA")) {
					foundStateCode = true;
				}
			}
			assertTrue(foundStateCode);
		}

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchEventDateTime() throws IOException, InterruptedException {
		EventSearch event = new EventSearch();
		event.put(Parameter.startDateTime, "2022-01-05T00:00:01Z");
		event.put(Parameter.endDateTime, "2022-01-06T23:59:59Z");

		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
		Map<String, Object> map = objectMapper.readValue(TicketmasterManager.searchEvent(event), typeRef);
		ArrayList<Map<String, Object>> eventList = (ArrayList<Map<String, Object>>) ((Map<String, Object>) map.get("_embedded")).get("events");

		boolean startDateInRange = true;
		boolean endDateInRange = true;
		for(Map<String, Object> e : eventList) {
			Assert.assertEquals(e.get("type"), "event");
			Map<String, Object> start = (Map<String, Object>) ((Map<String, Object>) e.get("dates")).get("start");
			if(start.get("dateTime").toString().compareTo("2022-01-05T00:00:01Z") < 0) {
				startDateInRange = false;
			}
			Map<String, Object> end = (Map<String, Object>) ((Map<String, Object>) e.get("dates")).get("end");
			if(end != null && end.get("dateTime").toString().compareTo("2022-01-06T23:59:59Z") > 0) {
				startDateInRange = false;
			}
		}
		assertTrue(startDateInRange);
		assertTrue(endDateInRange);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchEventClassificationName() throws IOException, InterruptedException {
		EventSearch event = new EventSearch();
		event.put(Parameter.classificationName, "Music");

		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
		Map<String, Object> map = objectMapper.readValue(TicketmasterManager.searchEvent(event), typeRef);
		ArrayList<Map<String, Object>> eventList = (ArrayList<Map<String, Object>>) ((Map<String, Object>) map.get("_embedded")).get("events");

		for(Map<String, Object> e : eventList) {
			Assert.assertEquals(e.get("type"), "event");
			ArrayList<Map<String, Object>> classificationList = (ArrayList<Map<String, Object>>) e.get("classifications");
			boolean foundClass = false;
			for(Map<String, Object> c : classificationList) {
				if(((Map<String, Object>)c.get("segment")).get("name").toString().equals("Music")
						|| ((Map<String, Object>)c.get("genre")).get("name").toString().equals("Music") ) {
					foundClass = true;
				}
			}
			assertTrue(foundClass);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRateLimit() throws IOException, InterruptedException {
		EventSearch event = new EventSearch();
		event.put(Parameter.id, "k7vGFpS8LxPSc");
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};

		TicketmasterManager.searchEvent(event);
		TicketmasterManager.searchEvent(event);
		TicketmasterManager.searchEvent(event);
		TicketmasterManager.searchEvent(event);
		TicketmasterManager.searchEvent(event);
		TicketmasterManager.searchEvent(event);
		Map<String, Object> map = objectMapper.readValue(TicketmasterManager.searchEvent(event), typeRef);
		ArrayList<Object> eventList = (ArrayList<Object>) ((Map<String, Object>) map.get("_embedded")).get("events");
		ArrayList<Map<String, Object>> eventList2 = new ArrayList<Map<String, Object>>();
		for (Object obj : eventList) {
			eventList2.add((Map<String, Object>) obj);
		}
		
		Assert.assertEquals(eventList2.get(0).get("name"), "Mozart & Mendelssohn");
		Assert.assertEquals(eventList2.get(0).get("type"), "event");
		Assert.assertEquals(eventList2.get(0).get("id"), "k7vGFpS8LxPSc");

	}
}

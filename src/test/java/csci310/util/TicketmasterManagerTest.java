package csci310.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

public class TicketmasterManagerTest extends TicketmasterManager{
	@Test
	public void testSearchEventByKeyword() throws IOException {
		String keyword = "eventnotfound";

		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {};
		Map<String, Object> map = objectMapper.readValue(TicketmasterManager.searchEventByKeyword(keyword), typeRef);

		Assert.assertNotNull(map.get("_links"));
		Assert.assertNotNull(map.get("page"));

	}
	
	//Need change after event model is done
	@Test
	public void testGetEventDetails() throws IOException {
		String eventID = "k7vGFpS8LxPSc";

		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {};
		Map<String, Object> map = objectMapper.readValue(TicketmasterManager.getEventDetails(eventID), typeRef);

		Assert.assertEquals(map.get("name"), "Mozart & Mendelssohn");
		Assert.assertEquals(map.get("type"), "event");
	}
}

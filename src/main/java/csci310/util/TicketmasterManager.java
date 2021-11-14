package csci310.util;

import csci310.Configuration;
import csci310.models.EventSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.MalformedURLException;
import java.net.URL;
import java.util.BitSet;
import java.util.Map;
import java.util.StringJoiner;

public class TicketmasterManager {

	private static final String API_KEY = Configuration.load().value("ticketmaster.key");
	
	private static final String ROOT_URL = "https://app.ticketmaster.com/discovery/v2/";
	
	//Basic Event Search by keyword
	public static String searchEventByKeyword(String keyword) throws IOException {
		URL url = new URL(ROOT_URL + "events.json?apikey=" + API_KEY + "&keyword=" + keyword);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("GET");
	    connection.setRequestProperty("Content-Type", "application/json");
	    connection.setRequestProperty("Authorization","Token " + API_KEY);
	    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    
	    String lineString = in.readLine();
	    return lineString;
	}	
	
	//Event Details
	public static String getEventDetails(String eventID) throws IOException {
		URL url = new URL(ROOT_URL + "events/" + eventID + ".json?apikey=" + API_KEY);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization","Token " + API_KEY);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
        String lineString = in.readLine();
        return lineString;
	}
	
	// EventSearch
	public static String searchEvent(EventSearch event) throws IOException {
		URL url = new URL(ROOT_URL + "events.json?apikey=" + API_KEY + event.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization","Token " + API_KEY);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
        String lineString = in.readLine();
        return lineString;
	}

}
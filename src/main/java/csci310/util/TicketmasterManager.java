package csci310.util;

import csci310.Configuration;
import csci310.models.EventSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TicketmasterManager {

	private static final String API_KEY = Configuration.load().value("ticketmaster.key");
	
	private static final String ROOT_URL = "https://app.ticketmaster.com/discovery/v2/";
	
	private static Instant startInstant = Instant.now();

	public static CountDownLatch waiter = new CountDownLatch(1);
	
	public static void checkRateLimit() {
		long t = Duration.between(startInstant, Instant.now()).toMillis();
		if (t < 200) {
			try {
				waiter.await(200, TimeUnit.MILLISECONDS);
			} catch (InterruptedException ignored) {}
		}
		startInstant = Instant.now();
	}

	// EventSearch
	public static String searchEvent(EventSearch event) throws IOException {
		checkRateLimit();
		URL url = new URL(ROOT_URL + "events.json?apikey=" + API_KEY + event.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization","Token " + API_KEY);
		return new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
	}
}
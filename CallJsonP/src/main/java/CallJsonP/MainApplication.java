package CallJsonP;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MainApplication {
	private static Map<String, Dictionary> dictionary = new HashMap<String, Dictionary>();
	public static void main(String[] args) {
		CorporationReader reader = new CorporationReader();
		reader.readData("https://data.gov.il/api/action/datastore_search?language=english&resource_id=b5223cbc-e1b2-4503-a499-97cdcd7190d2");

	}
	

	
}

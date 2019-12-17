package CallJsonP;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class CorporationReader {
	private static Map<String, Dictionary> dictionary = new HashMap<String, Dictionary>();
	private static List<Corporation> corporationList = new ArrayList<>();
	private Integer totalRecords = 0;
	
	public void readData(String urlStr) {
		HttpURLConnection conn = null;
		 
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
	
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			readJsonData(conn.getInputStream());
		

			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(conn != null ) {
				conn.disconnect();
			}
		}
	}
	
	private void readJsonData(InputStream inputStream) throws IOException {
		ObjectMapper obj  = new ObjectMapper();;
		JsonNode jsonNode = obj.readTree(inputStream);
		boolean success = jsonNode.get("success").asBoolean();
		if(!success) {
			return;
		}
		JsonNode result= jsonNode.get("result");
		totalRecords = result.get("total").intValue();
		System.out.println("Total Recrods " + totalRecords);
		
		JsonNode fields = result.get("fields");
		initalizeFieldSection(fields);

		for(Dictionary word: dictionary.values()) {
			System.out.println(word.toString());
		}
		
		JsonNode records= result.get("records");
		initalizeRecordsSection(records);
	}

	private void initalizeRecordsSection(JsonNode records) {
		if(records == null) {
			return;
		}
		Iterator<JsonNode> itr = records.elements();
		while(itr.hasNext()) {
			
			JsonNode node = itr.next();
			Corporation corporation = createCorporationItem(node);
			corporationList.add(corporation);
		}
		
	}

	private Corporation createCorporationItem(JsonNode corporationNode) {
		Corporation corporation = new Corporation();
		Iterator<String> nameItr =  corporationNode.fieldNames();
		
		while(nameItr.hasNext()) {
			String fieldName = nameItr.next();
			JsonNode valNode = corporationNode.get(fieldName);
			Dictionary value = dictionary.get(fieldName);
			if(value != null) {
				fieldName = value.getFiledEng();
				
			}
			JsonNodeType jsonType = valNode.getNodeType();
			switch(jsonType) {
			case BOOLEAN:
				corporation.getData().put(fieldName, Boolean.valueOf(valNode.asBoolean()));
				break;
			case NUMBER:
				corporation.getData().put(fieldName, Integer.valueOf(valNode.asInt()));
				break;
			case STRING:
				corporation.getData().put(fieldName, valNode.asText());
				break;
			default:
				System.out.println("ERROR: no handle for " + fieldName + " with type " + jsonType);
			}
			
		}
		return corporation;
	}

	private void initalizeFieldSection(JsonNode fields) {
		
		if(fields == null) {
			return;
		}
		
		Iterator<JsonNode> itr = fields.elements();
		Integer index = 1;
		while(itr.hasNext()) {
			JsonNode node = itr.next();
			Dictionary dictionaryElm = new Dictionary(node.get("id").asText(), "Field" + index, FieldTypes.getByString(node.get("type").asText()));
			index++;
			dictionary.put(dictionaryElm.getFieldHeb(), dictionaryElm);
		}
	}

}

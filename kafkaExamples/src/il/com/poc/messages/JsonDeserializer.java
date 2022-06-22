package il.com.poc.messages;

import java.io.IOException;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDeserializer implements Deserializer<EventMessage>{

	@Override
	public EventMessage deserialize(String topic, byte[] data) {
		final ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(data, EventMessage.class);
		} catch (IOException e) {
			 throw new SerializationException("Error derializing JSON message", e);
		}
	
	}

}

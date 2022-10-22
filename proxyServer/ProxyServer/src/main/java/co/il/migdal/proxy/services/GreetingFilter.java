package co.il.migdal.proxy.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.il.migdal.proxy.beans.Greeting;
@Service(value = "Greeting1Filter")
public class GreetingFilter implements ProxyBaseFilter {
	 ObjectMapper Obj = new ObjectMapper();
	public ResponseEntity<String> handleRequest() throws JsonProcessingException{
		Greeting greeting = new Greeting(-1, "From Filter");
		
		return ResponseEntity.ok(Obj.writeValueAsString(greeting));
	}
}

package co.il.migdal.proxy.services;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProxyBaseFilter {
	public ResponseEntity<String> handleRequest() throws JsonProcessingException;
}

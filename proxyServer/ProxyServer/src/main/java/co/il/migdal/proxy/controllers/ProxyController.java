package co.il.migdal.proxy.controllers;

import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.il.migdal.proxy.beans.Greeting;
import co.il.migdal.proxy.services.ProxyService;

@RestController
public class ProxyController {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	
	 @Autowired
	 ProxyService service;
	 
	@RequestMapping("/**")
	public ResponseEntity<String> processProxyRequest(String body,
            HttpMethod method, HttpServletRequest request, HttpServletResponse response, @RequestHeader(name="Host", required=false) final String host) throws URISyntaxException {
		return service.processProxyRequest(body, method, request, response, host);
	}
	
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}

package co.il.migdal.proxy.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class ProxyService {
	@Autowired
	ApplicationContext ctx;
	
	public ResponseEntity<String> processProxyRequest(String body,
            HttpMethod method, HttpServletRequest request, HttpServletResponse response, String host) throws URISyntaxException {

		
		String requestUrl = request.getRequestURI();
		//extract the first url part
		
		String beanFilterName = null;
		
		//String contextUrl = request.getContextPath();
		String hostParts[] = host.split(":");
		Integer port = 80;
		if(hostParts != null && hostParts.length > 0) {
			host = hostParts[0];
			if(hostParts.length > 1) {
				port = Integer.valueOf(hostParts[1]);
			}
		}
		//log if required in this line
		URI uri = new URI(request.isSecure()?"https": "http", null, host, port, null, null, null);
		
		// replacing context path form urI to match actual gateway URI
		uri = UriComponentsBuilder.fromUri(uri)
				.path(requestUrl)
				.query(request.getQueryString())
				.build(true).toUri();
		
		String pathParts[] = uri.getRawPath().split("/");
		if(pathParts != null && pathParts.length > 0) {
			for(String pathPart : pathParts) {
				if(!pathPart.trim().isBlank()) {
					beanFilterName = pathPart.trim() + "Filter";
					break;
				}
			}
				
		}
		
		
		HttpHeaders headers = new HttpHeaders();
		Enumeration<String> headerNames = request.getHeaderNames();

		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			headers.set(headerName, request.getHeader(headerName));
		}

		//headers.set("TRACE", traceId);
		//headers.remove(HttpHeaders.ACCEPT_ENCODING);
		ResponseEntity<String> serverResponse = null;
		if(ctx.containsBean(beanFilterName)) {
			ProxyBaseFilter proxyFilter = (ProxyBaseFilter)ctx.getBean(beanFilterName);
			try {
				serverResponse = proxyFilter.handleRequest();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			if(serverResponse != null) {
				return serverResponse;
			}
		}
		

		HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
		ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
		RestTemplate restTemplate = new RestTemplate(factory);
		try {
		
		serverResponse = restTemplate.exchange(uri, method, httpEntity, String.class);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.put(HttpHeaders.CONTENT_TYPE, serverResponse.getHeaders().get(HttpHeaders.CONTENT_TYPE));
		return serverResponse;
		
		
		} catch (HttpStatusCodeException e) {
			return ResponseEntity.status(e.getRawStatusCode())
					.headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
		}

	}

	public ApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	

}

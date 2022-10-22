package co.il.migdal.proxy;


import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProxyApplication {

	public static void main(String[] args) {
		String serverPort = "8083";
		if(args != null && args.length > 0) {
			serverPort = args[0];
		}
		System.out.println("Server port=" + serverPort);
		SpringApplication app = new SpringApplication(ProxyApplication.class);
		app.setDefaultProperties(Collections
		          .singletonMap("server.port", serverPort));
		 app.run(args);
	}

}

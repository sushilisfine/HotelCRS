package com.myhotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CrsConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsConfigServerApplication.class, args);
	}

}

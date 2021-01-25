package com.myhotel.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CrsDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsDiscoveryApplication.class, args);
	}

}

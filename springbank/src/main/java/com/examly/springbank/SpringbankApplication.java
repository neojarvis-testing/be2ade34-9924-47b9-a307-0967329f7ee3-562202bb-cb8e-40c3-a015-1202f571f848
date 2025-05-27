package com.examly.springbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class SpringbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbankApplication.class, args);
	}

}

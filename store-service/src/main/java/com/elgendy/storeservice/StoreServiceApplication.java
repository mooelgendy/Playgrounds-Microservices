package com.elgendy.storeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class StoreServiceApplication {

	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return  WebClient.builder();
	}

	public static void main(String[] args) {
		SpringApplication.run(StoreServiceApplication.class, args);
	}

}

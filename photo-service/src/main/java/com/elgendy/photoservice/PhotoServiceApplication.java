package com.elgendy.photoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class PhotoServiceApplication {

	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return  WebClient.builder();
	}

	public static void main(String[] args) {
		SpringApplication.run(PhotoServiceApplication.class, args);
	}

}

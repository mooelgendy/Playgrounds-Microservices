package com.elgendy.invitationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@EnableCaching
@EnableDiscoveryClient
@SpringBootApplication
public class InvitationServiceApplication {

	@Bean
	@LoadBalanced
	public WebClient.Builder getWebClientBuilder(){
		return  WebClient.builder();
	}

	public static void main(String[] args) {
		SpringApplication.run(InvitationServiceApplication.class, args);
	}

}

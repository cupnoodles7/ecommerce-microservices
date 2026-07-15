package com.tnf.order_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {
	
	
	@Bean
	@LoadBalanced //IMPORTANT: Enables service discovery
	
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}
	
	
	@Bean
	@LoadBalanced
	public WebClient webClient(WebClient.Builder builder) {
		return builder.build();
	}
}

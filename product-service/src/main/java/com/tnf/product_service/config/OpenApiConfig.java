package com.tnf.product_service.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	// Base URL that Swagger "Try it out" should target. Points at the API gateway
	// so requests are routed (and secured) through it instead of hitting this
	// instance's internal IP directly. Overridable via config-server per env.
	@Value("${gateway.url:http://localhost:8083}")
	private String gatewayUrl;

	@Bean
	public OpenAPI productOpenAPI() {
		return new OpenAPI()
				.servers(List.of(new Server()
						.url(gatewayUrl)
						.description("API Gateway")));
	}

}

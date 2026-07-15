package com.tnf.auth_service.Config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	// Base URL that Swagger "Try it out" should target. Points at the API gateway
	// so requests are routed through it instead of this instance's internal IP.
	@Value("${gateway.url:http://localhost:8083}")
	private String gatewayUrl;

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.servers(List.of(new Server()
				.url(gatewayUrl)
				.description("API Gateway")))
				.addSecurityItem(new SecurityRequirement()
				.addList("BearerAuth"))
				.components(new Components()
				.addSecuritySchemes("BearerAuth", new SecurityScheme()
				.name("BearerAuth")
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT")));

	}

}

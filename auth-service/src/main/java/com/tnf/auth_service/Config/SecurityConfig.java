package com.tnf.auth_service.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// Auth is enforced at the API gateway. This service validates tokens via /validate
	// and the X-Auth-* headers, so requests are permitted here. Default Spring Security
	// (HTTP Basic on every endpoint) is replaced by this chain.
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				// Disable CSRF for stateless REST APIs
				.csrf(csrf -> csrf.disable())
				// No server-side session; JWT carries the auth state
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/auth/**").permitAll()
						.anyRequest().permitAll())
				.build();
	}
}

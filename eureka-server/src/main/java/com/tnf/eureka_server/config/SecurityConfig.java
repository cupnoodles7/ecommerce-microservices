//package com.tnf.eureka_server.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//	//basic Http authentication
//	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
//		
//		http
//			.csrf(csrf -> csrf.disable()) //disable csrf only enable http auth
//			.authorizeHttpRequests(auth -> auth
//					.anyRequest().authenticated()
//					)
//					.httpBasic(httpBasic -> {});
//		
//		return http.build();
//	}
//
//}
//NOT NEEDED ANYMORE

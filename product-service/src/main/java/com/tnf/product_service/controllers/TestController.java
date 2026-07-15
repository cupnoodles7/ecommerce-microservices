package com.tnf.product_service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/test")
public class TestController {
	
	@Value("${message}")
	private String message;
	
	@Value("${my.name}")
	private String team;
	
	@GetMapping("/vijay")
	public String config() {
		return message + " - " + team;
	}

}

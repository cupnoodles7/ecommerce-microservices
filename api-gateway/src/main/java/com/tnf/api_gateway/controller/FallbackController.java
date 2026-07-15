
package com.tnf.api_gateway.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
	
	@GetMapping("/products")
	public ResponseEntity<Map<String, String>> productServiceFallback() {
		Map<String, String> response = new HashMap<>();
		response.put("message", "Product Service is currently unavailable. Please try again later.");
		response.put("status", "503");
		return ResponseEntity.status(503).body(response);
	}
	
	@GetMapping("/orders")
	public ResponseEntity<Map<String, String>> orderServiceFallback() {
		Map<String, String> response = new HashMap<>();
		response.put("message", "Order Service is currently unavailable. Please try again later.");
		response.put("status", "503");
		return ResponseEntity.status(503).body(response);
	}
	
	@GetMapping("/payments")
	public ResponseEntity<Map<String, String>> paymentServiceFallback() {
		Map<String, String> response = new HashMap<>();
		response.put("message", "Payment Service is currently unavailable. Please try again later.");
		response.put("status", "503");
		return ResponseEntity.status(503).body(response);
	}


}

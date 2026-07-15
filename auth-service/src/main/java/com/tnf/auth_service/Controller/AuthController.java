package com.tnf.auth_service.Controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tnf.auth_service.dto.AuthRequest;
import com.tnf.auth_service.dto.AuthResponse;
import com.tnf.auth_service.dto.LoginRequest;
import com.tnf.auth_service.entity.User;
import com.tnf.auth_service.service.AuthService;
import com.tnf.auth_service.util.JwtUtil;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	
	//PUBLIC ENDPOINTS
	
	//Public: Register a new user
	
	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody AuthRequest request) {
		
		logger.info("Register request for : {}", request.getUsername());
		User user = authService.registerUser(request);
		Map<String, Object> response = new HashMap<>();
		response.put("message", "User registered successfully");
		response.put("userId", user.getId());
		response.put("username", user.getUsername());
		response.put("email", user.getEmail());
		response.put("roles", user.getRoles());
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(response);
		
	}
	
	//Public: Login and get JWT token
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
		
		logger.info("Login request for: {}", request.getUsernameOrEmail());
		
		AuthResponse response = authService.loginUser(request);
		return ResponseEntity.ok(response);
	}
	
	
	//PUBLIC: Validate JWT Token (from Gateway to check)
	
	@PostMapping("/validate")
	public ResponseEntity<Map<String, Object>>
	validateToken(@RequestHeader("Authorization") String authHeader) {
		logger.info("Token Validation Request");
		Map<String, Object> response = new HashMap<>();
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			response.put("valid", false);
			response.put("message", "Invalid token format");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
		
		String token = authHeader.substring(7);
		if(jwtUtil.validateToken(token)) {
			String username = jwtUtil.extractUsername(token);
			response.put("valid", true);
			response.put("username", username);
			response.put("roles", jwtUtil.extractRoles(token));
			return ResponseEntity.ok(response);
		} else {
			
			response.put("valid", false);
			response.put("message", "Invalid or expired token");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			
		}
	}
	
	
	
	
	
	//PRIVATE ENDPOINTS
	
	//Private: Get current user profiles
	@GetMapping("/me")
	public ResponseEntity<User> getCurrentuser(@RequestHeader("X-Auth-Username") String username) {
		logger.info("Getting profile for: {}", username);
		User user = authService.getUserByUsername(username);
		return ResponseEntity.ok(user);
	}
	
	//Private: Get all users (Admin only)
	@GetMapping("/users")
	public ResponseEntity<Iterable<User>> getAllUsers(@RequestHeader("X-Auth-Roles") String rolesHeader){
		logger.info("Getting all users");
		
		//check if user has ADMIN role
		if (rolesHeader == null || !rolesHeader.contains("ADMIN")) {
			throw new RuntimeException("Access denied. Admin Role required. ");
		}
		
		Iterable<User> users = authService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	
	//Private: Logout (invalidate token - client-side)
	@PostMapping("/logout")
	public ResponseEntity<Map<String, String>> logout() {
		logger.info(" Logout request");
		Map<String, String> response = new HashMap<>();
		response.put("message", "Logout successful. Please remove your");
		return ResponseEntity.ok(response);
		
	}
	
	
	

}

package com.tnf.auth_service.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tnf.auth_service.dto.AuthRequest;
import com.tnf.auth_service.dto.AuthResponse;
import com.tnf.auth_service.dto.LoginRequest;
import com.tnf.auth_service.entity.User;
import com.tnf.auth_service.repository.UserRepository;
import com.tnf.auth_service.util.JwtUtil;


@Service
public class AuthService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
			
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private final PasswordEncoder passwordEncoder = 
			new BCryptPasswordEncoder();
	
	
	
	//PUBLIC ENDPOINTS
	
	//register a new user
	public User registerUser(AuthRequest request) {
		logger.info("Registering user with email: {}", request.getUsername());
	
		//check if username already exists
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new RuntimeException("Username already exists");
		}
		
		//check if email already exists
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already exists");
		}
		
		//Create new User
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setEnabled(true);
		
		User savedUser = userRepository.save(user);
		logger.info("User registered: {}", savedUser.getUsername());
		
		return savedUser;
		
	}
	
	//login user and generate JWT token
	public AuthResponse loginUser(LoginRequest request) {
		
		logger.info("Login attempt: {}", request.getUsernameOrEmail());
			
		//find user by username or email
		Optional<User> userOpt = userRepository
				.findByUsername(request.getUsernameOrEmail());
		
		if (userOpt.isEmpty() ) {
			userOpt = userRepository.findByEmail(request.getUsernameOrEmail());
		}
		
		if (userOpt.isEmpty()) {
			throw new RuntimeException("Invalid username/email or password");
		}
		
		User user = userOpt.get();
		
		//check password
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalud username/email or password");
		}
		
		//check if user is enabled
		if(!user.isEnabled()) {
			throw new RuntimeException("Account is disabled");
		}
		
		//update last login
		user.setLastLogin(LocalDateTime.now());
		userRepository.save(user);
		
		//generate JWT token
		String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
		long expiresIn = jwtUtil.extractExpiration(token).getTime()
				- System.currentTimeMillis();
		
		logger.info("login successful: {}", user.getUsername());
			
		
		return new AuthResponse(
				token, 
				user.getUsername(),
				user.getEmail(),
				user.getRoles(),
				expiresIn
				);
	
	}
	
	
	//PRIVATE ENDPOINTS
	
	//get user profile by username (private)
	public User getUserProfile(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}
	
	
	//validate JWT token (Private)
	public boolean validateToken(String token) {
		return jwtUtil.validateToken(token);
	}
	
	//get user by username(private)
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}
	
	//get all users (admin ONLY - private )
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

}

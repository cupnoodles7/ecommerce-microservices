package com.tnf.auth_service.dto;

import java.util.Set;

public class AuthResponse {
	
	private String token;
	private String type = "Bearer"; //last time we used BASIC auth
	private String username;
	private String email;
	private Set<String> roles;
	private long expiresIn;
	
	
	public AuthResponse(String token, String username, String email, Set<String> roles, long expiresIn) {
		super();
		this.token = token;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.expiresIn = expiresIn;
	}


	public AuthResponse() {
		super();
	}

	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Set<String> getRoles() {
		return roles;
	}


	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}


	public long getExpiresIn() {
		return expiresIn;
	}


	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	
	
	

}

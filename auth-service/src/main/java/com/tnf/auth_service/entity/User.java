package com.tnf.auth_service.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
public class User {
	
	@Id
	private String id;
	
	@Indexed(unique = true) //unique username
	private String username;
	
	@Indexed(unique = true) //unique email
	private String email;
	
	private String password;
	
	private Set<String> roles = new HashSet<>(); //USER, ADMIN
	
	private boolean enabled = true;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime lastLogin;
	

	public User() {
		this.createdAt = LocalDateTime.now();
		this.roles.add("USER"); //Default role
	}




	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", roles="
				+ roles + ", enabled=" + enabled + ", createdAt=" + createdAt + ", lastLogin=" + lastLogin + "]";
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Set<String> getRoles() {
		return roles;
	}


	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDateTime getLastLogin() {
		return lastLogin;
	}


	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	
	
	
	

}

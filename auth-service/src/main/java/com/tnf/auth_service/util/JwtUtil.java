package com.tnf.auth_service.util;

import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


//keycloak abstracts this

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}") //takes values from properties file
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	private Key getSigningKey() {
		byte[] keyBytes = secret.getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> extractRoles(String token){
		Claims claims = extractAllClaims(token);
		// jjwt + Jackson deserializes the JSON array into an ArrayList, not a Set,
		// so read it as a Collection and wrap it ourselves.
		Object roles = claims.get("roles");
		if (roles instanceof Collection) {
			return new HashSet<>((Collection<String>) roles);
		}
		return Collections.emptySet();
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
		
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(String username, Set<String> roles) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", roles);
		return createToken(claims, username);
	}
	
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public Boolean validateToken(String token) {
		try {
			extractAllClaims(token);
			return !isTokenExpired(token);
		} catch(Exception e) {
			return false;
		}
	}
	
	public Boolean validateTokeb(String token, String username) {
		final String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}
	
	

	
}

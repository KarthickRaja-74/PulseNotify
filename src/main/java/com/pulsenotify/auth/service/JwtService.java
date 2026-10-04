package com.pulsenotify.auth.service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.pulsenotify.config.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private final JwtProperties jwtProperties;
	 private final SecretKey secretKey;
	 
	public JwtService(JwtProperties jwtProperties) {
		this.jwtProperties =  jwtProperties;
		
		byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(UUID userId) {
		Instant now = Instant.now();
		Instant expiration = now.plus(jwtProperties.getExpiration());
		return Jwts.builder()
				.subject(userId.toString())
				.issuedAt(Date.from(now))
				.expiration(Date.from(expiration))
				.signWith(secretKey)
				.compact();
	}
	
	public UUID extractUserId(String token) {
	    Claims claims = Jwts.parser()
	            .verifyWith(secretKey)
	            .build()
	            .parseSignedClaims(token)
	            .getPayload();

	    return UUID.fromString(claims.getSubject());
	}
}

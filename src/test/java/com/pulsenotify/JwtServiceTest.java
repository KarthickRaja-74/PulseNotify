package com.pulsenotify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pulsenotify.auth.service.JwtService;
import com.pulsenotify.config.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtServiceTest {
	private JwtService jwtService;
//	@BeforeEach
//	void setUp() {
//	    JwtProperties properties = new JwtProperties();
//	    properties.setExpiration(Duration.ofHours(1));
//	   // Decoders.BASE64.decode(properties.getSecret());
//	    properties.setSecret(
//	            "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY="
//	    );
//	    jwtService = new JwtService(properties);
//	}
	
	@Test
	void shouldGenerateToken() {
	    UUID userId = UUID.randomUUID();
	    String token = jwtService.generateToken(userId);
	    assertNotNull(token);
	}
	
	@Test
	void shouldContainUserIdAsSubject() {
	    UUID userId = UUID.randomUUID();

	    String token = jwtService.generateToken(userId);

	    String secret = "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=";

	    SecretKey secretKey = Keys.hmacShaKeyFor(
	            Decoders.BASE64.decode(secret)
	    );
	    
	    Claims claims = Jwts.parser()
	            .verifyWith(secretKey)
	            .build()
	            .parseSignedClaims(token)
	            .getPayload();

	    assertEquals(userId.toString(), claims.getSubject());
	}
	
	@Test
	void shouldSetIssuedAtAndExpiration() {
	    UUID userId = UUID.randomUUID();
	    String secret = "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=";
	    String token = jwtService.generateToken(userId);

	    SecretKey secretKey = Keys.hmacShaKeyFor(
	            Decoders.BASE64.decode(secret)
	    );

	    Claims claims = Jwts.parser()
	            .verifyWith(secretKey)
	            .build()
	            .parseSignedClaims(token)
	            .getPayload();
	    
	    assertNotNull(claims.getIssuedAt());
	    assertNotNull(claims.getExpiration());
	    Duration duration = Duration.between(
	            claims.getIssuedAt().toInstant(),
	            claims.getExpiration().toInstant()
	    );

	    assertEquals(Duration.ofHours(1), duration);
	}
}

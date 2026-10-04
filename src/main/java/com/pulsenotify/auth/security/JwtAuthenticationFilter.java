package com.pulsenotify.auth.security;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pulsenotify.auth.service.JwtService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	
	public JwtAuthenticationFilter(JwtService jwtService) {
	    this.jwtService = jwtService;
	}
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {
		try {
		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
		    filterChain.doFilter(request, response);
		    return;
		}

		String token = authHeader.substring(7);
		UUID userId = jwtService.extractUserId(token);
		
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			UsernamePasswordAuthenticationToken authentication =
			        new UsernamePasswordAuthenticationToken(
			                userId,
			                null,
			                Collections.emptyList()
			        );
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		} catch (JwtException | IllegalArgumentException ex) {
		    
		}
		filterChain.doFilter(request, response);
	}
}

package com.capg.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.dto.LoginDto;
import com.capg.jwt.JwtUtility;
import com.capg.response.JSONResponse;
import com.capg.service.UserDetailsImpl;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app")
public class AuthController {
	
	@Autowired
	private DaoAuthenticationProvider authenticationManager;
	
	@Autowired
	private JwtUtility jwtUtility;
	
	@PostMapping("/login")
	public ResponseEntity<?> validateUser(@Valid @RequestBody LoginDto loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String jwtToken = jwtUtility.generateToken(authentication);
		
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		List<String> li=authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		JSONResponse jsonResponse = new JSONResponse(jwtToken, userDetails.getUsername(), li);		
		return ResponseEntity.ok(jsonResponse);
						
													
	}
	
	
}

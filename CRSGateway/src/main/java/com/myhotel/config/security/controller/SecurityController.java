package com.myhotel.config.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myhotel.config.security.config.util.JwtUtil;
import com.myhotel.config.security.model.AuthenticationRequest;
import com.myhotel.config.security.model.AuthenticationResponse;
import com.myhotel.config.security.service.impl.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class SecurityController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping
	@RequestMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		try {
			// Authenticate user name and password
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid Username or Password.", e);
		}

		// We need User details to generate Token.
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

		return ResponseEntity.ok(new AuthenticationResponse(jwtUtil.generateToken(userDetails)));
	}
}
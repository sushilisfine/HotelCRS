package com.myhotel.config.security.service.impl;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Bean
	PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (username.equals("admin"))
			return new User("admin", getEncoder().encode("admin"), Arrays.asList(() -> "ROLE_ADMIN"));
		else
			return new User("user", getEncoder().encode("user"), Arrays.asList(() -> "ROLE_USER"));

	}
}
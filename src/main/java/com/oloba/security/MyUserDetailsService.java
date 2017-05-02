package com.oloba.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.oloba.module.user.UserService;

public class MyUserDetailsService implements UserDetailsService  {
	
	private static Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);
	
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUserDetails user = userService.loginWith(username);
		if (null == user) {
			log.warn("*********** User Not Found, username={}", username);
			throw new UsernameNotFoundException(username+" Not Found.");
		}
		return user;
	}
}

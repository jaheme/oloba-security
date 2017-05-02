package com.oloba.module.common;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.oloba.security.MyUserDetails;

/**
 * 登录成功后的一些公用数据，供外部调用
 * @author jahe.lai
 *
 */
public class LoginedService {
	
	/** 登录用户名的字段名 */
	public static final String USERNAME = "username";
	
	public static String getUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		return username;
	}
	
	public static int getUserid() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((MyUserDetails)principal).getId();
		}
		return 0;
	}
}

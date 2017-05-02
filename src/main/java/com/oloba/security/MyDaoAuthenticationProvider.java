package com.oloba.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.Assert;

import com.oloba.core.utils.DigestUtil;
import com.oloba.module.common.CacheService;

/**
 * 修改密码的组成方式，结合验证码检验
 * @author oloba
 *
 */
public class MyDaoAuthenticationProvider extends DaoAuthenticationProvider {
	

	private static Logger log = LoggerFactory.getLogger(MyDaoAuthenticationProvider.class);
	

	@Autowired
	private CacheService cacheService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
				messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.onlySupports",
						"Only UsernamePasswordAuthenticationToken is supported"));

		// Determine username
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
				: authentication.getName();

		boolean cacheWasUsed = true;
		UserDetails user = this.getUserCache().getUserFromCache(username);

		if (user == null) {
			cacheWasUsed = false;

			try {
				user = retrieveUser(username,
						(UsernamePasswordAuthenticationToken) authentication);
			}
			catch (UsernameNotFoundException notFound) {
				logger.debug("User '" + username + "' not found");

				if (hideUserNotFoundExceptions) {
					throw new BadCredentialsException(messages.getMessage(
							"AbstractUserDetailsAuthenticationProvider.badCredentials",
							"Bad credentials"));
				}
				else {
					throw notFound;
				}
			}

			Assert.notNull(user,
					"retrieveUser returned null - a violation of the interface contract");
		}
		
		try {
			user = changePassword(user, authentication);
			getPreAuthenticationChecks().check(user);
			additionalAuthenticationChecks(user,
					(UsernamePasswordAuthenticationToken) authentication);
		}
		catch (AuthenticationException exception) {
			if (cacheWasUsed) {
				// There was a problem, so try again after checking
				// we're using latest data (i.e. not from the cache)
				cacheWasUsed = false;
				user = retrieveUser(username,
						(UsernamePasswordAuthenticationToken) authentication);
				user = changePassword(user, authentication);
				getPreAuthenticationChecks().check(user);
				additionalAuthenticationChecks(user,
						(UsernamePasswordAuthenticationToken) authentication);
			}
			else {
				throw exception;
			}
		}

		getPostAuthenticationChecks().check(user);

		if (!cacheWasUsed) {
			this.getUserCache().putUserInCache(user);
		}

		Object principalToReturn = user;

		if (isForcePrincipalAsString()) {
			principalToReturn = user.getUsername();
		}

		return createSuccessAuthentication(principalToReturn, authentication, user);
	}

	private UserDetails changePassword(UserDetails user, Authentication authentication) {
		// WebAuthenticationDetails对象包含了web登录请求的一些验证信息
		WebAuthenticationDetails webDetails = (WebAuthenticationDetails) authentication.getDetails();
		String verify_code = cacheService.getVerifyCode(webDetails.getSessionId());
		
		String psw = DigestUtil.md5Hex(user.getPassword()+verify_code).toLowerCase();
		MyUserDetails myuser = (MyUserDetails) user;
		myuser.setPassword(psw);
		
		log.warn("verify_code={}   mypassword={}   requestpsw={}   seesionId={}", 
				verify_code, myuser.getPassword(), 
				authentication.getCredentials(), webDetails.getSessionId());
		return myuser;
	}

}

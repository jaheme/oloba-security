package com.oloba.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import com.oloba.module.privilege.PrivilegeService;
import com.oloba.module.privilege.PrivilegeUserService;

public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private static Logger log = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);
	
	private static final String USER_MENU = "user_menu_session";

	@Autowired
	private PrivilegeService privilegeService;
	@Autowired
	private PrivilegeUserService privilegeUserService;
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		privilegeUserService.onLoginSuccess();
		
		HttpSession session = request.getSession(true);
		session.setAttribute(USER_MENU, privilegeService.mineAccessMenu());
		
		MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
		session.setAttribute("username", userDetails.getUsername());
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest == null) {
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}
		String targetUrlParameter = getTargetUrlParameter();
		if (isAlwaysUseDefaultTargetUrl()
				|| (targetUrlParameter != null && StringUtils.hasText(request
						.getParameter(targetUrlParameter)))) {
			requestCache.removeRequest(request, response);
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}

		clearAuthenticationAttributes(request);
		
		// Use the DefaultSavedRequest URL
		String targetUrl = savedRequest.getRedirectUrl();
		logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	
	
}

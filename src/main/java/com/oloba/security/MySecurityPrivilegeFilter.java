package com.oloba.security;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.oloba.core.http.JsonView;
import com.oloba.core.status.BaseStatus;
import com.oloba.core.status.IStatus;
import com.oloba.core.utils.JsonUtil;
import com.oloba.module.common.URI;

/**
 * 对请求进行验证. 在web.xml上进行过滤器配置。
 * @author oloba
 *
 */
public class MySecurityPrivilegeFilter extends OncePerRequestFilter {

	private final String PNODES = "pnodes";
	private static Logger log = LoggerFactory.getLogger(MySecurityPrivilegeFilter.class);
	
	/**
	 * 在此实现此访问请求的权限验证，没有访问权限则抛出异常终止请求。
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (null == auth || null == auth.getPrincipal()) {
			if (ajaxRequest(request, response, BaseStatus.TIME_OUT)) {
				return;
			}
			doFilter(request, response, filterChain);
			return;
		}
		
		boolean check = checkPrivilege(request, auth);
		if (check) {
			doFilter(request, response, filterChain);
			return;
		}
		
		// 权限校验不通过
		if (ajaxRequest(request, response, BaseStatus.AUTHENTICATE)) {
			return;
		}
		getServletContext().getRequestDispatcher(URI.HS+URI.AUTH_FAILURE).forward(request, response);
		return;
	}
	
	/**
	 * 获取URI的权限节点值
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	private boolean checkPrivilege(HttpServletRequest request, Authentication auth) throws ServletException {
		String uri = request.getRequestURI();
		if ((URI.HS + URI.MAIN).equals(uri)) {
			return true;
		}
		MyUserDetails userDetails = (MyUserDetails)auth.getPrincipal();
		String[] arr = StringUtils.split(uri, "/");
		if (arr.length < 3) {	// 第三个元素是权限点. /hs/modual/node/**
			return false;
		}
		String module_name = arr[1];
		String node_name = arr[2];
		List<Map<String, List<String>>> plist = userDetails.getPrivilege();
		Map<String, List<String>> map = null;
		for (Iterator<Map<String, List<String>>> it = plist.iterator(); it.hasNext();) {
			map = it.next();
			if (map.containsKey(module_name)) {
				List<String> pnodeList = map.get(module_name);
				if (pnodeList.contains(node_name)) {
					request.setAttribute(PNODES, JsonUtil.toStr(pnodeList));
					return true;
				}
				log.error("uri={}; nodes={}", uri, JsonUtil.toStr(pnodeList));
				break;
			}
		}
		return false;
	}
	
	private boolean ajaxRequest(HttpServletRequest request, 
			HttpServletResponse response, IStatus status) throws IOException {
		String xreq = request.getHeader("X-Requested-With");
		if (StringUtils.equalsIgnoreCase("XMLHttpRequest", xreq)) {
			JsonView.resp(status).write(response);
			return true;
		}
		return false;
	}

}

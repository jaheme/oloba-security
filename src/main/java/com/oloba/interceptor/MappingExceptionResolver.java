package com.oloba.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.oloba.core.http.MJsonView;

/**
 * 扩展异常处理类
 *
 */
public class MappingExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			logger.error("Error : ", ex);
			request.setAttribute("msg", ex.getMessage());

			return getModelAndView(viewName, ex, request);
		} else { // ajax ?
			if ("XMLHttpRequest".equals(
                    request.getHeader("X-Requested-With"))) {
				logger.warn(request.getRequestURI() + " is Ajax.");
			}
			return MJsonView.fail(ex.getMessage());
		}
	}

}

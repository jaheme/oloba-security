package com.oloba.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.oloba.core.constant.EnvConf;
import com.oloba.core.http.MJsonView;

/**
 * 处理业务上没有捕获的异常，让异常不穿透到前端(网页端、手机端等) 返回失败的状态码。
 * 
 * @author Tony.lai
 * 
 */
@Service
public class ExecuteExceptionHandler implements HandlerExceptionResolver {

	private static Logger log = LoggerFactory.getLogger(ExecuteExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		log.error("unCatchException. URI:" + request.getRequestURI(), ex);
		if (EnvConf.isProd()) {
			return MJsonView.fail();
		}
		return MJsonView.fail(ex.toString());
	}

}

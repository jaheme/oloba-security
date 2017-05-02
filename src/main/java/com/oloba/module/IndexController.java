package com.oloba.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oloba.core.http.MJsonView;
import com.oloba.core.status.BaseStatus;
import com.oloba.core.utils.DateUtil;
import com.oloba.module.common.CacheService;
import com.oloba.module.common.URI;
import com.oloba.verify.Captcha;
import com.oloba.verify.GifCaptcha;

@Controller
public class IndexController {
	
	private static Logger log = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private CacheService cacheService;
	
	@RequestMapping(URI.LOGIN)
	public ModelAndView login_page(HttpServletRequest request) {
		String sessionId = request.getSession().getId();
		log.info("sessionId={}", sessionId);
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("time", DateUtil.currentStr("M月d日 HH时mm分"));
		return mav;
	}
	
	@RequestMapping(URI.VERIFY_CODE)
	public void getGifCode(HttpServletResponse response,HttpServletRequest request){
		try {
			response.setHeader("Pragma", "No-cache");  
	        response.setHeader("Cache-Control", "no-cache");  
	        response.setDateHeader("Expires", 0);
	        response.setContentType("image/gif");
	        /**
	         * gif格式动画验证码
	         * 宽，高，位数。
	         */
	        Captcha captcha = new GifCaptcha(146,33,4);
	        //输出
	        captcha.out(response.getOutputStream());
	        HttpSession session = request.getSession(true);
	        //存入
	        cacheService.putVerifyCode(session.getId(), captcha.text().toLowerCase());
		} catch (Exception e) {
//			LoggerUtils.fmtError(getClass(),e, "获取验证码异常：%s",e.getMessage());
		}
	}
	
	@RequestMapping(URI.MAIN)
	public ModelAndView logined2main() {
		ModelAndView mav = new ModelAndView("main");
		return mav;
	}
	
	@RequestMapping(URI.LOGIN_FAILURE)
	public ModelAndView login_failure() {
		log.info("login failure");
		return MJsonView.fail("login failure");
	}
	
	@RequestMapping(URI.AUTH_FAILURE)
	public ModelAndView auth_failure() {
		return MJsonView.resp(BaseStatus.AUTHENTICATE);
	}

}

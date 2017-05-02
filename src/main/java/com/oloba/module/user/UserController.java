package com.oloba.module.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oloba.core.http.MJsonView;
import com.oloba.core.status.BaseStatus;
import com.oloba.module.common.TResult;
import com.oloba.module.common.URI;
import com.oloba.module.user.pojo.TBaseUser;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	

	@RequestMapping(URI.USER_MANAGER)
	public ModelAndView manager() {
		ModelAndView mav = new ModelAndView("user/user_manager");
		return mav;
	}
	
	@RequestMapping(URI.USER_NEW)
	public ModelAndView newUser(TBaseUser user) {
		if (StringUtils.isBlank(user.getUsername())) {
			return MJsonView.resp(BaseStatus.PARAM_INVALID, "帐号必填");
		}
		TResult<Boolean> ret = userService.newUser(user);
		if (null != ret.result || ret.result) {
			return MJsonView.ok();
		}
		return MJsonView.fail();
	}

	@RequestMapping(URI.USER_EDIT_LOAD)
	public ModelAndView loadUser(Integer userid) {
		if (null == userid || userid.intValue() < 1) {
			return MJsonView.resp(BaseStatus.PARAM_INVALID, "用户标识无效");
		}
		return MJsonView.ok(userService.getUserById(userid));
	}

	
	@RequestMapping(URI.USER_EDIT_SAVE)
	public ModelAndView editUser(TBaseUser user) {
		if (StringUtils.isBlank(user.getUsername())) {
			return MJsonView.resp(BaseStatus.PARAM_INVALID, "帐号必填");
		}
		TResult<Boolean> ret = userService.updateUser(user);
		if (null != ret.result || ret.result) {
			return MJsonView.ok();
		}
		return MJsonView.fail();
	}
}

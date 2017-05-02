package com.oloba.module.privilege;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.oloba.core.http.MJsonView;
import com.oloba.core.status.BaseStatus;
import com.oloba.core.utils.JsonUtil;
import com.oloba.module.common.LoginedService;
import com.oloba.module.common.TResult;
import com.oloba.module.common.URI;
import com.oloba.module.privilege.pojo.TBaseRole;
import com.oloba.module.user.UserService;
import com.oloba.module.user.model.UserForPrivilege;

@Controller
public class PrivilegeUserController {
	

	private static Logger log = LoggerFactory.getLogger(PrivilegeUserController.class);

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private PrivilegeUserService privilegeUserService;
	@Autowired
	private PrivilegeService privilegeService;
	
	@RequestMapping(URI.PRIVILEGE_USER_MANAGER)
	public ModelAndView manager() {
	int create_uid = LoginedService.getUserid();
		List<TBaseRole> roleList = roleService.getUserRoles(create_uid);
		List<UserForPrivilege> userList = userService.getMyUserList(create_uid);
		ModelAndView mav = new ModelAndView("privilege/privilege_user");
		mav.addObject("roleList", roleList);
		mav.addObject("userList", userList);
		mav.addObject("menuList", privilegeService.minePMenu());
		
		mav.addObject("new_uri", URI.HS+URI.USER_NEW);
		mav.addObject("edit_uri", URI.HS+URI.USER_EDIT_LOAD);
		mav.addObject("edit_save_uri", URI.HS+URI.USER_EDIT_SAVE);
		mav.addObject("role_privilege_uri", URI.HS+URI.PRIVILEGE_ROLE_GET);
		mav.addObject("user_privilege_uri", URI.HS+URI.PRIVILEGE_USER_GET);
		mav.addObject("setting_privilege_uri", URI.HS+URI.PRIVILEGE_USER_SETTING);
		mav.addObject("setting_privilege_withrole_uri", URI.HS+URI.PRIVILEGE_USER_SETTING_WITHROLE);
		
		return mav;
	}

	/**
	 * 有权限的管理员，查看用户的权限，同时也可以在基础上配置新的权限。
	 * @param userid
	 * @return
	 */
	@RequestMapping(value=URI.PRIVILEGE_USER_GET, method=RequestMethod.POST)
	public ModelAndView get(Integer userid) {
		if (null == userid || userid.intValue() < 1) {
			return MJsonView.resp(BaseStatus.PARAM_INVALID, "先选择用户");
		}
		return MJsonView.ok(privilegeService.userPrivilege(userid));
	}
	
	@RequestMapping(value=URI.PRIVILEGE_USER_SETTING, method=RequestMethod.POST)
	public ModelAndView setting(Integer userid, @RequestParam("privilegeList[]") List<String> privilegeList) {
		if (null == userid || userid.intValue() < 1) {
			return MJsonView.resp(BaseStatus.PARAM_INVALID, "用户标识无效");
		}
		if (null == privilegeList || privilegeList.isEmpty()) {
			return MJsonView.resp(BaseStatus.PARAM_INVALID, "请配置角色的权限");
		}
		log.warn("userid={}   privilegeList={}", userid, JsonUtil.toStr(privilegeList));
		boolean ret = privilegeUserService.setting(userid, privilegeList);
		return ret ? MJsonView.ok() : MJsonView.fail();
	}
	
	@RequestMapping(value=URI.PRIVILEGE_USER_SETTING_WITHROLE, method=RequestMethod.POST)
	public ModelAndView setting(Integer userid, Integer role_id) {
		if (null == userid || userid.intValue() < 1) {
			return MJsonView.resp(BaseStatus.PARAM_INVALID, "用户标识无效");
		}
		if (null == role_id || role_id.intValue() < 1) {
			return MJsonView.resp(BaseStatus.PARAM_INVALID, "角色标识无效");
		}
		TResult<Boolean> ret = privilegeUserService.settingWithRole(userid, role_id);
		if (StringUtils.isBlank(ret.msg)) {
			return MJsonView.ok();
		}
		return MJsonView.fail(ret.msg);
	}
}

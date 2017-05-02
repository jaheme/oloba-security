package com.oloba.module.privilege;

import java.util.List;

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
import com.oloba.module.common.URI;
import com.oloba.module.privilege.pojo.TBaseRole;

@Controller
public class PrivilegeRoleController {
	
	private static Logger log = LoggerFactory.getLogger(PrivilegeRoleController.class);
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private PrivilegeService privilegeService;
	@Autowired
	private PrivilegeRoleService privilegeRoleService;

	@RequestMapping(URI.PRIVILEGE_ROLE_MANAGER)
	public ModelAndView manager() {
		int create_uid = LoginedService.getUserid();
		List<TBaseRole> roleList = roleService.getUserRoles(create_uid);
		ModelAndView mav = new ModelAndView("privilege/privilege_role");
		mav.addObject("roleList", roleList);
		mav.addObject("menuList", privilegeService.minePMenu());

		mav.addObject("new_uri", URI.HS+URI.PRIVILEGE_ROLE_NEW);
		mav.addObject("edit_uri", URI.HS+URI.PRIVILEGE_ROLE_EDIT);
		mav.addObject("edit_save_uri", URI.HS+URI.PRIVILEGE_ROLE_EDIT_SAVE);
		mav.addObject("get_privilege_uri", URI.HS+URI.PRIVILEGE_ROLE_GET);
		mav.addObject("setting_privilege_uri", URI.HS+URI.PRIVILEGE_ROLE_SETTING);
		
		return mav;
	}
	
	@RequestMapping(URI.PRIVILEGE_ROLE_NEW)
	public ModelAndView newRole(String role_name, String role_desc) {
		int create_uid = LoginedService.getUserid();
		boolean ret = roleService.newRole(create_uid, role_name, role_desc);
		return ret ? MJsonView.ok() : MJsonView.fail();
	}
	
	@RequestMapping(value=URI.PRIVILEGE_ROLE_EDIT, method=RequestMethod.POST)
	public ModelAndView loadEditRole(Integer role_id) {
		if (null == role_id || role_id.intValue() == 0) {
			return MJsonView.fail("请选择角色");
		}
		int create_uid = LoginedService.getUserid();
		TBaseRole role = roleService.getUserRole(create_uid, role_id);
		return MJsonView.ok(role);
	}
	
	@RequestMapping(URI.PRIVILEGE_ROLE_EDIT_SAVE)
	public ModelAndView loadEditRole(Integer role_id, 
			String role_name, String role_desc) {
		if (null == role_id || role_id.intValue() == 0) {
			return MJsonView.fail("请选择角色");
		}
		int create_uid = LoginedService.getUserid();
		boolean ret = roleService.updateRole(create_uid, role_id, 
				role_name, role_desc);
		return ret ? MJsonView.ok() : MJsonView.fail();
	}
	
	@RequestMapping(URI.PRIVILEGE_ROLE_GET)
	public ModelAndView get(Integer role_id) {
		if (null == role_id || role_id.intValue() < 1) {
			return MJsonView.resp(BaseStatus.PARAM_INVALID, "先选择角色");
		}
		return MJsonView.ok(privilegeRoleService.getPrivilegeForSettingById(role_id));
	}
	
	@RequestMapping(URI.PRIVILEGE_ROLE_SETTING)
	public ModelAndView setting(Integer role_id, @RequestParam("rolePrivilegeList[]") List<String> rolePrivilegeList) {
		if (null == role_id || role_id.intValue() < 1) {
			return MJsonView.resp(BaseStatus.PARAM_INVALID, "角色无效");
		}
		if (null == rolePrivilegeList || rolePrivilegeList.isEmpty()) {
			return MJsonView.resp(BaseStatus.PARAM_INVALID, "请配置角色的权限");
		}
		log.warn("role_id={}   rolePrivilegeList={}", role_id, JsonUtil.toStr(rolePrivilegeList));
		boolean ret = privilegeRoleService.setting(role_id, rolePrivilegeList);
		return ret ? MJsonView.ok() : MJsonView.fail();
	}
	
}

package com.oloba.module.privilege;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.oloba.module.common.LoginedService;
import com.oloba.module.common.TResult;
import com.oloba.module.privilege.pojo.TBaseRole;
import com.oloba.module.privilege.pojo.TPrivilegeUser;
import com.oloba.security.MyUserDetails;

@Service
public class PrivilegeUserService {
	

	private static Logger log = LoggerFactory.getLogger(PrivilegeUserService.class);
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private PrivilegeService privilegeService;
	@Autowired
	private PrivilegeUserDao privilegeUserDao;
	
	/**
	 * 登录成功。加载用户的权限。
	 */
	public void onLoginSuccess() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		MyUserDetails userDetails = (MyUserDetails)principal;
		List<Map<String, List<String>>> listMap = privilegeService.minePrivilege(userDetails);
		userDetails.setPrivilege(listMap);
	}
	
	
	
	////////////////////////  user privilege setting  ///////////////////////////

	protected boolean setting(int userid, List<String> req_plist) {
		int create_uid = LoginedService.getUserid();
		List<TPrivilegeUser> list = privilegeUserDao.getByUserid(userid);
		List<TPrivilegeUser> newList = buildPrivileges(userid, create_uid, req_plist);
		try {
			if (list.isEmpty()) {
				return privilegeUserDao.insert(userid, create_uid, newList);
			}
			return privilegeUserDao.update(userid, create_uid, newList);
		} catch (Exception e) {
			log.error("setting", e);
		}
		return false;
	}
	
	private List<TPrivilegeUser> buildPrivileges(int userid, int create_uid, 
			List<String> req_plist) {
		List<TPrivilegeUser> newList = new ArrayList<>();
		String[] prArr = null;
		String pri = null;
		TPrivilegeUser puser = null;
		for (String prStr : req_plist) { // prStr=module#node1,node2,.....
			prArr = StringUtils.split(prStr, "#");
			if (prArr.length < 2) {
				continue;
			}
			pri = PrivilegeHelper.genPrivilege(prArr[0], prArr[1]);
			if (StringUtils.isBlank(pri)) {
				continue;
			}
			puser = TPrivilegeUser.valueOf(userid, create_uid, prArr[0], pri);
			if (!newList.contains(puser)) {
				newList.add(puser);
			}
		}
		return newList;
	}
	
	protected TResult<Boolean> settingWithRole(int userid, int role_id) {
		int create_uid = LoginedService.getUserid();
		TBaseRole role = roleService.getUserRole(create_uid, role_id);
		if (null == role) {
			return TResult.valueOf("未找到角色");
		}
		try {
			boolean ret = privilegeUserDao.setting_with_role(userid, role_id, create_uid);
			return TResult.valueT(ret);
		} catch (Exception e) {
			log.error("settingWithRole", e);
		}
		return TResult.valueOf("设置权限时出现了未知的错误");
	}
}

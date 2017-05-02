package com.oloba.module.privilege;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oloba.module.common.LoginedService;
import com.oloba.module.privilege.pojo.TPrivilegeRole;

@Service
public class PrivilegeRoleService {

	@Autowired
	private PrivilegeRoleDao privilegeRoleDao;
	
	
	protected List<TPrivilegeRole> getById(int role_id) {
		return privilegeRoleDao.getById(role_id);
	}
	
	/**
	 * 此方法提供的权限数据用于用户自己展示，不能用于为配置权限提供数据 <br>
	 * 需要配置权限的数据调用 getPrivilegeForSettingById 方法
	 * @param role_id
	 * @return List<Map<String, List<String>>>
	 */
	protected List<Map<String, List<String>>> getPrivilegeById(int role_id) {
		List<TPrivilegeRole> list = privilegeRoleDao.getById(role_id);
		return buildRolePrivilege(list);
	}
	
	/**
	 * 
	 * @param role_id
	 * @return List<Map<String, List<String>>>
	 */
	protected List<Map<String, List<String>>> getPrivilegeForSettingById(int role_id) {
		int create_uid = LoginedService.getUserid();
		List<TPrivilegeRole> list = privilegeRoleDao.getById(role_id, create_uid);
		return buildRolePrivilege(list);
	}
	
	private List<Map<String, List<String>>> buildRolePrivilege(List<TPrivilegeRole> list) {
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		List<Map<String, List<String>>> respList = new ArrayList<>(list.size());
		Map<String, List<String>> map = null;
		for (TPrivilegeRole prole : list) {
			map = new HashMap<>(2);
			map.put(prole.getModule_name(), 
					PrivilegeHelper.privilegeToNodeName(prole.getModule_name(), prole.getPrivilege()));
			respList.add(map);
		}
		return respList;
	}
	
	
	/**
	 * 配置角色的权限
	 * @param role_id
	 * @param req_plist
	 * @return
	 */
	protected boolean setting(int role_id, List<String> req_plist) {
		int create_uid = LoginedService.getUserid();
		List<TPrivilegeRole> list = privilegeRoleDao.getById(role_id, create_uid);
		List<TPrivilegeRole> newList = buildPrivileges(role_id, create_uid, req_plist);
		if (list.isEmpty()) {
			return privilegeRoleDao.insert(role_id, create_uid, newList);
		}
		return privilegeRoleDao.update(role_id, create_uid, newList);
	}
	
	private List<TPrivilegeRole> buildPrivileges(int role_id, int create_uid, 
			List<String> req_plist) {
		List<TPrivilegeRole> newList = new ArrayList<>();
		String[] prArr = null;
		String pri = null;
		TPrivilegeRole prole = null;
		for (String prStr : req_plist) { // prStr=module#node1,node2,.....
			prArr = StringUtils.split(prStr, "#");
			pri = PrivilegeHelper.genPrivilege(prArr[0], prArr[1]);
			if (StringUtils.isBlank(pri)) {
				continue;
			}
			prole = TPrivilegeRole.valueOf(role_id, create_uid, prArr[0], pri);
			if (!newList.contains(prole)) {
				newList.add(prole);
			}
		}
		return newList;
	}
	
}

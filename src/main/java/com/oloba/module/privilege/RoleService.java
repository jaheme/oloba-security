package com.oloba.module.privilege;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oloba.module.privilege.pojo.TBaseRole;

@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;
	
	protected boolean newRole(int create_uid, String role_name, String role_desc) {
		return roleDao.newRole(create_uid, role_name, role_desc);
	}
	
	protected List<TBaseRole> getUserRoles(int create_uid) {
		return roleDao.getUserRoles(create_uid);
	}
	
	public TBaseRole getUserRole(int create_uid, int role_id) {
		return roleDao.getUserRole(create_uid, role_id);
	}
	
	protected boolean updateRole(int create_uid, int role_id,
			String role_name, String role_desc) {
		return roleDao.updateRole(create_uid, role_id, role_name, role_desc);
	}
}

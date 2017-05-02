package com.oloba.module.privilege;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oloba.core.db.SaasDbRepository;
import com.oloba.module.privilege.pojo.TBaseRole;

@Repository
public class RoleDao {

	@Autowired
	private SaasDbRepository saas;
	
	protected boolean newRole(int create_uid, String role_name, String role_desc) {
		String sql = "INSERT INTO t_base_role (role_name, role_desc, create_uid,"
				 + "create_time) VALUES (?,?,?, now())";
		return saas.update(sql, role_name, role_desc, create_uid);
	}
	
	
	protected List<TBaseRole> getUserRoles(int create_uid) {
		return saas.query("SELECT role_id, role_name, role_desc FROM t_base_role"
				+ " WHERE create_uid=? AND enabled=?", 
				TBaseRole.role_privilege_mapper, create_uid, true);
	}
	
	protected TBaseRole getUserRole(int create_uid, int role_id) {
		return saas.queryT("SELECT role_id, role_name, role_desc FROM t_base_role"
				+ " WHERE create_uid=? AND role_id=? AND enabled=?", 
				TBaseRole.role_edit_mapper, create_uid, role_id, true);
	}
	
	protected boolean updateRole(int create_uid, int role_id,
			String role_name, String role_desc) {
		String sql = "UPDATE t_base_role SET role_name=?, role_desc=?"
				+ " WHERE role_id=? AND create_uid=?";
		return saas.update(sql, role_name, role_desc, role_id, create_uid);
	}
}

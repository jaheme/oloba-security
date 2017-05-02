package com.oloba.module.privilege;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oloba.core.db.SaasDbRepository;
import com.oloba.module.privilege.pojo.TPrivilegeRole;

@Repository
public class PrivilegeRoleDao {

	@Autowired
	private SaasDbRepository saas;
	
	protected List<TPrivilegeRole> getById(int role_id) {
		return saas.query("SELECT * FROM t_privilege_role WHERE role_id=?", 
				TPrivilegeRole.mapper, role_id);
	}
	
	protected List<TPrivilegeRole> getById(int role_id, int create_uid) {
		return saas.query("SELECT * FROM t_privilege_role WHERE role_id=? AND create_uid=?", 
				TPrivilegeRole.mapper, role_id, create_uid);
	}
	
	protected boolean insert(int role_id, int create_uid,
			List<TPrivilegeRole> plist) {
		String sql =  "INSERT INTO t_privilege_role (role_id, module_name, privilege,"
				 + " create_uid, update_time) VALUES (?,?,?, ?,now())";
		List<Object[]> params = new ArrayList<>();
		for (TPrivilegeRole pr : plist) {
			params.add(new Object[]{
					pr.getRole_id(),
					pr.getModule_name(),
					pr.getPrivilege(),
					create_uid
			});
		}
		int count = saas.updateBatch(sql, params);
		return count == plist.size() ? true : false;
	}
	
	protected boolean delete(List<Integer> idList) {
		String sql =  "DELETE FROM t_privilege_role WHERE id=?";
		List<Object[]> params = new ArrayList<>();
		for (Integer id : idList) {
			params.add(new Object[]{
					id
			});
		}
		int count = saas.updateBatch(sql, params);
		return count == idList.size() ? true : false;
	}
	
	protected boolean delete(int role_id, int create_uid) {
		String sql =  "DELETE FROM t_privilege_role WHERE role_id=? AND create_uid=?";
		return saas.update(sql, role_id, create_uid);
	}
	
	protected boolean update(int role_id, int create_uid, List<TPrivilegeRole> plist) {
		delete(role_id, create_uid);
		return insert(role_id, create_uid, plist);
//		String sql =  "UPDATE t_privilege_role SET privilege=?, update_time=now() WHERE id=?";
//		List<Object[]> params = new ArrayList<>();
//		for (TPrivilegeRole tr : plist) {
//			params.add(new Object[]{
//					tr.getPrivilege(),
//					tr.getId()
//			});
//		}
//		int count = saas.updateBatch(sql, params);
//		return count == plist.size() ? true : false;
	}
	
}

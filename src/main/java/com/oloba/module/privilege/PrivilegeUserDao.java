package com.oloba.module.privilege;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oloba.core.db.SaasDbRepository;
import com.oloba.module.privilege.pojo.TPrivilegeUser;

@Repository
public class PrivilegeUserDao {

	@Autowired
	private SaasDbRepository saas;

	protected List<TPrivilegeUser> getByUserid(int userid) {
		String sql = "SELECT role_id, module_name, privilege"
				+ " FROM t_privilege_user WHERE user_id=?";
		return saas.query(sql, TPrivilegeUser.privilege_mapper, userid);
	}
	
	
	protected boolean insert(int userid, int create_uid,
			List<TPrivilegeUser> plist) {
		int role_id = 0;
		String sql =  "INSERT INTO t_privilege_user (user_id, role_id, create_uid,"
				 + " module_name, privilege, update_time) VALUES (?,?,?, ?,?,now())";
		List<Object[]> params = new ArrayList<>();
		for (TPrivilegeUser pr : plist) {
			params.add(new Object[]{
					userid,
					role_id,
					create_uid,
					pr.getModule_name(),
					pr.getPrivilege(),
			});
		}
		int count = saas.updateBatch(sql, params);
		return count == plist.size() ? true : false;
	}
	
	protected boolean delete(int userid) {
		String sql =  "DELETE FROM t_privilege_user WHERE user_id=?";
		return saas.update(sql, userid);
	}

	@Transactional
	protected boolean update(int userid, int create_uid, List<TPrivilegeUser> plist) {
		delete(userid);
		return insert(userid, create_uid, plist);
	}
	
	@Transactional
	protected boolean setting_with_role(int userid, int role_id,
			int create_uid) {
		delete(userid);
		String sql =  "INSERT INTO t_privilege_user (user_id, role_id, create_uid,"
				 + " update_time) VALUES (?,?,?, now())";
		return saas.update(sql, userid, role_id, create_uid);
	}
}

package com.oloba.module.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oloba.core.db.SaasDbRepository;
import com.oloba.module.user.pojo.TBaseUser;
import com.oloba.module.user.pojo.UserMapper;

@Repository
public class UserDao {
	
	@Autowired
	private SaasDbRepository saas;
	
	protected TBaseUser getUserByUsername(String username) {
		String sql = "SELECT id, username, password, nickname, company_id, enabled"
				+ " FROM t_base_user WHERE username=?";
		return saas.queryT(sql, UserMapper.user_mapper, username);
	}
	
	protected List<TBaseUser> getMyUserList(int create_uid) {
		String sql = "SELECT id, username, nickname, enabled"
				+ " FROM t_base_user WHERE id=? OR create_uid=?";
		return saas.query(sql, UserMapper.privilege_mapper, create_uid, create_uid);
	}
	
	protected TBaseUser getUserById(int userid) {
		String sql = "SELECT id, username, password, nickname, company_id, enabled"
				+ " FROM t_base_user WHERE id=?";
		return saas.queryT(sql, UserMapper.user_mapper, userid);
	}
	
	protected int getUserId(String username) {
		String sql = "SELECT id FROM t_base_user WHERE username=?";
		return saas.queryInt(sql, username);
	}
	
	
	protected boolean newUser(TBaseUser user) {
		String sql = "INSERT INTO t_base_user (username, password, nickname,"
				+ " company_id, create_uid, enabled,"
				+ " reg_time, login_time ) VALUES (?,?,?, ?,?,?, now(),now())";
		return saas.update(sql, user.getUsername(), user.getPassword(), 
				user.getNickname(), user.getCompany_id(), user.getCreate_uid(),
				user.isEnabled());
	}
	
	protected boolean updateUser(TBaseUser user) {
		String sql = "UPDATE t_base_user SET username=?, nickname=?,"
			+ " company_id=?, create_uid=?, enabled=? WHERE id=?";
		return saas.update(sql, user.getUsername(), user.getNickname(), 
				user.getCompany_id(), user.getCreate_uid(),
				user.isEnabled(), user.getId());
	}

}

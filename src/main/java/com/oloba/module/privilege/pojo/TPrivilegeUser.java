package com.oloba.module.privilege.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

public class TPrivilegeUser {

	/** 用户ID */
	private int user_id;
	/** 角色ID */
	private int role_id;
	/** 生成此权限的用户ID */
	private int create_uid;
	/** 模块名称PModule.pname */
	private String module_name;
	/** 权限信息 */
	private String privilege;
	/** 更新时间 */
	private Date update_time;

	public static RowMapper<TPrivilegeUser> mapper = new RowMapper<TPrivilegeUser>() {

		@Override
		public TPrivilegeUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			TPrivilegeUser pojo = new TPrivilegeUser();
			pojo.user_id = rs.getInt("user_id");
			pojo.role_id = rs.getInt("role_id");
			pojo.create_uid = rs.getInt("create_uid");
			pojo.module_name = rs.getString("module_name");
			pojo.privilege = rs.getString("privilege");
			pojo.update_time = rs.getDate("update_time");
			return pojo;
		}
	};
	
	public static RowMapper<TPrivilegeUser> privilege_mapper = new RowMapper<TPrivilegeUser>() {

		@Override
		public TPrivilegeUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			TPrivilegeUser pojo = new TPrivilegeUser();
			pojo.role_id = rs.getInt("role_id");
			pojo.module_name = rs.getString("module_name");
			pojo.privilege = rs.getString("privilege");
			return pojo;
		}
	};
	
	public static TPrivilegeUser valueOf(int userid, int create_uid, 
			String module_name, String privilege) {
		TPrivilegeUser tr = new TPrivilegeUser();
		tr.setUser_id(userid);
		tr.setCreate_uid(create_uid);
		tr.setModule_name(module_name);
		tr.setPrivilege(privilege);
		return tr;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public int getCreate_uid() {
		return create_uid;
	}

	public void setCreate_uid(int create_uid) {
		this.create_uid = create_uid;
	}

	public String getModule_name() {
		return module_name;
	}

	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		TPrivilegeUser pu = (TPrivilegeUser)obj;
		if (null == pu.getModule_name()) {
			return false;
		}
		if (this.module_name.equals(pu.getModule_name())) {
			return true;
		}
		return false;
	}

	
	
	
}

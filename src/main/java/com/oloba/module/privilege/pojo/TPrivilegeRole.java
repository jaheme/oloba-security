package com.oloba.module.privilege.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TPrivilegeRole {

	/**  */
	private int id;
	/** 角色ID */
	private int role_id;
	/** 模块名称PModule.pname */
	private String module_name;
	/** 权限信息 */
	private String privilege;
	/**  */
	private int create_uid;

	public static RowMapper<TPrivilegeRole> mapper = new RowMapper<TPrivilegeRole>() {

		@Override
		public TPrivilegeRole mapRow(ResultSet rs, int rowNum) throws SQLException {
			TPrivilegeRole pojo = new TPrivilegeRole();
			pojo.id = rs.getInt("id");
			pojo.role_id = rs.getInt("role_id");
			pojo.module_name = rs.getString("module_name");
			pojo.privilege = rs.getString("privilege");
			pojo.create_uid = rs.getInt("create_uid");
			return pojo;
		}
	};
	
	public static TPrivilegeRole valueOf(int role_id, int create_uid, 
			String module_name, String privilege) {
		TPrivilegeRole tr = new TPrivilegeRole();
		tr.setRole_id(role_id);
		tr.setCreate_uid(create_uid);
		tr.setModule_name(module_name);
		tr.setPrivilege(privilege);
		return tr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
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

	public int getCreate_uid() {
		return create_uid;
	}

	public void setCreate_uid(int create_uid) {
		this.create_uid = create_uid;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		TPrivilegeRole tr = (TPrivilegeRole)obj;
		if (this.module_name.equals(tr.getModule_name())) {
			return true;
		}
		return false;
	}
	
	

}

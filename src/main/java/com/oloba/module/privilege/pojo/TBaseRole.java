package com.oloba.module.privilege.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

public class TBaseRole {

	/**  */
	private int role_id;
	/** 角色名称 */
	private String role_name;
	/** 角色描述 */
	private String role_desc;
	/** 创建用户的id */
	private int create_uid;
	/** 是否可用 */
	private boolean enabled;
	/**  */
	private Date create_time;
	

	
	public static RowMapper<TBaseRole> role_mapper = new RowMapper<TBaseRole>() {

		@Override
		public TBaseRole mapRow(ResultSet rs, int rowNum) throws SQLException {
			TBaseRole pojo = new TBaseRole();
			pojo.setRole_id(rs.getInt("role_id"));
			pojo.setRole_name(rs.getString("role_name"));
			pojo.setRole_desc(rs.getString("role_desc"));
			pojo.setCreate_uid(rs.getInt("create_uid"));
			pojo.setEnabled(rs.getBoolean("enabled"));
			pojo.setCreate_time(rs.getDate("create_time"));
			return pojo;
		}
	};
	
	public static RowMapper<TBaseRole> role_edit_mapper = new RowMapper<TBaseRole>() {

		@Override
		public TBaseRole mapRow(ResultSet rs, int rowNum) throws SQLException {
			TBaseRole pojo = new TBaseRole();
			pojo.setRole_id(rs.getInt("role_id"));
			pojo.setRole_name(rs.getString("role_name"));
			pojo.setRole_desc(rs.getString("role_desc"));
			return pojo;
		}
	};
	
	public static RowMapper<TBaseRole> role_privilege_mapper = new RowMapper<TBaseRole>() {

		@Override
		public TBaseRole mapRow(ResultSet rs, int rowNum) throws SQLException {
			TBaseRole pojo = new TBaseRole();
			pojo.setRole_id(rs.getInt("role_id"));
			pojo.setRole_name(rs.getString("role_name"));
			pojo.setRole_desc(rs.getString("role_desc"));
			return pojo;
		}
	};

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getRole_desc() {
		return role_desc;
	}

	public void setRole_desc(String role_desc) {
		this.role_desc = role_desc;
	}

	public int getCreate_uid() {
		return create_uid;
	}

	public void setCreate_uid(int create_uid) {
		this.create_uid = create_uid;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

}

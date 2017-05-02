package com.oloba.module.user.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.oloba.core.utils.DateUtil;

public class UserMapper {
	

	public static RowMapper<TBaseUser> mapper = new RowMapper<TBaseUser>() {

		@Override
		public TBaseUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			TBaseUser pojo = new TBaseUser();
			pojo.setId(rs.getInt("id"));
			pojo.setUsername(rs.getString("username"));
			pojo.setPassword(rs.getString("password"));
			pojo.setNickname(rs.getString("nickname"));
			pojo.setCompany_id(rs.getInt("company_id"));
			pojo.setEnabled(rs.getBoolean("enabled"));
			Date d = rs.getTimestamp("reg_time");
			pojo.setReg_time(null == d ? null : DateUtil.dateStr(d));
			d = rs.getTimestamp("login_time");
			pojo.setLogin_time(null == d ? null : DateUtil.dateStr(d));
			return pojo;
		}
	};
	
	public static RowMapper<TBaseUser> user_mapper = new RowMapper<TBaseUser>() {

		@Override
		public TBaseUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			TBaseUser pojo = new TBaseUser();
			pojo.setId(rs.getInt("id"));
			pojo.setUsername(rs.getString("username"));
			pojo.setPassword(rs.getString("password"));
			pojo.setNickname(rs.getString("nickname"));
			pojo.setCompany_id(rs.getInt("company_id"));
			pojo.setEnabled(rs.getBoolean("enabled"));
			return pojo;
		}
	};
	
	public static RowMapper<TBaseUser> privilege_mapper = new RowMapper<TBaseUser>() {

		@Override
		public TBaseUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			TBaseUser pojo = new TBaseUser();
			pojo.setId(rs.getInt("id"));
			pojo.setUsername(rs.getString("username"));
			pojo.setNickname(rs.getString("nickname"));
			pojo.setEnabled(rs.getBoolean("enabled"));
			return pojo;
		}
	};
	
}

package com.oloba.module.user.pojo;

public class TBaseUser {
	
	/**  */
	private int id;
	/** 帐号 */
	private String username;
	/**  */
	private String password;
	/** 姓名或昵称 */
	private String nickname;
	/** 所属公司标识 */
	private int company_id;
	/** 创建者标识 */
	private int create_uid;
	/** 是否可用 */
	private boolean enabled;
	/** 注册时间 */
	private String reg_time;
	/** 最新登录时间 */
	private String login_time;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
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

	public String getReg_time() {
		return reg_time;
	}

	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}

	public String getLogin_time() {
		return login_time;
	}

	public void setLogin_time(String login_time) {
		this.login_time = login_time;
	}

}

package com.oloba.module.user.model;

import com.oloba.module.user.pojo.TBaseUser;

public class UserForPrivilege {

	/**  */
	private int id;
	/** 帐号 */
	private String username;
	/** 姓名或昵称 */
	private String nickname;
	/** 是否可用 */
	private boolean enabled;
	
	public UserForPrivilege(TBaseUser user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.nickname = user.getNickname();
		this.enabled = user.isEnabled();
	}

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}

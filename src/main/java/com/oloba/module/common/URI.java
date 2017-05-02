package com.oloba.module.common;

/**
 * URI的结构：PModual.pname+"/"+PNode.pname[+"/自定义"]
 * @author shanpin
 *
 */
public interface URI {
	
	// 总前缀
	public final static String HS = "/hs/";

	public final static String LOGIN = "/";
	public final static String MAIN = "main";
	public final static String VERIFY_CODE = "verify_code";
	public final static String LOGIN_FAILURE = "login/failure";
	public final static String AUTH_FAILURE = "auth_failure";
	
	/** 权限配置的基本数据 */
	public final static String PRIVILEGE_MANAGER = "privilege/manager";

	public final static String PRIVILEGE_ROLE_MANAGER = "privilege_role/manager";
	public final static String PRIVILEGE_ROLE_NEW = "privilege_role/new";
	public final static String PRIVILEGE_ROLE_EDIT = "privilege_role/edit";
	public final static String PRIVILEGE_ROLE_EDIT_SAVE = "privilege_role/edit/save";
	public final static String PRIVILEGE_ROLE_SETTING = "privilege_role/setting";
	public final static String PRIVILEGE_ROLE_GET = "privilege_role/get";
	
	/** 用户权限配置 */
	public final static String PRIVILEGE_USER_MANAGER = "privilege_user/manager";
	public final static String PRIVILEGE_USER_GET = "privilege_user/get";
	public final static String PRIVILEGE_USER_SETTING = "privilege_user/setting";
	public final static String PRIVILEGE_USER_SETTING_WITHROLE = "privilege_user/setting/withrole";

	public final static String USER_MANAGER = "user/manager";
	/** 添加、新增用户 */
	public final static String USER_NEW = "user/new";
	/** 编辑用户 */
	public final static String USER_EDIT_LOAD = "user/edit/load";
	/** 编辑用户 */
	public final static String USER_EDIT_SAVE = "user/edit/save";

}

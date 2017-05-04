package com.oloba.module.privilege.model;

import org.apache.commons.lang3.StringUtils;

/**
 * ！！此对象代码不能改动！！
 * 主菜单的菜单配置。
 * 放在代码上，防止用户更改。
 * @author oloba
 *
 */
public enum PModule {

	///// 以下代码不可改动！！！！！！
	PRIVILEGE("privilege", "权限基础数据", PNode.MANAGER),
	
	PRIVILEGE_ROLE("privilege_role", "角色管理配置", PNode.MANAGER, PNode.NEW,
			PNode.EDIT, PNode.SETTING, PNode.GET),
	
	PRIVILEGE_USER("privilege_user", "用户管理配置", PNode.MANAGER, PNode.NEW,
			PNode.EDIT, PNode.SETTING, PNode.GET),

	USER("user", "用户管理", PNode.MANAGER, PNode.NEW, PNode.EDIT)
	;
	
	public final String pname;
	public final String desc;
	public final PNode[] nodes;	// 菜单拥有的功能点
	
	private PModule(String pname, String desc, PNode... nodes) {
		this.pname = pname;
		this.desc = desc;
		this.nodes = nodes;
	}
	
	public String getPname() {
		return pname;
	}
	public String getDesc() {
		return desc;
	}

	public PNode[] getNodes() {
		return nodes;
	}

	
	public static final PModule get(String pname) {
		PModule[] ms = PModule.values();
		for (PModule m : ms) {
			if (StringUtils.equals(m.pname, pname)) {
				return m;
			}
		}
		return null;
	}
	
}

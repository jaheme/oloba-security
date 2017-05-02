package com.oloba.module.privilege.module_tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于权限控制的菜单树列表显示
 * @author jahe.lai
 *
 */
public class AccessMenu {
	
	private final String desc;
	private final List<AccessModule> moduleList = new ArrayList<>();
	private final List<AccessMenu> subMenuList = new ArrayList<>();
	
	public AccessMenu(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public List<AccessModule> getModuleList() {
		return moduleList;
	}

	public List<AccessMenu> getSubMenuList() {
		return subMenuList;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		AccessMenu menu = (AccessMenu)obj;
		if (this.desc.equals(menu.getDesc())) {
			return true;
		}
		return false;
	}
	
}

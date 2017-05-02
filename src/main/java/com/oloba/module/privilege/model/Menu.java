package com.oloba.module.privilege.model;

import java.util.ArrayList;
import java.util.List;

import com.oloba.core.utils.JsonUtil;

/**
 * 
 * @author oloba
 *
 */
public class Menu {
	
	private int id; // 当前ID PMenu.ordinal() + 1;
	private int pid = 0; // 父id PMenu.ordinal() + 1
	private String name; // 菜单名称
	private List<Module> moduleList = new ArrayList<>();
	private List<Menu> subList = new ArrayList<>();

	public static Menu valueOf(PMenu menu) {
		Menu m = new Menu();
		m.id = menu.ordinal() + 1;
		m.name = menu.getDesc();
		PMenu parent_menu = menu.parent_menu;
		if (null != parent_menu) {
			m.pid = parent_menu.ordinal() + 1;
		}
		for (PModule module : menu.modules) {
			m.moduleList.add(new Module(m.id, module));
		}
		return m;
	}
	
	public static Menu valueOf(PMenu menu, String module_pname, 
			List<String> nodeNameList) {
		Menu m = new Menu();
		m.id = menu.ordinal() + 1;
		m.name = menu.getDesc();
		PMenu parent_menu = menu.parent_menu;
		if (null != parent_menu) {
			m.pid = parent_menu.ordinal() + 1;
		}
		if (null != module_pname) {
			for (PModule module : menu.modules) {
				if (module.pname.equals(module_pname)) {
					m.moduleList.add(new Module(m.id, module, nodeNameList));
				}
			}
		}
		return m;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Module> getModuleList() {
		return moduleList;
	}

	public List<Menu> getSubList() {
		return subList;
	}

	public void setSubList(List<Menu> subList) {
		this.subList = subList;
	}
	
	

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		Menu menu = (Menu)obj;
		if (this.id == menu.getId()) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return JsonUtil.toStr(this);
	}

}


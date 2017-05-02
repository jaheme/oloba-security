package com.oloba.module.privilege.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 菜单结构 <br>
 * 菜单上的链接地址是每个模块名(PModule.pname)+权限节点数组(PModule.nodes)的第一个元素。<br>
 *
 * @author oloba
 * @see PModule
 *
 */
public enum PMenu {

	///// 以下代码不可改动！！！！！！
	SYS_BASE(null, "系统基本配置", PModule.PRIVILEGE, PModule.PRIVILEGE_ROLE, 
			PModule.PRIVILEGE_USER),
	TEST_BASE(null, "测试", PModule.PRIVILEGE_USER),
	TEST2_BASE(SYS_BASE, "测试2", PModule.USER)
	;
	
	public final PMenu parent_menu;	// 空：代表是第一层。不空：值是上层菜单。
	public final String desc;
	public final PModule[] modules;	// 模块列表
	
	private PMenu(PMenu parent_menu, String desc, PModule... modules) {
		this.parent_menu = parent_menu;
		this.desc = desc;
		this.modules = modules;
	}
	
	
	public PMenu getParent_menu() {
		return parent_menu;
	}

	public String getDesc() {
		return desc;
	}

	public PModule[] getModules() {
		return modules;
	}



	/**
	 * 根据提供的module_pname获取所属于的PMenu对象。
	 * @param module_pname
	 * @return PMenu OR NULL
	 */
	public static final PMenu parentPMenu(String module_pname) {
		for (PMenu menu : PMenu.values()) {
			for (PModule module : menu.modules) {
				if (StringUtils.equals(module_pname, module.pname)) {
					return menu;
				}
			}
		}
		return null;
	}
	
	// TODO 暂时只处理一层子菜单，以后完善。
	public static final List<Menu> getMenuList() {
		List<Menu> list = new ArrayList<>();
		PMenu[] menuArr = PMenu.values();
		for (PMenu menu : menuArr) {
			if (null != menu.parent_menu) { // 是子菜单
				continue;
			}
			Menu m = Menu.valueOf(menu);
			for (PMenu subMenu : menuArr) { // 处理子菜单
				if (menu == subMenu.parent_menu) {
					m.getSubList().add(Menu.valueOf(subMenu));
				}
			}
			
			list.add(m);
		}
		return list;
	}
	
}

package com.oloba.module.privilege;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.oloba.module.common.URI;
import com.oloba.module.privilege.model.Menu;
import com.oloba.module.privilege.model.Module;
import com.oloba.module.privilege.model.PMenu;
import com.oloba.module.privilege.model.PModule;
import com.oloba.module.privilege.model.PNode;
import com.oloba.module.privilege.module_tree.AccessMenu;
import com.oloba.module.privilege.module_tree.AccessModule;
import com.oloba.module.privilege.pojo.TPrivilegeRole;
import com.oloba.module.privilege.pojo.TPrivilegeUser;
import com.oloba.security.MyUserDetails;

/**
 * 全部菜单的生成、根据用户权限生成用于权限配置的菜单列表、根据用户权限生成访问菜单
 * @author jahe.lai
 *
 */
@Service
public class PrivilegeService {
	
	private static final String SUPER = "super";

	@Autowired
	private PrivilegeRoleService privilegeRoleService;
	@Autowired
	private PrivilegeUserDao privilegeUserDao;
	
	
	/**
	 * 生成登录成功用户的访问菜单，并放到session供渲染。
	 * @return
	 */
	public List<AccessMenu> mineAccessMenu() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		MyUserDetails userDetails = (MyUserDetails)principal;
		int userid = userDetails.getId();
		String username = userDetails.getUsername();
		if (SUPER.equals(username)) {
			// 返回所有的菜单
			return buildAllMenu();
		}
		List<TPrivilegeUser> list = privilegeUserDao.getByUserid(userid);
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		if (list.size() == 1) {
			TPrivilegeUser puser = list.get(0);
			if (puser.getRole_id() > 0) { // 只认角色的权限
				return buildAccessByRolePrivilege(puser.getRole_id());
			}
		}
		return buildAccessByUserPrivilege(list);
	}
	
	private List<AccessMenu> buildAllMenu() {
		List<AccessMenu> menuList = new ArrayList<>();
		for (PMenu menu : PMenu.values()) {
			if (null != menu.parent_menu) { // sub menu
				continue;
			}
			addPMenu(menuList, menu);
		}
		return menuList;
	}
	
	/**
	 * 从PMenu定义的顺序从上到下生成访问菜单
	 * @param menuList
	 * @param pmenu
	 */
	private void addPMenu(List<AccessMenu> menuList, PMenu pmenu) {
		AccessMenu tree_menu = new AccessMenu(pmenu.getDesc());
		for (PModule module : pmenu.modules) {
			tree_menu.getModuleList().add(
					new AccessModule(module.getDesc(), genUri(module)) );
		}
		// 处理子菜单
		for (PMenu sub : PMenu.values()) {
			if (null == sub.parent_menu) {
				continue;
			}
			if (pmenu == sub.parent_menu) {	// 当前PMenu加进tree_menu的subMenuList
				addPMenu(tree_menu.getSubMenuList(), sub);
			}
		}
		menuList.add(tree_menu);
	}
	
	/**
	 * 根据角色的权限数据，生成访问菜单
	 * @param role_id
	 * @return
	 */
	private List<AccessMenu> buildAccessByRolePrivilege(int role_id) {
		List<AccessMenu> menuList = new ArrayList<>();
		List<TPrivilegeRole> rlist = privilegeRoleService.getById(role_id);
		for (Iterator<TPrivilegeRole> it = rlist.iterator(); it.hasNext();) {
			buildAccessMenu(menuList, it.next().getModule_name());
		}
		return menuList;
	}

	/**
	 * 根据用户的权限配置数据，生成访问菜单
	 * @param list
	 * @return
	 */
	private List<AccessMenu> buildAccessByUserPrivilege(List<TPrivilegeUser> list) {
		List<AccessMenu> menuList = new ArrayList<>();
		for (Iterator<TPrivilegeUser> it = list.iterator(); it.hasNext();) {
			buildAccessMenu(menuList, it.next().getModule_name());
		}
		return menuList;
	}
	
	
	private void buildAccessMenu(List<AccessMenu> list, String module_pname) {
		PMenu modulePMenu = PMenu.parentPMenu(module_pname);
		if (null == modulePMenu) {
			return;
		}
		PMenu parentMenu = modulePMenu.parent_menu; // 查看是否有父级菜单定义
		if (null == parentMenu) {
			buildAccessMenu(list, modulePMenu, module_pname);
		} else {
			// 最高层菜单加入
			AccessMenu menu = buildAccessMenu(list, parentMenu, null);
			// 本菜单加入
			buildAccessMenu(menu.getSubMenuList(), modulePMenu, module_pname);
		}
	}

	
	/**
	 * 由下级往上级生成访问菜单
	 * @param menuList
	 * @param pmenu
	 * @param modual_name
	 */
	private AccessMenu buildAccessMenu(List<AccessMenu> menuList, PMenu pmenu, String modual_name) {
		AccessMenu tree_menu = new AccessMenu(pmenu.getDesc());
		if (menuList.contains(tree_menu)) {
			for (AccessMenu tm : menuList) {
				if (tm.equals(tree_menu)) {
					tree_menu = tm;
					break;
				}
			}
		} else {
			menuList.add(tree_menu);
		}
		if (StringUtils.isNoneBlank(modual_name)) {
			PModule module = PModule.get(modual_name);
			tree_menu.getModuleList().add(
					new AccessModule(module.getDesc(), genUri(module)) );
		}
		return tree_menu;
	}
	
	private String genUri(PModule module) {
		return URI.HS + module.pname +"/" + module.getNodes()[0].getPname();
	}
	
	
	////////////////////////////// 用户可配置的菜单列表
	

	/**
	 * 获取用户的可用菜单数据，供权限配置页面使用。
	 * @return
	 */
	public List<Menu> minePMenu() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		MyUserDetails userDetails = (MyUserDetails)principal;
		int userid = userDetails.getId();
		String username = userDetails.getUsername();
		if (SUPER.equals(username)) {
			// 返回所有的菜单
			return PMenu.getMenuList();
		}
		List<Map<String, List<String>>> listmap = userPrivilege(userid);
		List<Menu> menuList = new ArrayList<>();
		for (Map<String, List<String>> map : listmap) {
			String module_pname = map.keySet().iterator().next();
			buildPMenu(menuList, module_pname, map.get(module_pname));
		}
		return menuList;
	}
	
	/**
	 * 建立用户的菜单
	 * @param list
	 * @param module_pname
	 */
	private static final void buildPMenu(List<Menu> list, String module_pname, 
			List<String> nodeNameList) {
		PMenu modulePMenu = PMenu.parentPMenu(module_pname);
		if (null == modulePMenu) {
			return;
		}
		PMenu pmenu = modulePMenu.parent_menu; // 查看是否有父级菜单定义
		if (null == pmenu) {
			// 先将父菜单加入
			addMenu(list, modulePMenu, module_pname, nodeNameList);
		} else {
			// 最高层菜单加入
			Menu menu = addMenu(list, pmenu, null, null);
			// 子菜单加入
			addMenu(menu.getSubList(), modulePMenu, module_pname, nodeNameList);
		}
	}
	
	private static Menu addMenu(List<Menu> list, PMenu pmenu, 
			String module_name, List<String> nodeNameList) {
		Menu menu = Menu.valueOf(pmenu, module_name, nodeNameList);
		if (!list.contains(menu)) {
			list.add(menu);
			return menu;
		}
		for (Menu m : list) {
			if (m.equals(menu)) {
				m.getModuleList().add(new Module(m.getId(),
						PModule.get(module_name), nodeNameList) );
				break;
			}
		}
		return menu;
	}
	
	/////////////////////  user privilege for filter & privilege-setting ////////////////

	/**
	 * 我的权限结构数据
	 * @param userid
	 * @return
	 */
	protected List<Map<String, List<String>>> minePrivilege(MyUserDetails userDetails) {
		int userid = userDetails.getId();
		String username = userDetails.getUsername();
		if (SUPER.equals(username)) {
			return superPrivilege();
		}
		return userPrivilege(userid);
	}
	
	/**
	 * 获取用户的权限,供过滤器权限校验、此用户给用户或角色配置权限。
	 * @param userid 当前登录用户ID/被查看的用户ID
	 * @return
	 */
	protected List<Map<String, List<String>>> userPrivilege(int userid) {
		List<TPrivilegeUser> list = privilegeUserDao.getByUserid(userid);
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		if (list.size() == 1) {
			TPrivilegeUser puser = list.get(0);
			if (puser.getRole_id() > 0) { // 只认角色的权限
				return privilegeRoleService.getPrivilegeById(puser.getRole_id());
			}
		}
		List<Map<String, List<String>>> respList = new ArrayList<>(list.size());
		Map<String, List<String>> map = null;
		for (TPrivilegeUser puser : list) {
			map = new HashMap<>(2);
			map.put(puser.getModule_name(), 
					PrivilegeHelper.privilegeToNodeName(puser.getModule_name(), puser.getPrivilege()));
			respList.add(map);
		}
		return respList;
	}
	
	/**
	 * 超级用户的权限
	 * @return
	 */
	protected List<Map<String, List<String>>> superPrivilege() {
		List<Map<String, List<String>>> list = new ArrayList<>();
		Map<String, List<String>> map = null;
		List<String> nodeNameList = null;
		for (PMenu pmenu : PMenu.values()) {
			for (PModule module : pmenu.getModules()) {
				map = new HashMap<>(2);
				nodeNameList = new ArrayList<>(module.getNodes().length);
				for (PNode node : module.getNodes()) {
					nodeNameList.add(node.getPname());
				}
				map.put(module.getPname(), nodeNameList);
				list.add(map);
			}
		}
		return list;
	}
}

package com.oloba.module.privilege;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oloba.module.privilege.model.PModule;
import com.oloba.module.privilege.model.PNode;

public class PrivilegeHelper {
	
	private static Logger log = LoggerFactory.getLogger(PrivilegeHelper.class);
	private static final char ZERO = '0';
	private static final char ONE = '1';
	

	/**
	 * 判断此URI是否合理: 此PModule是否有此PNode.
	 * @param module_pname 菜单模块名称	PModule.pname
	 * @param node_pname 权限节点名称 PNode.pname
	 * @return
	 */
	public static final boolean isModuleNodes(String module_pname, String node_pname) {
		PModule[] menuArr = PModule.values();
		for (PModule module : menuArr) {
			if (!StringUtils.equals(module.pname, module_pname)) {
				continue;
			}
			for (PNode node : module.nodes) {
				if (StringUtils.equals(node.pname, node_pname)) {
					return true;
				}
			}
			break;
		}
		return false;
	}
	
	public static final boolean isModuleNodes(PModule module, String node_pname) {
		for (PNode node : module.nodes) {
			if (StringUtils.equals(node.pname, node_pname)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否有此权限节点的访问允许
	 * 
	 * @param pname	PNode.pname
	 * @param privilege 权限字符串
	 * @return
	 */
	public static final boolean hasNode(final String pname, final String privilege) {
		PNode[] pnArr = PNode.values();
		for (PNode pn : pnArr) {
			if (pn.pname.equals(pname)) {
				if (privilege.length() > pn.index) {
					char nv = privilege.charAt(pn.index);
					return ZERO == nv ? false : true;
				}
				break;
			}
		}
		return false;
	}
	
	public static final PNode getPNode(int index) {
		PNode[] pnArr = PNode.values();
		for (PNode pn : pnArr) {
			if (pn.getIndex() == index) {
				return pn;
			}
		}
		return null;
	}
	
	
	/**
	 * 生成模块的权限表示字条串
	 * @param pname	模块名称
	 * @param nodes 节点列表，以"|"间隔。
	 * @return
	 */
	public static final String genPrivilege(String pname, String nodes) {
		if (StringUtils.isBlank(nodes)) {
			return null;
		}
		PModule pmodule = PModule.get(pname);
		if (null == pmodule) {
			return null;
		}
		String[] nodeArr = StringUtils.split(nodes, "|");
		// 检验
		for (String node_pname : nodeArr) {
			if (!isModuleNodes(pmodule, node_pname)) {
				log.error("PModule={} exclude {} . abort!", pname, node_pname);
				return null;
			}
		}
		return genNodePrivilege(nodeArr);
	}
	
	private static final String genNodePrivilege(String[] nodeArr) {
		PNode[] pnodeArr = PNode.values();
		char pri[] = new char[pnodeArr.length];
		for (int i=0; i<pnodeArr.length; i++) {
			pri[i] = ZERO;
		}
		// 设置拥有权限位为1
		PNode node = null;
		for (String node_pname : nodeArr) {
			node = PNode.get(node_pname);
			if (null == node) {
				continue;
			}
			pri[node.getIndex()] = ONE;
		}
		return new String(pri);
	}
	
	/**
	 * 权限字符表示转化为
	 * @param privilege 0101表现方式
	 * @return List<String>
	 */
	public static List<String> privilegeToNodeName(String module_pname, String privilege) {
		PModule pmodule = PModule.get(module_pname);
		if (null == pmodule) {
			return Collections.emptyList();
		}
		StringBuilder build = new StringBuilder();
		char priArr[] = privilege.toCharArray();
		String node_pname = null;
		for (int index=0; index<priArr.length; index++) {
			if (priArr[index] == ONE) {
				node_pname = getPNode(index).getPname();
				if (isModuleNodes(pmodule, node_pname)) 
					build.append(node_pname + ",");
			}
		}
		build.deleteCharAt(build.length()-1);
		return Arrays.asList(build.toString().split(","));
	}
	
}

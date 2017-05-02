package com.oloba.module.privilege;

/**
 * 用户的权限对象
 * @author oloba
 *
 */
public class UserPrivilege {

	/** 模块名称:PModual.pname */
	private String modual;
	/** 此模块的权限节点字符串 （EX: 1010101010） */
	private String nodes;

	public String getModual() {
		return modual;
	}

	public void setModual(String modual) {
		this.modual = modual;
	}

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		}
		UserPrivilege p = (UserPrivilege)obj;
		if (this.modual.equals(p.getModual())) {
			return true;
		}
		return false;
	}
	
	

}

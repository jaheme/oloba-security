package com.oloba.module.privilege.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Module {

	private final int id;	// PModule.ordinal() + 100;
	private final int pid;	// Menu.id
	
	private final String pname;
	private final String desc;
	private final List<PNode> nodeList = new ArrayList<>();
	
	public Module (int pid, PModule module) {
		this(pid, module, null);
	}
	
	public Module (int pid, PModule module, List<String> nodeNameList) {
		this.id = module.ordinal() + 100;
		this.pid = pid;
		this.pname = module.getPname();
		this.desc = module.getDesc();
		if (null == nodeNameList) {
			this.nodeList.addAll(Arrays.asList(module.nodes));
		} else {
			for (String nodeName : nodeNameList) {
				this.nodeList.add(PNode.get(nodeName));
			}
		}
	}

	public int getId() {
		return id;
	}

	public int getPid() {
		return pid;
	}

	public String getPname() {
		return pname;
	}

	public String getDesc() {
		return desc;
	}

	public List<PNode> getNodeList() {
		return nodeList;
	}

	

}

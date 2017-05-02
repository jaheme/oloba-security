package com.oloba.module.privilege.module_tree;

public class AccessModule {

	private final String desc;
	private final String uri;
	
	public AccessModule(String desc, String uri) {
		this.desc = desc;
		this.uri = uri;
	}

	public String getDesc() {
		return desc;
	}

	public String getUri() {
		return uri;
	}

}

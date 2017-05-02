package com.oloba.module.privilege.model;

/**
 * ！！此对象代码不能改动！！
 * 模块领域的权限基本控制节点 <br>
 * 一个模块的权限集合用字符串表示，字符串只有0、1两个值，0表示字符索引无权限、1表示此字符索引位置拥有权限。<br>
 * 例子："10000000" 表示只有manager/**的权限。
 * 
 * @author oloba
 *
 */
public enum PNode {

	///// 以下代码不可改动！！！！！！
	MANAGER(0, "manager", "管理"), 		// 
	LIST(1, "list", "列表"), 			// 
	QUERY(2, "query", "查询/搜索"), 		// 
	SEARCH(3, "search", "查询/搜索"), 	//
	FIND(4, "find", "查询/搜索"), 	// 
	
	ADD(5, "add", "新建/增加"), 			// 
	NEW(6, "new", "新建/新增"), 		// 
	JOIN(7, "join", "加入/增加"), 		// 
	SAVE(8, "save", "生成/保存"), 		// 
	
	VIEW(9, "view", "查看/详细"), 		//
	GET(10, "get", "查看/获取"), 		//
	LOAD(11, "load", "加载/获取"), 		// 
	
	DETAIL(12, "detail", "查看详细"), 		// 
	EDIT(13, "edit", "编辑"), 			// 
	MODIFY(14, "modify", "修改"), 		// 
	SETTING(15, "setting", "设置/配置"), 	//  比如标记
	STATUS(16, "status", "设置状态"), 	//  成功与否类的状态
	ROLLBACK(17, "rollback", "回滚"), 	// 
	RESET(18, "reset", "重置"), 			// 
	
	DELETE(19, "delete", "删除"), 		// 
	REMOVE(20, "remove", "移除/移出"), 	// 
	
	UPLOAD(21, "upload", "上传"), 		// 
	DOWNLOAD(22, "download", "下载"), 	// 
	IMPORT(23, "import", "导入"), 		// 
	EXPORT(24, "export", "导出"), 		// 
	
	SUBMIT(25, "submit", "提交"),
	SUBMITUP(26, "submit_up", "呈上(提交)"),
	VERIFY(27, "verify", "审核"),
	FINISHED(28, "finished", "已完成"),
	SUCCESS(29, "success", "成功/完成")
	;
	
	public final int index;
	public final String pname;
	public final String desc;

	private PNode(final int index, final String pname, final String desc) {
		this.index = index;
		this.pname = pname;
		this.desc = desc;
	}

	/**
	 * 由名称找到权限节点
	 * 
	 * @param name
	 * @return 未找到，返回 null
	 */
	public static final PNode get(final String name) {
		PNode[] pnArr = PNode.values();
		for (PNode pn : pnArr) {
			if (pn.pname.equals(name)) {
				return pn;
			}
		}
		return null;
	}


	public int getIndex() {
		return index;
	}

	public String getPname() {
		return pname;
	}

	public String getDesc() {
		return desc;
	}
	
	
	
}


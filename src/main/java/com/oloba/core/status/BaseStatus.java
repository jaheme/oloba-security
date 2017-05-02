package com.oloba.core.status;

/**
 * 1~9999(含) 已占用，用于通用的状态描述，比如db异常
 * @author Tony.lai
 *
 */
public enum BaseStatus implements IStatus {
	

	SUCCESS(200, "全部成功"),
	
	DATA_LACK(300, "找不到需要的数据."),

	FORTHH_ERROR(400, "400类，不支持此种操作"),
	AUTHENTICATE(401, "请求未通过 Privilege 验证。"),

	TIME_OUT(500, "登录已超时, 请重新登录。"),
	FAIL(500, "未处理的异常，请联系管理员."),

	PARAM_INVALID(600, "参数异常"),
	KEY_INVALID(601, "KEY标识无效"),
	
	DB_SUCCESS(800, "DB操作成功"),
	DB_ERROR(801, "DB操作异常")
	;
	
	private final int num;

	private final String msg;
	
	private BaseStatus(int num, String msg) {
		this.num = num;
		this.msg = msg;
	}
	
	public int getCode(){
		return num;
	}
	
	public String getMsg() {
		return msg;
	}

}

package com.oloba.core.status;

/**
 * 状态码和状态信息。要求做到模块状态 码区分 <br>
 * 业务的状态码以万做为起始。<br>
 * 1~9999(含) 已占用，用于通用的状态描述，比如db异常
 * 10000~10099(含) 已占用，用于用户的状态描述
 * 20000~20999(含) 订单、支付状态码
 * 
 * @see BaseStatus
 * @see UStatus
 * @see OrderStatus
 * 
 * @author jahe.lai
 *
 */
public interface IStatus {
	
	public int getCode();
	
	public String getMsg();

}

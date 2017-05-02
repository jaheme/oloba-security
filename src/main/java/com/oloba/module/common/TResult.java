package com.oloba.module.common;

/**
 * 多值返回
 * @author jahe.lai
 *
 * @param <T>
 */
public class TResult<T> {
	
	public String msg = "";
	public T result;
	
	private TResult() {
		
	}
	
	public static <T> TResult<T> valueOf(String msg) {
		TResult<T> sr = new TResult<>();
		sr.msg = msg;
		return sr;
	}
	
	public static <T> TResult<T> valueOf(String msg, T t) {
		TResult<T> sr = new TResult<>();
		sr.msg = msg;
		sr.result = t;
		return sr;
	}

	public static <T> TResult<T> valueT(T t) {
		TResult<T> sr = new TResult<>();
		sr.result = t;
		return sr;
	}
	
}

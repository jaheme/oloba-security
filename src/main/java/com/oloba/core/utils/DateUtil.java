package com.oloba.core.utils;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 日期、时间工具对象
 * @author Tony.lai
 * 
 */
public class DateUtil {
	
	/**
	 * 生成毫秒内的时间串 （长度16） <br>
	 * 可用于生成主键的前缀 (再接随机字符串)
	 * @return
	 */
	public static String dateMills() {
		DateTime d = DateTime.now();
		return d.toString("yyyyMMdd") + d.getMillisOfDay();
	}

	/**
	 * 获取当前时间的日期信息(yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static String currentStr() {
		return DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
	}

	public static String yyyy_MM_dd() {
		return DateTime.now().toString("yyyy-MM-dd");
	}
	public static String yyyyMM() {
		return DateTime.now().toString("yyyyMM");
	}
	
	/**
	 * 获取当前时间的指定格式的日期信息
	 * @param pattern yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String currentStr(String pattern) {
		if (null == pattern) {
			return currentStr();
		}
		return DateTime.now().toString(pattern);
	}
	
	/**
	 * 返回yyyy-MM-dd HH:mm:ss格式的时间信息
	 * @param date JDK的Date对象。
	 * @return
	 */
	public static String dateStr(java.util.Date date) {
		DateTime dt = new DateTime(date);
		return dt.toString("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 返回yyyy-MM-dd格式的日期信息
	 * @param date JDK的Date对象。
	 * @return
	 */
	public static String yyyy_MM_dd(java.util.Date date) {
		DateTime dt = new DateTime(date);
		return dt.toString("yyyy-MM-dd");
	}
	
	/** @return  yyyy-MM-dd HH:mm:ss */
	public static String stringWithDate(java.util.Date date) {
		return dateStr(date, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 返回指定格式的时间信息
	 * @param date JDK的Date对象。
	 * @param pattern	时间格式。
	 * @return
	 */
	public static String dateStr(java.util.Date date, String pattern) {
		DateTime dt = new DateTime(date);
		return dt.toString(pattern);
	}
	
	/**
	 * 在指定日期的基础上增量月份
	 * @param yyyyMM
	 * @param plusNums
	 * @return
	 */
	public static int yyyyMM(String yyyyMM, int plusNums) {
		DateTimeFormatter format = DateTimeFormat .forPattern("yyyyMM");
		DateTime dt = DateTime.parse(yyyyMM, format).plusMonths(plusNums);
		return Integer.parseInt(dt.toString("yyyyMM"));
	}
	

	public static String yyyyMM(int plusNums) {
		DateTime dt = DateTime.now().plusMonths(plusNums);
		return dt.toString("yyyyMM");
	}
	
	public static int yyyyMM(String yyyyMM, String pattern) {
		DateTimeFormatter format = DateTimeFormat .forPattern("yyyyMM");
		DateTime dt = DateTime.parse(yyyyMM, format);
		return Integer.parseInt(dt.toString(pattern));
	}
	
	/**
	 * 在当前日期上偏移多少天的日期对象
	 * @param 
	 * @param plusNums 大于0增  小于0减
	 * @return
	 */
	public static String dateStr(int plusNums, String pattern) {
		DateTime dt = DateTime.now().plusDays(plusNums);
		return dt.toString(pattern);
	}
	
	/**
	 * 在当前月偏移多少月
	 * @param 
	 * @param plusNums 大于0增  小于0减
	 * @return
	 */
	public static int month(int plusNums) {
		DateTime dt = DateTime.now().plusMonths(plusNums);
		return Integer.parseInt(dt.toString("yyyyMM"));
	}
	

	public static String cnMonth(String yyyyMM) {
		DateTimeFormatter format = DateTimeFormat .forPattern("yyyyMM");
		DateTime dt = DateTime.parse(yyyyMM, format);
		return dt.toString("yyyy年M月");
	}

	public static String cnMonth(int yyyyMM) {
		DateTimeFormatter format = DateTimeFormat .forPattern("yyyyMM");
		DateTime dt = DateTime.parse(String.valueOf(yyyyMM), format);
		return dt.toString("M月");
	}
	
	/** 指定月份基数上增减月份，并返回指定的月份格式 */
	public static String cnMonth(String yyyyMM, int plusNums) {
		DateTimeFormatter format = DateTimeFormat .forPattern("yyyyMM");
		DateTime dt = DateTime.parse(yyyyMM, format).plusMonths(plusNums);;
		return dt.toString("yyyy年M月");
	}
	
	public static boolean isToday(Date date) {
		DateTime dt = new DateTime(date);
		if (DateTime.now().toString("yyMd").equals(dt.toString("yyMd"))) {
			return true;
		}
		return false;
	}
}

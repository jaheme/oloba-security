package com.oloba.core.http;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.oloba.core.status.BaseStatus;
import com.oloba.core.status.IStatus;

/**
 * 给请求响应JSON格式的数据(首先实现方式)。<br>
 * 经测试，此方式响应Json，性能较@RestController(@ResponseBody)注解高一倍。
 * @author Tony.lai
 *
 */
public class MJsonView {
	
	private final static String CODE = "code";
	private final static String MSG = "msg";
	private final static String DATA = "data";

	private static MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
	
	/** 只返回处理成功，不带数据体。 */
	public static ModelAndView ok() {
		ModelAndView json = new ModelAndView(jsonView);
		json.addObject(CODE, BaseStatus.SUCCESS.getCode());
		json.addObject(MSG, "");
		return json;
	}

	/** 返回成功，并需要返回的数据体  */
	public static ModelAndView ok(Object obj) {
		return ok().addObject(DATA, obj);
	}
	
	/** 返回成功，并需要返回的数据体  */
	public static ModelAndView ok(String key, Object value) {
		Map<String, Object> map = new HashMap<>(2);
		map.put(key, value);
		return ok().addObject(DATA, map);
	}

	/** 返回成功，并需要返回的数据体  */
	public static ModelAndView okMsg(Object obj, String msg) {
		return ok().addObject(DATA, obj).addObject(MSG, msg);
	}
	/** 只返回失败状态和消息，消息可自己指定。 */
	public static ModelAndView fail(String msg) {
		ModelAndView json = new ModelAndView(jsonView);
		json.addObject(CODE, BaseStatus.FAIL.getCode());
		json.addObject(MSG, msg == null ? BaseStatus.FAIL.getMsg() : msg);
		return json;
	}

	/** 只返回失败状态和消息。 */
	public static ModelAndView fail() {
		return fail(null);
	}
	
	/** 只返回指定的状态码和消息 */
	public static ModelAndView resp(IStatus status) {
		ModelAndView json = new ModelAndView(jsonView);
		json.addObject(CODE, status.getCode());
		json.addObject(MSG, status.getMsg());
		return json;
	}
	
	/** 只返回指定的状态码和消息， 消息可自指定。 */
	public static ModelAndView resp(IStatus status, String msg) {
		ModelAndView json = new ModelAndView(jsonView);
		json.addObject(CODE, status.getCode());
		json.addObject(MSG, msg == null ? status.getMsg() : msg);
		return json;
	}

}

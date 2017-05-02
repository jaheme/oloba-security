package com.oloba.core.http;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.oloba.core.utils.JsonUtil;

public class HttpHelper {
	
	public static void respText(HttpServletResponse resp, String text) throws IOException {
//		resp.setContentType("application/x-www-form-urlencoded");
//		resp.setContentType("text/plain");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		ServletOutputStream outputStream = resp.getOutputStream();
		outputStream.write(text.getBytes());
		outputStream.flush();
	}

	public static void respJson(HttpServletResponse resp, String json) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		ServletOutputStream outputStream = resp.getOutputStream();
		JsonUtil.mapper.writeValue(outputStream, json);
		outputStream.flush();
	}
	

	public static void respXml(HttpServletResponse resp, String xml) throws IOException {
		resp.setContentType("application/xml");
		resp.setCharacterEncoding("UTF-8");
		ServletOutputStream outputStream = resp.getOutputStream();
		outputStream.write(xml.getBytes());
		outputStream.flush();
	}
	
	
	public static String getIp(HttpServletRequest request) {
        String realIp = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(realIp) && !"unKnown".equalsIgnoreCase(realIp)) {
        	return realIp;
        }
        String forwarded = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(forwarded) && !"unKnown".equalsIgnoreCase(forwarded)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = forwarded.indexOf(",");
            if(index != -1){
                return forwarded.substring(0,index);
            }else{
                return forwarded;
            }
        }
        return request.getRemoteAddr();
    }
	
	
	public static boolean isIOS(HttpServletRequest request) {
		String UserAgent = request.getHeader("User-Agent");
		if (UserAgent.startsWith("iOS")) {
			return true;
		}
		return false;
    }
}

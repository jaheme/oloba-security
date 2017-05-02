package com.oloba.module.common;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class CacheService {
	
	////////////////////////////////// verify code /////////////////////////
	private final Cache<String, String> VERIFY_CODE = CacheBuilder.newBuilder()
			.expireAfterWrite(2, TimeUnit.MINUTES)
			.build();
	
	public void putVerifyCode(String sessionId, String code) {
		VERIFY_CODE.put(sessionId, code);
	}
	
	/** 
	 * 获取一次就移除此缓存
	 * @param sessionId
	 * @return
	 */
	public String getVerifyCode(String sessionId) {
		String vcode = VERIFY_CODE.getIfPresent(sessionId);
		VERIFY_CODE.invalidate(sessionId);	// remove
		return vcode;
	}
	////////////////////////////////// verify code end /////////////////////////
	
	

}

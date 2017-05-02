package com.oloba.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.common.base.Charsets;


public class DigestUtil {

	public static byte[] md5(final byte[] input) {
		try {
			final MessageDigest md5Algorithm = MessageDigest.getInstance("MD5");
			return md5Algorithm.digest(input);
		} catch (final NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] md5(final String input) {
		return md5(input.getBytes(Charsets.UTF_8));
	}

	public static String md5Hex(final byte[] input) {
		return StringUtil.toHexString(md5(input));
	}

	public static String md5Hex(final String input) {
		return StringUtil.toHexString(md5(input));
	}
	
	public static String md5Hex16(final String input) {
		String s= StringUtil.toHexString(md5(input));
		return s.substring(8, s.length()-8);
	}
	
	public static String guid(long id, int version) {
		String uuid = java.util.UUID.randomUUID().toString() + id;
		return DigestUtil.md5Hex(uuid) + "V" + version;
	}
}

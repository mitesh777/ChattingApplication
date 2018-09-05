package com.gc.chatapp.utility;

import org.apache.commons.codec.digest.DigestUtils;

public class Encryption {
	public static String encryptData(String s){
		return DigestUtils.sha256Hex(s);
	}
}
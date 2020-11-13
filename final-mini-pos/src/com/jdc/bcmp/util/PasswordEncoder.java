package com.jdc.bcmp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncoder {
	
	public static String encrypt(String raw) throws NoSuchAlgorithmException {
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] digest = md.digest(raw.getBytes());
		
		return Base64.getEncoder().encodeToString(digest);
		
	}

}

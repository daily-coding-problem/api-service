package com.dcp.api_service.utilities;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenUtil {

	public static String generateSecureToken() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] tokenBytes = new byte[24]; // 24 bytes generate 32 characters in base64
		secureRandom.nextBytes(tokenBytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
	}
}

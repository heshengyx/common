package com.myself.common.utils;

import java.util.Random;

public class RandomGeneratorUtil {

	private static String range = "ACBDEFGHJKLMNPQRSTUVWXYZ123456789";
	
	public static synchronized String getRandomString(int length) {
		Random random = new Random();
		StringBuffer result = new StringBuffer();
		
		for (int i = 0; i < length; i++) {
			result.append(range.charAt(random.nextInt(range.length())));
		}
		return result.toString();
	}
}

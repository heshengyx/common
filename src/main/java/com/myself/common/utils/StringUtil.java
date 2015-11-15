package com.myself.common.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class StringUtil {

	public static String getMethodName(String field) {
		return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
	}

	public static String setMethodName(String field) {
		return "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
	}

	/**
	 * DES加密
	 * 
	 */
	public static byte[] encrypt(byte[] data, String key) {
		byte[] result = null;
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			result = cipher.doFinal(data);
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	/**
	 * DES解密
	 */
	public static byte[] decrypt(byte[] data, String key) {
		byte[] result = null;
		try {
			SecureRandom random = new SecureRandom();
			// 创建一个DESKeySpec对象
			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			// 创建一个密匙工厂
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// 将DESKeySpec对象转换成SecretKey对象
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, securekey, random);
			result = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

}

package com.myself.common.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	private static Properties properties = new Properties();
	
	static {
		InputStream is = PropertiesUtil.class.getResourceAsStream("/data.properties");
		try {
			properties.load(is);
		} catch (Exception e) {
			System.err.println("不能读取属性文件，请确保属性文件在你的classpath中");
		}
		
	}
	private PropertiesUtil() {
	}
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public static void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}
}

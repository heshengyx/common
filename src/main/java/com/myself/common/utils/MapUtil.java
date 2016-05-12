package com.myself.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtil {

	public static Map<String, String> getMap(String origin, String symbol) {
		Map<String, String> map = null;
		if (origin != null && !"".equals(origin)) {
			map = new LinkedHashMap<String, String>();
			
			String[] targets = origin.split(symbol);
			for (String target : targets) {
				String[] datas = target.split("[:]");
				map.put(datas[0], datas[1]);
			}
		}
		return map;
	}
}

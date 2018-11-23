package com.qiyuely.remex.dawn.common.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * 当前线程变量池
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ThreadLocalUtils {
	
	/** 当前线程变量池 */
	protected final static ThreadLocal<Map<String, Object>> localMap = new ThreadLocal<>();
	
	/**
	 * 根据key取得当前线程变量池的值
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String key) {
		Map<String, Object> map = localMap.get();
		
		if (map != null) {
			return (T) map.get(key);
		}
		
		return null;
	}
	
	/**
	 * 往当前线程变量池设置值
	 * @param key
	 * @param val
	 */
	public static void set(String key, Object val) {
		Map<String, Object> map = localMap.get();
		if (map == null) {
			map = new HashMap<>();
			localMap.set(map);
		}
		
		map.put(key, val);
	}
}

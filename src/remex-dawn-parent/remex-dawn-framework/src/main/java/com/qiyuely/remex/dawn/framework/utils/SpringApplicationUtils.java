package com.qiyuely.remex.dawn.framework.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

/**
 * SpringApplication工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class SpringApplicationUtils {

	/**
	 * 启动
	 * @param source
	 * @param args
	 * @return
	 */
	public static ApplicationContext run(Class<?> applicationClass, String... args) {
		ApplicationContext context = SpringApplication.run(applicationClass, args);
		ApplicationContextUtils.setContext(context);
		return context;
	}
}

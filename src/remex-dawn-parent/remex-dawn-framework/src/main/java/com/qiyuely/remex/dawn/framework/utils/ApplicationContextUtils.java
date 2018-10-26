package com.qiyuely.remex.dawn.framework.utils;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 上下文的工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ApplicationContextUtils {

	private static ApplicationContext context;

	/**
	 * 设置ApplicationContext
	 * @param context
	 */
	public static void setContext(ApplicationContext context) {
		ApplicationContextUtils.context = context;
	}

	/**
	 * 取得ApplicationContext
	 * @return
	 */
	public static ApplicationContext getContext() {
		return context;
	}

	/**
	 * 取得HttpServletResponse
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();;
		return response;
	}
}

package com.qiyuely.remex.dawn.common.sys;

import com.qiyuely.remex.dawn.common.structure.rsp.BaseResult;

/**
 * 系统配置项，记录了各项目所使用的对象类型等
 * 
 * @author Qiaoxin.Hong
 *
 */
public class SysOptions {
	
	/**
	 * 结果集class
	 */
	@SuppressWarnings("rawtypes")
	private static Class<? extends BaseResult> resultClass = BaseResult.class;
	
	/**
	 * 初始化结果集class
	 * @param resultClass
	 */
	@SuppressWarnings("rawtypes")
	public static void initResultClass(Class<? extends BaseResult> resultClass) {
		SysOptions.resultClass = resultClass;
	}
	
	/**
	 * 取得结果集class
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <R extends BaseResult<?>> Class<R> getResultClass() {
		return (Class<R>) resultClass;
	}
}

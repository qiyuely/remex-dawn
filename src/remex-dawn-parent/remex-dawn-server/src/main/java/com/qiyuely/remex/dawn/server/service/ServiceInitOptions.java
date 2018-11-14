package com.qiyuely.remex.dawn.server.service;

import com.qiyuely.remex.dawn.common.structure.rsp.BaseResult;

/**
 * service配置项
 * 
 * @author Qiaoxin.Hong
 *
 */
public interface ServiceInitOptions {
	
	/**
	 * 取得结果集Class
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public default <R extends BaseResult<?>> Class<R> getResultClass() {
		return (Class<R>) BaseResult.class;
	}
}

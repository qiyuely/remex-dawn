package com.qiyuely.remex.dawn.framework.component.aspectj.config;

import com.qiyuely.remex.aspectj.config.AopConfig;
import com.qiyuely.remex.aspectj.manager.AopManager;
import com.qiyuely.remex.dawn.framework.component.aspectj.handle.AopHandleExceptionHandle;
import com.qiyuely.remex.dawn.framework.component.aspectj.handle.AopHandleLogger;
import com.qiyuely.remex.dawn.framework.component.aspectj.handle.AopHandleReqId;
import com.qiyuely.remex.dawn.framework.component.aspectj.handle.AopHandleValidator;

/**
 * aop配置类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class AopServiceConfig extends AopConfig {
	
	/**
	 * 初始化aop处理器
	 * @param aopManager
	 */
	@Override
	protected void initAopHandle(AopManager aopManager) {
		aopManager.addAopHandle(new AopHandleReqId(), new AopHandleLogger(), new AopHandleValidator()
				, new AopHandleExceptionHandle());
	}
}
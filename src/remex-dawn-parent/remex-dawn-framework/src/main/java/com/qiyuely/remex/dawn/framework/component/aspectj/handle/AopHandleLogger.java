package com.qiyuely.remex.dawn.framework.component.aspectj.handle;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiyuely.remex.aspectj.dto.AopHandleHead;
import com.qiyuely.remex.aspectj.dto.AopHandleResult;
import com.qiyuely.remex.aspectj.dto.AopHandleRunInfo;
import com.qiyuely.remex.aspectj.manager.BaseAopHandle;
import com.qiyuely.remex.core.constant.BConst;
import com.qiyuely.remex.dawn.common.msg.InArgsLogExclude;
import com.qiyuely.remex.dawn.common.structure.req.BaseRequestHead;
import com.qiyuely.remex.utils.JsonUtils;
import com.qiyuely.remex.utils.StringUtils;
import com.qiyuely.remex.utils.ValidateUtils;

/**
 * aop处理器 - 日志打印
 * 
 * @author Qiaoxin.Hong
 *
 */
public class AopHandleLogger extends BaseAopHandle {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/** 入参日志排除标识 */
	public final static String IN_ARGS_JSON_EXCLUDE_FLAG = "exclude";

	/**
	 * 方法执行前置处理
	 */
	@Override
	public AopHandleResult before(AopHandleHead head, AopHandleRunInfo runInfo) {
		//方法id标识
		String mid = fetchReqId(head);
		
		//入参json
		String inArgsJson = BConst.EMPTY;
		if (isWriteInArgsJson()) {
			inArgsJson = String.format("[args : %s]", fetchInArgsJson(head.getPoint(), head.getMethod()));
		}
		
		logger.info("{} [method : {}] begin call {} ", mid, head.getMethodName(), inArgsJson);
		
		return createDefaultResult();
	}
	
	/**
	 * 方法执行最终处理
	 * @return
	 */
	@Override
	public AopHandleResult end(AopHandleHead head, AopHandleRunInfo runInfo, Object result) {
		long callTime = System.currentTimeMillis() - head.getCurDate().getTime();
		//方法id标识
		String mid = fetchReqId(head);
		
		//操作成功
		if (runInfo.isSuccess()) {
			logger.info("{} [method : {}] end call [callTime : {}] ", mid, head.getMethodName(), callTime);
		} else {  //操作失败
			logger.error("{} [method : {}] end call [callTime : {}] ", mid, head.getMethodName()
					, callTime, runInfo.getException());
		}
		
		return createDefaultResult();
	}
	
	/**
	 * 提取入参json
	 * @param signature
	 * @param method
	 * @return
	 */
	protected String fetchInArgsJson(ProceedingJoinPoint point, Method method) {
		String argsJson = IN_ARGS_JSON_EXCLUDE_FLAG;
		
		//如果类上有入参日志排除标识，则不打印入参日志
		Class<?> entityClass = point.getTarget().getClass();
		if (entityClass.isAnnotationPresent(InArgsLogExclude.class)) {
			return argsJson;
		}
		
		//如果方法上有入参日志排除标识，则不打印入参日志
		if (method.isAnnotationPresent(InArgsLogExclude.class)) {
			return argsJson;
		}
		
		Object useArg = null;
		
		Object[] args = point.getArgs();
		if (ValidateUtils.isNotEmpty(args) && args.length == 1) {
			useArg = args[0];
		} else {
			useArg = args;
		}
		
		//入参转json
		argsJson = JsonUtils.toJsonLog(useArg);
		
		return argsJson;
	}
	
	/**
	 * 提取方法id标识
	 * @param signature
	 * @param method
	 * @return
	 */
	protected String fetchReqId(AopHandleHead head) {
		Object[] args = head.getArgs();
		
		String mid = BConst.EMPTY;
		if (isUseReqId() && ValidateUtils.isNotEmpty(args)) {
			for (Object arg : args) {
				//取第一个BaseRequestHead参数据来提取相关的id信息
				if (arg != null && BaseRequestHead.class.isAssignableFrom(arg.getClass())) {
					BaseRequestHead req = (BaseRequestHead) arg;
					mid = String.format("[rid : %s] [mid : %s]", StringUtils.defaultString(req.getGlobalRid())
							, StringUtils.defaultString(req.getGlobalMid())).toString();
					break;
				}
			}
		}
		
		//使用默认的id标识
		if (StringUtils.isBlank(mid)) {
			mid = String.format("[mid : %s]", head.getSid()).toString();
		}
		return mid;
	}
			
	
	/**
	 * 是否打印入参json
	 * @return
	 */
	protected boolean isWriteInArgsJson() {
		return true;
	}
	
	/**
	 * 是否使用请求id标识来作为方法id
	 * @return
	 */
	protected boolean isUseReqId() {
		return true;
	}
}

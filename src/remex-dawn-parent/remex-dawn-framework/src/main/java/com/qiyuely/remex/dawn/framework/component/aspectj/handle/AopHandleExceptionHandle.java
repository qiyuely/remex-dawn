package com.qiyuely.remex.dawn.framework.component.aspectj.handle;

import java.lang.reflect.Method;

import com.qiyuely.remex.aspectj.dto.AopHandleHead;
import com.qiyuely.remex.aspectj.dto.AopHandleResult;
import com.qiyuely.remex.aspectj.dto.AopHandleRunInfo;
import com.qiyuely.remex.aspectj.manager.BaseAopHandle;
import com.qiyuely.remex.dawn.common.exception.RemexDawnException;
import com.qiyuely.remex.dawn.common.msg.IMsgEnum;
import com.qiyuely.remex.dawn.common.msg.MsgEnumContext;
import com.qiyuely.remex.dawn.common.structure.rsp.BaseResult;
import com.qiyuely.remex.utils.BeanUtils;

/**
 * aop处理器 - 异常统一处理
 * 
 * @author Qiaoxin.Hong
 *
 */
public class AopHandleExceptionHandle extends BaseAopHandle {

	/**
	 * 方法执行异常处理
	 * @return
	 */
	@Override
	public AopHandleResult exception(AopHandleHead head, AopHandleRunInfo runInfo) {
		AopHandleResult aopHandleResult = new AopHandleResult();
		
		Method method = head.getMethod();
		Throwable e = runInfo.getException();
		Object result = null;
		
		//如果是规定的结果集
		if (BaseResult.class.isAssignableFrom(method.getReturnType())) {
			BaseResult<?> proceResult = (BaseResult<?>) BeanUtils.newInstance(method.getReturnType());
			
			//如果是规定的异常，则直接以消息枚举填充消息
			if (e instanceof RemexDawnException) {
				RemexDawnException remexDawnException = (RemexDawnException) e;
				proceResult.fillMsgEnum(remexDawnException.getMsgEnum(), remexDawnException.getMsgArgs());
			} else {
				//以异常的消息作为消息枚举的key来查找，如果能找到，则以此消息枚举来填充消息，否则全部以未知错误来填充
				String msgKey = e.getMessage();
				IMsgEnum msgEnum = MsgEnumContext.findRealByCode(msgKey);
				proceResult.fillMsgEnum(msgEnum);
			}
			
			result = proceResult;
		}
		
		//替换原产生的结果集
		aopHandleResult.setReplaceResult(result);
		
		return aopHandleResult;
	}
}

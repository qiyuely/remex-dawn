package com.qiyuely.remex.dawn.framework.component.aspectj.handle;

import com.qiyuely.remex.aspectj.dto.AopHandleHead;
import com.qiyuely.remex.aspectj.dto.AopHandleResult;
import com.qiyuely.remex.aspectj.dto.AopHandleRunInfo;
import com.qiyuely.remex.aspectj.manager.BaseAopHandle;
import com.qiyuely.remex.dawn.common.structure.req.BaseRequestHead;
import com.qiyuely.remex.utils.IdUtils;
import com.qiyuely.remex.utils.StringUtils;
import com.qiyuely.remex.utils.ValidateUtils;

/**
 * aop处理器 - 请求id标识设置
 * 
 * @author Qiaoxin.Hong
 *
 */
public class AopHandleReqId extends BaseAopHandle {
	
	/**
	 * 方法执行前置处理
	 */
	@Override
	public AopHandleResult before(AopHandleHead head, AopHandleRunInfo runInfo) {
		Object[] args = head.getArgs();
		if (ValidateUtils.isNotEmpty(args)) {
			for (Object arg : args) {
				if (arg != null && BaseRequestHead.class.isAssignableFrom(arg.getClass())) {
					BaseRequestHead req = (BaseRequestHead) arg;
					
					//生成id
					String id = IdUtils.createId();
					
					//当前方法的id
					req.setGlobalMid(id);
					
					//请求的id
					if (StringUtils.isBlank(req.getGlobalRid())) {
						req.setGlobalRid(id);
					}
				}
			}
		}
		
		return createDefaultResult();
	}
}

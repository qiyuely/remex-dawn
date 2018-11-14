package com.qiyuely.remex.dawn.framework.component.aspectj.handle;

import java.lang.reflect.Method;

import com.qiyuely.remex.aspectj.dto.AopHandleHead;
import com.qiyuely.remex.aspectj.dto.AopHandleResult;
import com.qiyuely.remex.aspectj.dto.AopHandleRunInfo;
import com.qiyuely.remex.aspectj.manager.BaseAopHandle;
import com.qiyuely.remex.dawn.common.exception.RemexDawnException;
import com.qiyuely.remex.dawn.common.msg.IMsgEnum;
import com.qiyuely.remex.dawn.common.msg.MsgEnumContext;
import com.qiyuely.remex.dawn.common.msg.MsgValidatorEnum;
import com.qiyuely.remex.hvalidator.annotation.Validate;
import com.qiyuely.remex.hvalidator.dto.ValidateResult;
import com.qiyuely.remex.hvalidator.utils.ValidatorUtils;
import com.qiyuely.remex.utils.ValidateUtils;

/**
 * aop处理器 - 数据验证
 * @see com.qiyuely.remex.hvalidator.annotation.Validate 是否验证的标识注解
 * 
 * @author Qiaoxin.Hong
 *
 */
public class AopHandleValidator extends BaseAopHandle {
	
	/**
	 * 方法执行前置处理
	 */
	@Override
	public AopHandleResult before(AopHandleHead head, AopHandleRunInfo runInfo) {
		Method method = head.getMethod();
		Object[] args = head.getArgs();
		
		//启用
		if (!useValidateFlag() || method.isAnnotationPresent(Validate.class)) {
			// 参数验证
			if (ValidateUtils.isNotEmpty(args)) {
				for (Object arg : args) {
					ValidateResult validateResult = ValidatorUtils.validate(arg);
					
					//验证失败
					if (!validateResult.isSuccess()) {
						//查找对应的消息枚举
						IMsgEnum msgEnum = MsgEnumContext.findByCode(validateResult.getMessage());
						//如果未找到消息枚举，则全部以验证失败作为填充
						if (msgEnum == null) {
							msgEnum = getDefaultValidatorErrorMsgEnum();
						}
						throw new RemexDawnException(msgEnum, validateResult.getProperty());
					}
				}
			}
		}
		
		return createDefaultResult();
	}
	
	/**
	 * 是否使用验证的标识注解来启用验证，不启用则默认所有方法都进行验证
	 * @return
	 */
	protected boolean useValidateFlag() {
		return true;
	}
	
	/**
	 * 取得默认的验证失败的消息枚举
	 * @return
	 */
	protected IMsgEnum getDefaultValidatorErrorMsgEnum() {
		return MsgValidatorEnum.VALIDATOR_ERROR;
	}
}

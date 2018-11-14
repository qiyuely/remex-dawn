package com.qiyuely.remex.dawn.common.msg;

import java.util.HashSet;
import java.util.Set;

import com.qiyuely.remex.utils.StringUtils;

/**
 * 消息枚举上下文
 * 
 * @author Qiaoxin.Hong
 *
 */
public class MsgEnumContext {
	
	/** 默认成功的消息枚举 */
	private static IMsgEnum defaultSuccessMsgEnum = MsgBasicEnum.SUCCESS;
	
	/** 默认未知错误的消息枚举 */
	private static IMsgEnum defaultUnknownErrorMsgEnum = MsgBasicEnum.UNKNOWN_ERROR;
	
	/** 消息枚举列表 */
	private static Set<IMsgEnum> msgEnums = new HashSet<>();
	
	/**
	 * 根据消息编号查询消息枚举
	 * @param code
	 * @return
	 */
	public static IMsgEnum findByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		
		for (IMsgEnum msgEnum : msgEnums) {
			if (code.equals(msgEnum.getCode())) {
				return msgEnum;
			}
		}
		
		return null;
	}
	
	/**
	 * 根据消息编号查询消息枚举，如果最终未找到消息枚举，则返回未知错误的消息枚举
	 * @param code
	 * @return
	 */
	public static IMsgEnum findRealByCode(String code) {
		IMsgEnum msgEnum = findByCode(code);
		if (msgEnum == null) {
			msgEnum = getDefaultUnknownErrorMsgEnum();
		}
		return msgEnum;
	} 
	

	/**
	 * 往消息枚举上下文加入消息枚举类
	 * @param msgEnum
	 */
	public static void addMsgEnum(IMsgEnum...msgEnumsItem) {
		for (IMsgEnum msgEnum : msgEnumsItem) {
			msgEnums.add(msgEnum);
		}
	}
	
	/**
	 * 初始化默认的消息枚举信息，包括默认消息枚举、验证框架所用的消息枚举
	 * @see com.qiyuely.remex.dawn.common.msg.MsgBasicEnum 默认消息枚举
	 * @see com.qiyuely.remex.dawn.common.msg.MsgValidatorEnum 验证框架所用的消息枚举
	 */
	public static void initDefaultMsgEnum() {
		addMsgEnumArr(MsgBasicEnum.values(), MsgValidatorEnum.values());
	}
	
	/**
	 * 往消息枚举上下文加入消息枚举类
	 * @param msgEnum
	 */
	public static void addMsgEnumArr(IMsgEnum[]...msgEnumsItemArr) {
		for (IMsgEnum[] msgEnumsItem : msgEnumsItemArr) {
			addMsgEnum(msgEnumsItem);
		}
	}
	
	/**
	 * 取得默认成功的消息枚举
	 * @return
	 */
	public static IMsgEnum getDefaultSuccessMsgEnum() {
		return defaultSuccessMsgEnum;
	}
	
	/**
	 * 设置默认成功的消息枚举
	 * @param defaultSuccessMsgEnum
	 */
	public static void setDefaultSuccessMsgEnum(IMsgEnum defaultSuccessMsgEnum) {
		MsgEnumContext.defaultSuccessMsgEnum = defaultSuccessMsgEnum;
	}
	
	/**
	 * 设置默认未知错误的消息枚举
	 * @param defaultUnknownErrorMsgEnum
	 */
	public static void setDefaultUnknownErrorMsgEnum(IMsgEnum defaultUnknownErrorMsgEnum) {
		MsgEnumContext.defaultUnknownErrorMsgEnum = defaultUnknownErrorMsgEnum;
	}
	
	/**
	 * 取得默认未知错误的消息枚举
	 * @return
	 */
	public static IMsgEnum getDefaultUnknownErrorMsgEnum() {
		return defaultUnknownErrorMsgEnum;
	}
}

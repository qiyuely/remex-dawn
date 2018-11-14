package com.qiyuely.remex.dawn.common.msg;

/**
 * 消息枚举统一规范
 * 
 * @author Qiaoxin.Hong
 *
 */
public interface IMsgEnum {
	
	/**
	 * 取得消息编号
	 * @return
	 */
	public String getCode();
	
	/**
	 * 取得消息信息
	 * @return
	 */
	public String getMsg();
}

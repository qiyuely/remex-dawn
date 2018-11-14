package com.qiyuely.remex.dawn.common.msg;

/**
 * 默认消息枚举
 * 
 * @author Qiaoxin.Hong
 *
 */
public enum MsgBasicEnum implements IMsgEnum {
	
	/** 操作失败 */
	FAILED("FAILED", "操作失败"),
	
	/** 未知错误 */
	UNKNOWN_ERROR("UNKNOWN_ERROR", "未知错误"),
	
	/** 操作成功 */
	SUCCESS("SUCCESS", "操作成功");
	
	/** 消息编号 */
	private String code;
	
	/** 消息信息 */
	private String msg;
	
	private MsgBasicEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

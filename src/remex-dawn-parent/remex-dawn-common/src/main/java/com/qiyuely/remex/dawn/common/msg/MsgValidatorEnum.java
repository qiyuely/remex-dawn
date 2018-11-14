package com.qiyuely.remex.dawn.common.msg;

/**
 * 验证框架所用的消息枚举
 * 
 * @author Qiaoxin.Hong
 *
 */
public enum MsgValidatorEnum implements IMsgEnum {
	
	/** 不能为空 */
	VALIDATOR_REQUIRED(MsgValidatorEnum.VALIDATOR_REQUIRED_CODE, "{}不能为空"),
	
	/** 验证失败 */
	VALIDATOR_ERROR(MsgValidatorEnum.VALIDATOR_ERROR_CODE, "验证失败");

	/** 不能为空code */
	public static final String VALIDATOR_REQUIRED_CODE = "VALIDATOR_REQUIRED";
	
	/** 验证失败code */
	public static final String VALIDATOR_ERROR_CODE = "VALIDATOR_ERROR";
	
	
	/** 消息编号 */
	private String code;
	
	/** 消息信息 */
	private String msg;
	
	
	private MsgValidatorEnum(String code, String msg) {
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

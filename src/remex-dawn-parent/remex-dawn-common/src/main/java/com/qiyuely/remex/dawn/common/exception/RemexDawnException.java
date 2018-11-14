package com.qiyuely.remex.dawn.common.exception;

import com.qiyuely.remex.dawn.common.msg.IMsgEnum;
import com.qiyuely.remex.exception.RemexException;

/**
 * remex dawn运行异常类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RemexDawnException extends RemexException {
	private static final long serialVersionUID = 1L;
	
	/** 消息枚举 */
	protected IMsgEnum msgEnum;
	
	/** 消息占位符所对应的参数列表 */
	protected String[] msgArgs;

	public RemexDawnException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RemexDawnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RemexDawnException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RemexDawnException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RemexDawnException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	public RemexDawnException(IMsgEnum msgEnum, String...msgArgs) {
		this.msgEnum = msgEnum;
		this.msgArgs = msgArgs;
	}

	public IMsgEnum getMsgEnum() {
		return msgEnum;
	}

	public void setMsgEnum(IMsgEnum msgEnum) {
		this.msgEnum = msgEnum;
	}

	public String[] getMsgArgs() {
		return msgArgs;
	}

	public void setMsgArgs(String[] msgArgs) {
		this.msgArgs = msgArgs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

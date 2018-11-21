package com.qiyuely.remex.dawn.common.structure.rsp;

import java.io.Serializable;

import com.qiyuely.remex.dawn.common.exception.RemexDawnException;
import com.qiyuely.remex.dawn.common.msg.IMsgEnum;
import com.qiyuely.remex.dawn.common.msg.MsgEnumContext;
import com.qiyuely.remex.dawn.common.sys.SysOptions;
import com.qiyuely.remex.utils.BeanUtils;
import com.qiyuely.remex.utils.ValidateUtils;

/**
 * 结果集
 * 
 * @author Qiaoxin.Hong
 *
 */
public class BaseResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 结果code */
	protected String code;
	
	/** 结果消息 */
	protected String msg;
	
	/** 消息枚举 */
	protected IMsgEnum msgEnum;
	
	/** 消息占位符所对应的参数列表 */
	protected String[] msgArgs;
	
	/** 结果数据 */
	protected T data;
	
	/**
	 * 创建默认成功的结果集
	 */
	public BaseResult() {
		super();
		this.code = MsgEnumContext.getDefaultSuccessMsgEnum().getCode();
	}
	
	/**
	 * 创建结果集
	 * @param msgEnum
	 */
	public BaseResult(IMsgEnum msgEnum) {
		this.msgEnum = msgEnum;
	}

	/**
	 * 创建默认成功的结果集
	 */
	public BaseResult(T data) {
		this();
		this.data = data;
	}
	
	/**
	 * 创建结果集
	 */
	public BaseResult(T data, IMsgEnum msgEnum, String...msgArgs) {
		this.data = data;
		this.msgEnum = msgEnum;
		this.msgArgs = msgArgs;
		fetchMsgEnum();
	}

	/**
	 * 创建结果集
	 * @param code
	 * @param msg
	 */
	public BaseResult(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 创建结果集
	 * @param code
	 * @param msg
	 */
	public BaseResult(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	/**
	 * 以消息枚举进行消息填充
	 * @param msgEnum
	 * @param msgArgs
	 */
	public void fillMsgEnum(IMsgEnum msgEnum, String...msgArgs) {
		this.msgEnum = msgEnum;
		this.msgArgs = msgArgs;
		fetchMsgEnum();
	}
	
	/**
	 * 从消息枚举里提取数据到结果集中
	 * @param msgEnum
	 */
	protected void fetchMsgEnum() {
		if (msgEnum != null) {
			this.code = msgEnum.getCode();
			this.msg = resolvePlaceholders(msgEnum.getMsg(), msgArgs);
		}
	}
	
	/**
	 * 以异常信息来创建结果集
	 * @param exception
	 * @return
	 */
	public static <R extends BaseResult<?>> R createResult(RemexDawnException exception) {
		return createResult(exception.getMsgEnum(), exception.getMsgArgs());
	}
	
	/**
	 * 创建结果集
	 * @param msgEnum
	 * @param msgArgs
	 * @return
	 */
	public static <R extends BaseResult<?>> R createResult(IMsgEnum msgEnum, String...msgArgs) {
		Class<R> resultClass = SysOptions.getResultClass();
		R result = BeanUtils.newInstance(resultClass);
		result.fillMsgEnum(msgEnum, msgArgs);
		return result;
	}
	
	/**
	 * 是否操作成功
	 * @return
	 */
	public boolean assertSuccess() {
		return MsgEnumContext.getDefaultSuccessMsgEnum().getCode().equals(code);
	}
	
	/**
	 * 是否操作失败
	 * @return
	 */
	public boolean assertFailed() {
		return !assertSuccess();
	}
	
	/**
	 * 替换消息的占位符
	 * @param msg
	 * @param msgArgs
	 * @return
	 */
	public String resolvePlaceholders(String msg, String...msgArgs) {
		if (ValidateUtils.isNotEmpty(msgArgs)) {
			for (String msgArg : msgArgs) {
				msg = msg.replaceFirst("\\{\\}", msgArg);
			}
		}
		
		return msg;
	}
	
	/**
	 * 获取结果数据
	 * @return
	 */
	public T getData() {
		return data;
	}
	
	/**
	 * 带业务性质的获取结果数据，如果操作结果失败，则会抛出异常
	 * @see com.qiyuely.remex.dawn.common.exception.RemexDawnException
	 * @return
	 */
	public T dataService() {
		if (assertFailed()) {
			throw new RemexDawnException(msgEnum, msgArgs);
		}
		return data;
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

	public void setData(T data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void setMsgEnum(IMsgEnum msgEnum) {
		this.msgEnum = msgEnum;
	}
	
	public IMsgEnum getMsgEnum() {
		return msgEnum;
	}
	
	public void setMsgArgs(String[] msgArgs) {
		this.msgArgs = msgArgs;
	}
	
	public String[] getMsgArgs() {
		return msgArgs;
	}
}

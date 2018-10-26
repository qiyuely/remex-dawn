package com.qiyuely.remex.dawn.api.rsp;

import java.io.Serializable;

/**
 * 结果集
 * 
 * @author Qiaoxin.Hong
 *
 */
public class Result<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 操作成功 - 0 */
	public final static String SUCCESS = "0";
	
	/** 操作失败 - 1 */
	public final static String FAILED = "1";

	/** 结果code */
	private String code;
	
	/** 结果消息 */
	private String msg;
	
	/** 结果数据 */
	private T data;
	
	/**
	 * 创建默认成功的结果集
	 */
	public Result() {
		super();
		this.code = SUCCESS;
	}

	/**
	 * 创建默认成功的结果集
	 */
	public Result(T data) {
		this();
		this.data = data;
	}

	/**
	 * 创建结果集
	 * @param code
	 * @param msg
	 */
	public Result(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 创建结果集
	 * @param code
	 * @param msg
	 */
	public Result(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

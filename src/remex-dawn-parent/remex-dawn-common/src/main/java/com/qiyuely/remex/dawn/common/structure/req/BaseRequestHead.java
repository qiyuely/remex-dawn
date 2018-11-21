package com.qiyuely.remex.dawn.common.structure.req;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qiyuely.remex.dawn.common.msg.MsgValidatorEnum;

/**
 * 请求头
 * 
 * @author Qiaoxin.Hong
 *
 */
public class BaseRequestHead implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 验证消息key - 不能为空 */
	protected final static String VALIDATOR_REQUIRED = MsgValidatorEnum.VALIDATOR_REQUIRED_CODE;
	
	/** 验证消息key - 验证失败 */
	protected final static String VALIDATOR_ERROR = MsgValidatorEnum.VALIDATOR_ERROR_CODE;
	

	/**
	 * 请求的id，一个请求只生成一次
	 */
	@JsonIgnore
	private String globalRid;
	
	/**
	 * 当前方法的id，一个方法就重新生成一次
	 */
	@JsonIgnore
	private String globalMid;
	
	public BaseRequestHead() {
		this.globalRid = RequestThreadLocal.get("globalRid");
		this.globalMid = RequestThreadLocal.get("globalMid");
	}

	public String getGlobalRid() {
		return globalRid;
	}

	public void setGlobalRid(String globalRid) {
		this.globalRid = globalRid;
		RequestThreadLocal.set("globalRid", globalRid);
	}

	public void setGlobalMid(String globalMid) {
		this.globalMid = globalMid;
		RequestThreadLocal.set("globalMid", globalMid);
	}
	
	public String getGlobalMid() {
		return globalMid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

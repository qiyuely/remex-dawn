package com.qiyuely.remex.dawn.server.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.qiyuely.remex.dawn.api.rsp.Result;
import com.qiyuely.remex.exception.RemexException;

/**
 * 基础service
 * 
 * @author Qiaoxin.Hong
 *
 * @param <R> 结果集
 */
public class BaseService {
	
//	/**
//	 * 结果集Class
//	 */
//	protected Class<R> resultClass;
//	
//	@SuppressWarnings("unchecked")
//	public BaseService() {
//		//生成各泛型的class
//		Type genType = getClass().getGenericSuperclass();   
//		Type[] params = ((ParameterizedType) genType).getActualTypeArguments(); 
//		resultClass = (Class<R>) params[0];
//	}

//	/**
//	 * 创建默认成功的结果集
//	 * @param data
//	 * @return
//	 */
//	protected R packResult() {
//		return packResult(null);
//	}
	
	protected <T, R extends Result<T>> Class<R> createResult() {
		return (Class<R>) Result.class;
	}
	
	/**
	 * 创建默认成功的结果集
	 * @param data
	 * @return
	 */
	protected <T, R extends Result<T>> R packResult(T data) {
		try {
			R result = createResult();
			
			result.setData(data);
			
			return result;
		} catch (Exception e) {
			throw new RemexException();
		}
	}
}

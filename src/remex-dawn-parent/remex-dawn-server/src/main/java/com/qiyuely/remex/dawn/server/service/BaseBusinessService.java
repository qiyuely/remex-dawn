package com.qiyuely.remex.dawn.server.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiyuely.remex.dawn.common.structure.rsp.BaseResult;
import com.qiyuely.remex.dawn.common.sys.SysOptions;
import com.qiyuely.remex.dawn.common.exception.RemexDawnException;
import com.qiyuely.remex.utils.BeanUtils;
import com.qiyuely.remex.utils.DateUtils;
import com.qiyuely.remex.utils.IdUtils;

/**
 * 基础service
 * 
 * @author Qiaoxin.Hong
 *
 * @param <R> 结果集
 */
public class BaseBusinessService {
	/** 日志记录器 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 创建主键编号
	 * @return
	 */
	protected String createId() {
		return IdUtils.createId();
	}
	
	/**
	 * 取得当前时间
	 * @return
	 */
	protected Timestamp getCurTimestamp() {
		return DateUtils.getCurTimestamp();
	}
	
	/**
	 * 取得当前时间
	 * @return
	 */
	protected Date getCurDate() {
		return DateUtils.getCurDate();
	}
	
	/**
	 * 创建默认成功的结果集
	 * @param data
	 * @return
	 */
	protected <T, R extends BaseResult<T>> R packResult() {
		return packResult(null);
	}
	
	/**
	 * 创建默认成功的结果集
	 * @param data
	 * @return
	 */
	protected <T, R extends BaseResult<T>> R packResult(T data) {
		try {
			Class<R> resultClass = SysOptions.getResultClass();
			R result = resultClass.newInstance();
			
			result.setData(data);
			
			return result;
		} catch (Exception e) {
			throw new RemexDawnException("Service pack result error!", e);
		}
	}
	
	/**
	 * 将类转换为另一个类
	 * @param orig 源对象
	 * @param destClass 目标对象Class
	 * @return
	 */
	protected <T> T convertBean(Object orig, Class<T> destClass) {
		T dest = BeanUtils.convertBean(orig, destClass);
		return dest;
	}
	
	/**
	 * 将类的List转换为另一个类的List
	 * @param origList 源对象List
	 * @param destClass 目标对象Class
	 */
	protected <T> List<T> convertBeanList(List<?> origList, Class<T> destClass) {
		List<T> destList = BeanUtils.convertBeanList(origList, destClass);
		return destList;
	}
	
	/**
	 * 将类的Set转换为另一个类的Set
	 * @param origSet 源对象Set
	 * @param destClass 目标对象Class
	 */
	protected static <T> Set<T> convertBeanSet(Set<?> origSet, Class<T> destClass) {
		Set<T> destSet = BeanUtils.convertBeanSet(origSet, destClass);
		return destSet;
	}
}

package com.qiyuely.remex.dawn.jasperreport.interfaces;

import java.util.List;

import com.qiyuely.remex.dawn.common.structure.rsp.BaseResult;

/**
 * 导出excel，数据查询处理器
 * 
 * @author Qiaoxin.Hong
 *
 * @param <R> 接口查询请求参数实体
 * @param <D> 接口查询结果数据实体
 * @param <T> excel导出所用的数据实体，当不需要转换实体则与<D>相同
 */
public interface IExportExcelHandle<R, D, T> {

	/**
	 * 调用接口查询数据
	 * @param req
	 * @return
	 */
	public BaseResult<List<D>> queryList(R req);
	
	
	/**
	 * 数据转换，复杂结构数据建议转换，避免堆积太多数据
	 * @param dto
	 * @return
	 */
	public default T convertData(D dto) {
		@SuppressWarnings("unchecked")
		T data = (T) dto;
		return data;
	}
	
	/**
	 * 数据转换后的补充处理，可对当前这一个数据进行统一处理
	 * @param list
	 */
	public default void supplementAfterConvert(List<T> list) {
		
	}
}

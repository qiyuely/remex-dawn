package com.qiyuely.remex.dawn.jasperreport.interfaces;

import java.util.List;

/**
 * 导出excel统一处理规范，数据加载完后的后置处理
 * 
 * @author Qiaoxin.Hong
 *
 * @param <T> excel导出所用的数据实体，当不需要转换实体则与<D>相同
 */
public interface IExportExcelLoadOverHandle<T> {
	
	/**
	 * 数据加载完后的后置处理
	 * @param dataList
	 */
	public void loadOverHandle(List<T> dataList);
}

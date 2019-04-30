package com.qiyuely.remex.dawn.jasperreport.mop;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiyuely.remex.dawn.common.exception.RemexDawnException;
import com.qiyuely.remex.dawn.common.structure.req.BaseRequestHead;
import com.qiyuely.remex.dawn.common.structure.rsp.BaseResult;
import com.qiyuely.remex.dawn.framework.utils.ApplicationContextUtils;
import com.qiyuely.remex.dawn.jasperreport.interfaces.IExportExcelHandle;
import com.qiyuely.remex.dawn.jasperreport.interfaces.IExportExcelLoadOverHandle;
import com.qiyuely.remex.exception.RemexException;
import com.qiyuely.remex.jasperreport.data.JRBeanBatchDataSource;
import com.qiyuely.remex.jasperreport.interfaces.IReportBatchReadHandle;
import com.qiyuely.remex.jasperreport.mop.ReportExportMop;
import com.qiyuely.remex.utils.Assert;
import com.qiyuely.remex.utils.BeanUtils;
import com.qiyuely.remex.utils.CollectionUtils;
import com.qiyuely.remex.utils.StringUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * <pre>
 * 导出工具类 
 * 在ReportExportMop基础上，提供出一些扩展方法
 * 用默认约定的结果集和请求参数头进行一些集成
 * 以多线程提高查询性能
 * </pre>
 * 
 * @see com.qiyuely.remex.jasperreport.mop.ReportExportMop
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ReportFastExportMop {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/** 可配置，导出excel查询的线程数 */
	public final static int EXPORT_EXCEL_READ_THREAD_COUNT = 5;
	
	/**
	 * 取得导出的文件名，默认为模板文件流的名称
	 * @param defaultFileName 默认名称
	 * @return
	 */
	protected String getExportFileName(String defaultFileName) {
		return defaultFileName;
	}
	
	/**
	 * 取得模板文件流，可以以模板key，来统一得到模板文件流
	 * @param template 模板key
	 */
	protected InputStream getTemplateInputStream(Object templateKey) {
		
		return null;
	}
	
	/**
	 * 导出excel，分批读取数据，避免数据过大，一次性加载对内存造成过大压力
	 * @param ttemplateKey 模板key
	 * @param parameters 参数
	 * @param readHandle 分批提取数据的数据读取器
	 */
	public void exportExcel(Object templateKey, Map<String, Object> parameters, IReportBatchReadHandle readHandle) {
		InputStream templateInputStream = getTemplateInputStream(templateKey);
		Assert.notNull(templateInputStream, "Jasperreport export template input stream must not be null");
		
		exportExcel(templateInputStream, parameters, readHandle);
	}
	
	/**
	 * 导出excel，分批读取数据，避免数据过大，一次性加载对内存造成过大压力
	 * @param templateInputStream 模板文件流
	 * @param parameters 参数
	 * @param readHandle 分批提取数据的数据读取器
	 */
	public void exportExcel(InputStream templateInputStream, Map<String, Object> parameters, IReportBatchReadHandle readHandle) {
		JRBeanBatchDataSource dataSource = new JRBeanBatchDataSource(readHandle);
		exportExcel(templateInputStream, parameters, dataSource);
	}
	
	/**
	 * 导出excel，一次性写入数据列表的数据
	 * @param templateKey 模板key
	 * @param parameters 参数
	 * @param dataList 数据列表
	 */
	public void exportExcel(Object templateKey, Map<String, Object> parameters, Collection<?> dataList) {
		InputStream templateInputStream = getTemplateInputStream(templateKey);
		Assert.notNull(templateInputStream, "Jasperreport export template input stream must not be null");
		
		exportExcel(templateInputStream, parameters, dataList);
	}
	
	/**
	 * 导出excel，一次性写入数据列表的数据
	 * @param templateInputStream 模板文件流
	 * @param parameters 参数
	 * @param dataList 数据列表
	 */
	public void exportExcel(InputStream templateInputStream, Map<String, Object> parameters, Collection<?> dataList) {
		JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
		exportExcel(templateInputStream, parameters, dataSource);
	}
	
	/**
	 * 导出excel
	 * @param templateKey 模板key
	 * @param parameters 参数
	 * @param dataSource 数据源
	 */
	public void exportExcel(Object templateKey, Map<String, Object> parameters, JRDataSource dataSource) {
		InputStream templateInputStream = getTemplateInputStream(templateKey);
		Assert.notNull(templateInputStream, "Jasperreport export template input stream must not be null");
		
		exportExcel(templateInputStream, parameters, dataSource);
	}
	
	/**
	 * 导出excel
	 * @param templateInputStream 模板文件流
	 * @param parameters 参数
	 * @param dataSource 数据源
	 */
	public void exportExcel(InputStream templateInputStream, Map<String, Object> parameters, JRDataSource dataSource) {
		try {
			JasperReport jasperReport = ReportExportMop.createJasperReport(templateInputStream);
			
			//没有定义导出的文件名规则，则默认使用模板的定义的名称
			String fileName = getExportFileName(jasperReport.getName());
			if (StringUtils.isBlank(fileName)) {
				fileName = jasperReport.getName();
			}
			
			//取得response对象，并封装文件的头信息
			HttpServletResponse response = getResponse(fileName);
			
			ReportExportMop.exportExcel(response.getOutputStream(), jasperReport, parameters, dataSource);
		} catch (Exception e) {
			throw new RemexException("Jasperreport export excel error", e);
		}
	}
	
	/**
	 * 取得response对象
	 * @return
	 */
	public HttpServletResponse getResponse() {
		HttpServletResponse response = ApplicationContextUtils.getResponse();
		return response;
	}
	
	/**
	 * 取得response对象，并封装文件的头信息
	 * @param fileName 导出的文件名
	 * @return
	 */
	public HttpServletResponse getResponse(String fileName) {
		try {
			HttpServletResponse response = getResponse();
			response.setHeader("Access-Control-Expose-Headers", "Content-Disposition,Access-Token,Uid");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
			return response;
		} catch (Exception e) {
			throw new RemexDawnException("report export get response error!", e);
		}
	}
	
	/**
	 * 导出excel
	 * @param templateKey 模板key
	 * @param req 请求参数
	 * @param parameters 参数
	 * @param exportExcelHandle 数据查询处理器
	 * @param pageSize 每页读取数量
	 */
	@SuppressWarnings("unchecked")
	public <R extends BaseRequestHead, D, T> void exportExcel(Object templateKey, R req, Map<String, Object> parameters
			, IExportExcelHandle<R, D, T> exportExcelHandle, IExportExcelLoadOverHandle<T> exportExcelLoadOverHandle
			, int pageSize) {
		Assert.notNull(templateKey, "Report export excel templateKey must not be null");
		Assert.notNull(req, "Report export excel req must not be null");
		Assert.notNull(exportExcelHandle, "Report export excel exportExcelHandle must not be null");
		
		long curTime = System.currentTimeMillis();
		try {
			if (pageSize < 1) {
				pageSize = 10;
			}
			int finalPageSize = pageSize;
			
			//封装分页信息
			req.setPageNum(1);
			req.setPageSize(finalPageSize);
			
			logger.info("[id : {}] export excel [templateKey : {}] [pageSize : {}]", curTime, templateKey, finalPageSize);
			
			//调用接口查询数据
			BaseResult<List<D>> result = exportExcelHandle.queryList(req);
			result.dataService();
			
			int pages = result.getPages();
			long total = result.getTotal();
			
			logger.info("[id : {}] export excel [templateKey : {}] [pageSize : {}] [pages : {}] [total : {}]"
					, curTime, templateKey, finalPageSize, pages, total);
			
			//导出excel所用的所有数据列表
			List<T> allDataList = new ArrayList<>();
			
			if (pages > 0) {
				//临时数据数组
				List<List<T>> dataListArr = new ArrayList<>();
				for (int i = 0; i < pages; i++) {
					dataListArr.add(null);
				}
				
				//线程处理结果列表
				List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>();
				//线程池
				ExecutorService executor = Executors.newFixedThreadPool(EXPORT_EXCEL_READ_THREAD_COUNT);
				//请求参数class
				Class<R> reqClass = (Class<R>) req.getClass();
				
				//循环分页处理数据
				for (int i = 1; i <= pages; i++) {
					if (i == 1) {
						List<D> dtoList = result.dataService();
						List<T> dataList = convertData(dtoList, exportExcelHandle);
						dataListArr.set(i - 1, dataList);
						
						//数据转换后的补充处理
						exportExcelHandle.supplementAfterConvert(dataList);
					} else {
						//多线程查询数据
						final int pageNum = i;
						Future<Boolean> future = executor.submit(() -> {
							logger.info("[id : {}] export excel [templateKey : {}] [pageNum : {}] [pageSize : {}]"
									, curTime, templateKey, pageNum, finalPageSize);
							
							R newReq = BeanUtils.convertBean(req, reqClass);
							newReq.setPageNum(pageNum);
							
							//查询
							List<D> dtoList = exportExcelHandle.queryList(newReq).dataService();
							List<T> dataList = convertData(dtoList, exportExcelHandle);
							dataListArr.set(pageNum - 1, dataList);
							
							//数据转换后的补充处理
							exportExcelHandle.supplementAfterConvert(dataList);
							
							return true;
						});
						futureList.add(future);
					}
				}
				
				//判定请求结果集，如果有一次失败，则结果为失败
				for (Future<Boolean> future : futureList) {
					if (!future.get()) {
						throw new RemexDawnException("Jasperreport export excel read data error");
					}
				}
				
				//汇总导出excel所用的所有数据列表
				for (List<T> dataList : dataListArr) {
					allDataList.addAll(dataList);
				}
			}
			
			logger.info("[id : {}] export excel render start [templateKey : {}] [dataSize : {}]"
					, curTime, templateKey, allDataList.size());
			
			//如果有数据加载完后的后置处理，则处理
			if (exportExcelLoadOverHandle != null) {
				exportExcelLoadOverHandle.loadOverHandle(allDataList);
			}
			
			//导出
			exportExcel(templateKey, parameters, allDataList);
			
		} catch (Exception e) {
			throw new RemexException("Jasperreport export excel error", e);
		}
	}
	
	/**
	 * 转换数据
	 * @param dtoList
	 * @param reportExcelHandle
	 * @return
	 */
	public <D, T> List<T> convertData(List<D> dtoList, IExportExcelHandle<?, D, T> exportExcelHandle) {
		List<T> dataList = new ArrayList<>();
		
		if (CollectionUtils.isNotEmpty(dtoList)) {
			for (D dto : dtoList) {
				if (dto == null) {
					continue;
				}
				//数据转换
				T data = exportExcelHandle.convertData(dto);
				dataList.add(data);
			}
		}
		
		return dataList;
	}
	
	
	
	
	
	
}

package com.qiyuely.remex.dawn.framework.component.db;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 数据源配置类
 * 
 * @author Qiaoxin.Hong
 *
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
	
	/**
	 * 创建数据源
	 * @return
	 */
	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	public DataSource createDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		return dataSource;
	}
}

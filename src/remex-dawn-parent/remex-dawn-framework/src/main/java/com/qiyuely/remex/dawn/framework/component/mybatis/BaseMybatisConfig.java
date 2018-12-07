package com.qiyuely.remex.dawn.framework.component.mybatis;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

/**
 * mybatis配置类
 * 
 * @author Qiaoxin.Hong
 *
 */

@ConditionalOnBean(DataSource.class)
@Configuration
public class BaseMybatisConfig {

	@Autowired
	private DataSource dataSource;
	
	/**
	 * 创建mybatis管理类
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "mybatis")
	public SqlSessionFactoryBean createSqlSessionFactory() {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		//分页插件
		bean.setPlugins(new Interceptor[] {createPageHelper()});
		return bean;
	}
	
	/**
	 * 分页控件
	 * @return
	 */
	@Bean
	public PageHelper createPageHelper() {
		PageHelper pageHelper = new PageHelper();  
        Properties p = new Properties();  
        p.setProperty("offsetAsPageNum", "true");  
        p.setProperty("rowBoundsWithCount", "true");  
        p.setProperty("reasonable", "true");  
        pageHelper.setProperties(p);  
		return pageHelper;
	}
}

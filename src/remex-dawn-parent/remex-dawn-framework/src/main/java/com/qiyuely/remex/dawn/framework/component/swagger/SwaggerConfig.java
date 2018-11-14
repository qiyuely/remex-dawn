package com.qiyuely.remex.dawn.framework.component.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qiyuely.remex.dawn.framework.component.swagger.properties.SwaggerProperties;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置类
 * 
 * @author Qiaoxin.Hong
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	/**
	 * 创建swagger api
	 * @return
	 */
	@Bean
	public SwaggerFactoryBean createRestApi() {
		SwaggerFactoryBean bean = new SwaggerFactoryBean();
		bean.setProperties(createSwaggerProperties());
		return bean;
	}
	
	/**
	 * 创建swagger配置属性
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "swagger")
	public SwaggerProperties createSwaggerProperties() {
		SwaggerProperties properties = new SwaggerProperties();
		return properties;
	}
}

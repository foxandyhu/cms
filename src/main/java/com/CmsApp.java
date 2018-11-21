package com;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * Spring Boot APP启动入口
 * @author andy_hulibo@163.com
 * @date 2018/11/14 13:31
 */
@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages="com.bfly",excludeFilters=@ComponentScan.Filter(type= FilterType.ANNOTATION,value=Controller.class))
public class CmsApp extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CmsApp.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(CmsApp.class, args);
	}
}

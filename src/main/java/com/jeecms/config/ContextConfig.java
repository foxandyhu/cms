package com.jeecms.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.jeecms.core.security.CmsAuthenticationFilter;
import com.jeecms.core.security.CmsLogoutFilter;
import com.jeecms.core.security.CmsUserFilter;
import org.apache.axis.transport.http.AxisServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.IntrospectorCleanupListener;

import java.util.EventListener;

/**
*  @Description: 系统上下文配置
*  @Author: andy_hulibo@163.com
*  @CreateDate: 2018/11/9 10:03
*/
@Configuration
public class ContextConfig{

	@Bean
	public ServletListenerRegistrationBean<EventListener> introspectorCleanupListener(){
		ServletListenerRegistrationBean<EventListener> listener=new ServletListenerRegistrationBean<>(new IntrospectorCleanupListener());
		return listener;
	}

	@Bean
	public FilterRegistrationBean delegatingFilterProxy() {
		FilterRegistrationBean filter=new FilterRegistrationBean();
		filter.setFilter(new DelegatingFilterProxy());
		filter.addUrlPatterns("/*");
		filter.addInitParameter("targetFilterLifecycle","true");
		filter.setName("shiroFilter");
		return filter;
	}
	
	@Bean
	public FilterRegistrationBean openSessionInViewFilter() {
		FilterRegistrationBean filter=new FilterRegistrationBean();
		filter.setFilter(new OpenSessionInViewFilter());
		filter.addUrlPatterns("/*");
		filter.addInitParameter("encoding","UTF-8");
		filter.setName("openSessionInViewFilter");
		return filter;
	}

	@Bean
	public ServletRegistrationBean axisServlet(){
		AxisServlet axisServlet=new AxisServlet();
		ServletRegistrationBean registrationBean= new ServletRegistrationBean(axisServlet);
		registrationBean.setLoadOnStartup(4);
		registrationBean.addUrlMappings("/services/*");
		registrationBean.setName("axisServlet");
		return registrationBean;
	}

	@Bean
	public ServletRegistrationBean druidStatViewServlet(){
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
		servletRegistrationBean.addInitParameter("allow","127.0.0.1");
		servletRegistrationBean.addInitParameter("deny","192.168.1.73");
		servletRegistrationBean.addInitParameter("loginUsername","admin");
		servletRegistrationBean.addInitParameter("loginPassword","123456");
		servletRegistrationBean.addInitParameter("resetEnable","false");
		servletRegistrationBean.setLoadOnStartup(5);
		return servletRegistrationBean;
	}

	@Bean
	public ServletRegistrationBean cmsAdminApiServlet(){
		AnnotationConfigWebApplicationContext context=new AnnotationConfigWebApplicationContext();

		context.scan("com.context.admin");

		DispatcherServlet bbsAdminApiServlet=new DispatcherServlet(context);
		ServletRegistrationBean registrationBean= new ServletRegistrationBean(bbsAdminApiServlet);
		registrationBean.setLoadOnStartup(6);
		registrationBean.addInitParameter("dispatchOptionsRequest","true");
		registrationBean.addUrlMappings("/api/admin/*");
		registrationBean.setName("cmsAdminApiServlet");
		return registrationBean;
	}
	
	@Bean
	public ServletRegistrationBean cmsMemberApiServlet(){
		AnnotationConfigWebApplicationContext context=new AnnotationConfigWebApplicationContext();
		context.scan("com.context.member");
		
		DispatcherServlet bbsMemberApiServlet=new DispatcherServlet(context);
		
		ServletRegistrationBean registrationBean= new ServletRegistrationBean(bbsMemberApiServlet);
		registrationBean.setLoadOnStartup(7);
		registrationBean.addUrlMappings("/api/member/*");
		registrationBean.setName("cmsMemberApiServlet");
		return registrationBean;
	}

	@Bean
	public ServletRegistrationBean dispatcherServlet(){
		AnnotationConfigWebApplicationContext context=new AnnotationConfigWebApplicationContext();
		context.scan("com.context.front");

		DispatcherServlet bbsFrontServlet=new DispatcherServlet(context);
		context.setServletContext(bbsFrontServlet.getServletContext());

		ServletRegistrationBean registrationBean= new ServletRegistrationBean(bbsFrontServlet);
		registrationBean.setLoadOnStartup(8);
		registrationBean.addUrlMappings("*.jhtml","*.jspx","*.jsp","*.htm","/api/front/*");
		registrationBean.setName("dispatcherServlet");
		return registrationBean;
	}
	
	@Bean
	public FilterRegistrationBean bbsUserFilterRegistration(CmsUserFilter userFilter) {
		FilterRegistrationBean filter=new FilterRegistrationBean();
		filter.setFilter(userFilter);
		//不注册到FilterChain中
		filter.setEnabled(false);
		return filter;
	}
	
	@Bean
	public FilterRegistrationBean authcFilterRegistration(CmsAuthenticationFilter authcFilter) {
		FilterRegistrationBean filter=new FilterRegistrationBean(authcFilter);
		filter.setFilter(authcFilter);
		//不注册到FilterChain中
		filter.setEnabled(false);
		return filter;
	}

	@Bean
	public FilterRegistrationBean logoutFilterRegistration(CmsLogoutFilter logoutFilter) {
		FilterRegistrationBean filter=new FilterRegistrationBean(logoutFilter);
		filter.setFilter(logoutFilter);
		//不注册到FilterChain中
		filter.setEnabled(false);
		return filter;
	}
}

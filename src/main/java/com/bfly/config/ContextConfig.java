package com.bfly.config;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.IntrospectorCleanupListener;

import java.util.EventListener;

/**
 * Application系统上下文配置
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 13:38
 */
@Configuration
public class ContextConfig {

    @Bean
    public ServletListenerRegistrationBean<EventListener> introSpecTorCleanupListener() {
        return new ServletListenerRegistrationBean<>(new IntrospectorCleanupListener());
    }

    @Bean
    public FilterRegistrationBean openSessionInViewFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new OpenSessionInViewFilter());
        filter.addUrlPatterns("/*");
        filter.addInitParameter("encoding", "UTF-8");
        filter.setName("openSessionInViewFilter");
        return filter;
    }

    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        servletRegistrationBean.addInitParameter("deny", "192.168.1.73");
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        servletRegistrationBean.setLoadOnStartup(6);
        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.scan("com.context");

        DispatcherServlet bbsFrontServlet = new DispatcherServlet(context);
        context.setServletContext(bbsFrontServlet.getServletContext());

        ServletRegistrationBean registrationBean = new ServletRegistrationBean(bbsFrontServlet);
        registrationBean.setLoadOnStartup(7);
        registrationBean.addUrlMappings("/");
        registrationBean.setName("dispatcherServlet");
        return registrationBean;
    }
}

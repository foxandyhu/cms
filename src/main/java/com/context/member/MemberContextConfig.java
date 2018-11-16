package com.context.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages= {"com.jeecms.cms.api.member"},includeFilters=@Filter(type=FilterType.ANNOTATION,value=Controller.class),useDefaultFilters=false)
public class MemberContextConfig extends WebMvcConfigurerAdapter{

	@Autowired
	private MemberApiInterceptor interceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor).addPathPatterns("/**");
	}

	@Bean
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver resolver=new CookieLocaleResolver();
		resolver.setCookieName("clientlanguage");
		resolver.setCookieMaxAge(-1);
		return resolver;
	}
}

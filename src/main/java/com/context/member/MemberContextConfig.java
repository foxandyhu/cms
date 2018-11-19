package com.context.member;

import com.jeecms.common.web.springmvc.BindingInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.jeecms.cms.api.member"}, includeFilters = @Filter(type = FilterType.ANNOTATION, value = Controller.class), useDefaultFilters = false)
public class MemberContextConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private MemberApiInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }

    @Bean
    public BindingInitializer bindingInitializer() {
        return new BindingInitializer();
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(BindingInitializer bindingInitializer) {
        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        adapter.setWebBindingInitializer(bindingInitializer);
        return adapter;
    }

    /**
     * 当前端表单提交类型是enctype="multipart/form-data"时 HttpServletRequest
     * 获取不到数据，启用该bean即可
     *
     * @author: andy_hulibo@163.com
     * @date: 2018/11/13 16:03
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

}

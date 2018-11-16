package com.context.member;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jeecms.common.util.PropertyUtils;


/**
 * @author tom
 * @date 2017年11月17日
 */
@Configuration
@ConfigurationProperties(prefix = "Cross.domain")
public class MemberCorsConfigurerAdapter extends WebMvcConfigurerAdapter {

    //跨域请求地址，默认*允许所有来访域名访问，可以限定特定来访域名(去除* 调整成类似Cross.domain.*)最多五个(可修改源码调整)
    private String baseUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String pathPattern = "/**";
        CorsRegistration corsRe = (CorsRegistration) registry.addMapping(pathPattern);
        corsRe.allowedHeaders("*").allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS");
        List<String> urls = new ArrayList<>();
        urls.add(baseUrl);
        int urlSize = urls.size();
        //*开放访问
        if (urlSize <= 0) {
            corsRe.allowedOrigins("*");
        } else if (urlSize <= 1) {
            corsRe.allowedOrigins(urls.get(0));
        } else if (urlSize <= 2) {
            corsRe.allowedOrigins(urls.get(0), urls.get(1));
        } else if (urlSize <= 3) {
            corsRe.allowedOrigins(urls.get(0), urls.get(1), urls.get(2));
        } else if (urlSize <= 4) {
            corsRe.allowedOrigins(urls.get(0), urls.get(1), urls.get(2), urls.get(3));
        } else if (urlSize <= 5) {
            corsRe.allowedOrigins(urls.get(0), urls.get(1), urls.get(2), urls.get(3), urls.get(4));
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(memberApiInterceptor);
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(handlerExceptionResolver);
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }

    @Autowired
    private MemberApiInterceptor memberApiInterceptor;
    @Autowired
    private HandlerApiExceptionResolver handlerExceptionResolver;

}


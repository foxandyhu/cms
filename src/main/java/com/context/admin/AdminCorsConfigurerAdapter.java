package com.context.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author tom
 * @date 2017年11月17日
 */
@Configuration
@ConfigurationProperties(prefix = "Cross.domain")
public class AdminCorsConfigurerAdapter extends WebMvcConfigurerAdapter {

    private String baseUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String pathPattern = "/**";
        CorsRegistration corsRe = (CorsRegistration) registry.addMapping(pathPattern);
        corsRe.allowedHeaders("*").allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS");
        List<String> urls = new ArrayList<>();
        urls.add(baseUrl);
        int urlSize = urls.size();
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
        registry.addInterceptor(adminApiInterceptor);
        super.addInterceptors(registry);
    }


    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(handlerApiExceptionResolver);
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }

    @Autowired
    private AdminApiInterceptor adminApiInterceptor;
    @Autowired
    private HandlerApiExceptionResolver handlerApiExceptionResolver;
}


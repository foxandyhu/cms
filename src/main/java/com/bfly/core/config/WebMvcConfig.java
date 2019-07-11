package com.bfly.core.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.bfly.core.interceptor.ManageInterceptor;
import com.bfly.core.interceptor.MemberApiInterceptor;
import com.bfly.core.interceptor.WebContextInterceptor;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateDirectiveModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletContext;
import java.util.*;

/**
 * Servlet系统上下文配置
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 13:39
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    private WebContextInterceptor frontContextInterceptor;
    private ManageInterceptor manageInterceptor;
    private MemberApiInterceptor memberInterceptor;

    @Value("#{'${spring.resource.suffix}'.split(',')}")
    private List<String> suffixs;

    @Value("#{'${spring.cors.origins}'.split(',')}")
    private List<String> origins;

    @Value("#{'${spring.cors.headers}'.split(',')}")
    private List<String> headers;

    @Value("${spring.cors.maxage}")
    private long maxAge;

    @Autowired
    public WebMvcConfig(WebContextInterceptor frontContextInterceptor, ManageInterceptor manageInterceptor, MemberApiInterceptor memberInterceptor) {
        this.frontContextInterceptor = frontContextInterceptor;
        this.manageInterceptor = manageInterceptor;
        this.memberInterceptor = memberInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(manageInterceptor).addPathPatterns("/manage/**");
        registry.addInterceptor(memberInterceptor).addPathPatterns("/member/**");
        registry.addInterceptor(frontContextInterceptor).addPathPatterns("/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setSupportedMediaTypes(new ArrayList<MediaType>() {{
            add(MediaType.APPLICATION_JSON_UTF8);
        }});
        converters.add(fastJsonHttpMessageConverter);
    }

    @Bean
    public CorsConfiguration corsConfig() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(this.headers);
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        config.setAllowedOrigins(this.origins);
        config.setMaxAge(this.maxAge);
        return config;
    }

    @Bean
    public CorsFilter corsFilter(CorsConfiguration corsConfig) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsFilter(source);
    }

    /**
     * @author andy_hulibo@163.com
     * @date 2018/11/28 13:40
     * 手动开启Servlet容器默认的Servlet对静态资源的处理
     */
    @Bean
    public HandlerMapping defaultServletHandlerMapping(ServletContext ctx) {
        DefaultServletHttpRequestHandler handler = new DefaultServletHttpRequestHandler();
        handler.setServletContext(ctx);
        Map<String, HttpRequestHandler> urlMap = new HashMap<>(15);
        for (String suffix : suffixs) {
            urlMap.put("/**/" + suffix, handler);
        }
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setOrder(Integer.MIN_VALUE);
        handlerMapping.setUrlMap(urlMap);
        return handlerMapping;
    }

    /**
     * 当前端表单提交类型是enctype="multipart/form-data"时 HttpServletRequest
     * 获取不到数据，启用该bean即可
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/28 13:40
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) throws Exception {
        ApplicationContext context = event.getApplicationContext();
        Map<String, TemplateDirectiveModel> map = context.getBeansOfType(TemplateDirectiveModel.class);
        FreeMarkerConfigurer config = context.getBean(FreeMarkerConfigurer.class);
        config.getConfiguration().setAllSharedVariables(new SimpleHash(map, config.getConfiguration().getObjectWrapper()));
    }
}

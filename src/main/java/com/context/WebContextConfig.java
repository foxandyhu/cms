package com.context;

import com.bfly.common.web.springmvc.BindingInitializer;
import com.bfly.core.interceptor.*;
import freemarker.template.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Servlet系统上下文配置
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 13:39
 */
@Configuration
@ComponentScan(basePackages = {"com.bfly"}, includeFilters = @Filter(type = FilterType.ANNOTATION, value = Controller.class), useDefaultFilters = false)
@EnableWebMvc
public class WebContextConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private WebContextInterceptor frontContextInterceptor;
    @Autowired
    private LocaleInterceptor frontLocaleInterceptor;
    @Autowired
    private AdminApiInterceptor adminInterceptor;
    @Autowired
    private MemberApiInterceptor memberInterceptor;
    @Autowired
    private FireWallInterceptor fireWallInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        adminInterceptor.setExcludeUrls(new String[]{"/user/getPerms/**"});
        registry.addInterceptor(fireWallInterceptor).addPathPatterns("/api/admin/**");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/api/admin/**");

        registry.addInterceptor(memberInterceptor).addPathPatterns("/api/member/**");

        registry.addInterceptor(frontContextInterceptor).addPathPatterns("/**");
        registry.addInterceptor(frontLocaleInterceptor).addPathPatterns("/**");
    }

    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setCookieName("clientlanguage");
        resolver.setCookieMaxAge(-1);
        resolver.setCookieSecure(true);
        resolver.setCookieHttpOnly(true);
        return resolver;
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();

        Properties p = new Properties();
        p.setProperty("org.springframework.web.bind.MissingServletRequestParameterException", "/WEB-INF/error/requiredParameter.html");
        p.setProperty("org.springframework.beans.TypeMismatchException", "/WEB-INF/error/mismatchParameter.html");
        p.setProperty("org.springframework.web.bind.ServletRequestBindingException", "/WEB-INF/error/bindException.html");
        p.setProperty("com.bfly.core.exception.SiteNotFoundException", "/WEB-INF/error/siteNotFoundException.html");
        resolver.setExceptionMappings(p);
        return resolver;
    }

    @Value("#{'${spring.resource.suffix}'.split(',')}")
    private List<String> suffixs;

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
     * @author andy_hulibo@163.com
     * @date 2018/11/28 13:40
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Value("#{'${spring.view.directive}'.split(',')}")
    private List<String> directives;

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) throws Exception {
        ApplicationContext context = event.getApplicationContext();
        FreeMarkerConfigurer config = context.getBean(FreeMarkerConfigurer.class);
        Map<String, Object> map = new HashMap<String, Object>(15) {
            private static final long serialVersionUID = 723383891389861471L;

            {
                for (String key : directives) {
                    Object obj = context.getBean(key);
                    put(key, obj);
                }
            }
        };
        config.getConfiguration().setAllSharedVariables(new SimpleHash(map, config.getConfiguration().getObjectWrapper()));
    }
}

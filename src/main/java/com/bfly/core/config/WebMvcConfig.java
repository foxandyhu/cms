package com.bfly.core.config;

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
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 初始化数据绑定
     * 可能request提交过来的date为空字符会自动转换或去掉
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/6 15:36
     */
    @Bean
    public WebBindingInitializer bindingInitializer() {
        return (binder) -> binder.registerCustomEditor(Date.class, new DateTypeEditor());
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(WebBindingInitializer bindingInitializer) {
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

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) throws Exception {
        ApplicationContext context = event.getApplicationContext();
        Map<String, TemplateDirectiveModel> map = context.getBeansOfType(TemplateDirectiveModel.class);
        FreeMarkerConfigurer config = context.getBean(FreeMarkerConfigurer.class);
        config.getConfiguration().setAllSharedVariables(new SimpleHash(map, config.getConfiguration().getObjectWrapper()));
    }
}

package com.bfly.core.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.bfly.cms.system.entity.SiteConfig;
import com.bfly.cms.system.service.ISiteConfigService;
import com.bfly.common.FileUtil;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.interceptor.ManageInterceptor;
import com.bfly.core.interceptor.MemberApiInterceptor;
import com.bfly.core.interceptor.WebInterceptor;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateMethodModelEx;
import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
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
import java.util.ArrayList;
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

    private WebInterceptor webInterceptor;
    private ManageInterceptor manageInterceptor;
    private MemberApiInterceptor memberInterceptor;
    private ServletContext servletContext;

    @Value("#{'${spring.resource.suffix}'.split(',')}")
    private List<String> suffixs;

    @Value("#{'${spring.cors.origins}'.split(',')}")
    private List<String> origins;

    @Value("#{'${spring.cors.headers}'.split(',')}")
    private List<String> headers;

    @Value("${spring.cors.maxage}")
    private long maxAge;

    @Autowired
    public WebMvcConfig(WebInterceptor webInterceptor, ManageInterceptor manageInterceptor, MemberApiInterceptor memberInterceptor) {
        this.webInterceptor = webInterceptor;
        this.manageInterceptor = manageInterceptor;
        this.memberInterceptor = memberInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(manageInterceptor).addPathPatterns("/manage/**");
        registry.addInterceptor(memberInterceptor).addPathPatterns("/member/**");
        registry.addInterceptor(webInterceptor).addPathPatterns("/**").excludePathPatterns("/manage/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setSupportedMediaTypes(new ArrayList<MediaType>() {{
            add(MediaType.APPLICATION_JSON_UTF8);
        }});
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charsets.UTF_8);
        converters.add(fastJsonHttpMessageConverter);
        converters.add(stringHttpMessageConverter);
    }

    /**
     * 跨域访问配置
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/16 21:20
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.setAllowedHeaders(this.headers);
        config.setAllowedOrigins(this.origins);
        config.setMaxAge(this.maxAge);
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/manage/**", config);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
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

    /**
     * 应用程序启动把freemarker 自定义指令放入共享变量中
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/17 19:05
     */
    @EventListener
    public void handleContextRefresh(ApplicationStartedEvent event) throws Exception {
        ApplicationContext context = event.getApplicationContext();
        Map<String, TemplateDirectiveModel> directiveMap = context.getBeansOfType(TemplateDirectiveModel.class);
        Map<String, TemplateMethodModelEx> methodMap = context.getBeansOfType(TemplateMethodModelEx.class);

        FreeMarkerConfigurer config = context.getBean(FreeMarkerConfigurer.class);

        config.getConfiguration().setAllSharedVariables(new SimpleHash(directiveMap, config.getConfiguration().getObjectWrapper()));
        config.getConfiguration().setAllSharedVariables(new SimpleHash(methodMap, config.getConfiguration().getObjectWrapper()));
    }

    @Autowired
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * 应用程序启动初始化全局共享数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/18 15:41
     */
    @EventListener
    public void initContextData(ApplicationStartedEvent event) {
        ApplicationContext ctx = event.getApplicationContext();
        ISiteConfigService siteConfigService = ctx.getBean(ISiteConfigService.class);
        SiteConfig site = siteConfigService.getSite();

        ContextUtil.setSiteConfig(site, servletContext);
        servletContext.setAttribute("resServer", ResourceConfig.getServer());

        initDirs();
    }

    /**
     * 初始化系统目录
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/25 14:53
     */
    private void initDirs() {
        FileUtil.mkdir(ResourceConfig.getRootDir());
        FileUtil.mkdir(ResourceConfig.getTempDir());
        FileUtil.mkdir(ResourceConfig.getArticleIndexDir());
        FileUtil.mkdir(ResourceConfig.getContentDir());
        FileUtil.mkdir(ResourceConfig.getFaceDir());
        FileUtil.mkdir(ResourceConfig.getAdvertisingDir());
        FileUtil.mkdir(ResourceConfig.getAttachmentDir());
        FileUtil.mkdir(ResourceConfig.getDocDir());
        FileUtil.mkdir(ResourceConfig.getMediaDir());
        FileUtil.mkdir(ResourceConfig.getScoreDir());
        FileUtil.mkdir(ResourceConfig.getFriendLinkDir());
        FileUtil.mkdir(ResourceConfig.getVoteTopicDir());
        FileUtil.mkdir(ResourceConfig.getSysImgDir());
    }
}

package com.jeecms.config;

import com.jeecms.common.web.springmvc.RealPathResolver;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Component("myServletRealPathResolver")
public class ServletContextRealPathResolver1 implements RealPathResolver, ServletContextAware {

    public ServletContextRealPathResolver1() {
        System.out.println("Initing servlet aware");
    }

    /**
     * I never get called because I get initialized during a BeanPostProcessor initialization
     *
     * @param servletContext
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.context=servletContext;
    }

    @Override
    public String get(String path) {
        String realpath = context.getRealPath(path);
        //tomcat8.0获取不到真实路径，通过/获取路径
        if (StringUtils.isBlank(realpath)) {
            realpath = context.getRealPath("/") + path;
        }
        return realpath;
    }

    private ServletContext context;
}
package com.bfly.core.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 执行时间过滤器
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/29 10:28
 */
@WebFilter(filterName = "processTime", urlPatterns = {"*.htm"})
public class ProcessTimeFilter implements Filter {
    protected final Logger log = LoggerFactory
            .getLogger(ProcessTimeFilter.class);
    /**
     * 请求执行开始时间
     */
    public static final String START_TIME = "_start_time";

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        long time = System.currentTimeMillis();
        request.setAttribute(START_TIME, time);
        chain.doFilter(request, response);
        time = System.currentTimeMillis() - time;
        log.debug("process in {} ms: {}", time, request.getRequestURI());
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}

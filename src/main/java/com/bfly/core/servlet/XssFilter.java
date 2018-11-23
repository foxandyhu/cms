package com.bfly.core.servlet;

/**
 * @author Tom
 */
import java.io.IOException;

import javax.servlet.Filter;

import javax.servlet.FilterChain;

import javax.servlet.FilterConfig;

import javax.servlet.ServletException;

import javax.servlet.ServletRequest;

import javax.servlet.ServletResponse;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;

import com.bfly.common.web.XssHttpServletRequestWrapper;
import org.apache.commons.lang.StringUtils;

import com.bfly.core.web.util.URLHelper;

@WebFilter(filterName = "XssFilter",urlPatterns = {"*.html"},initParams = {@WebInitParam(name = "excludeUrls",value = "/member@/flow_statistic@/api")})
public class XssFilter implements Filter {
	private String excludeUrls;
	FilterConfig filterConfig = null;
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
		this.excludeUrls=filterConfig.getInitParameter("excludeUrls");
		this.filterConfig = filterConfig;
	}

	@Override
    public void destroy() {
		this.filterConfig = null;
	}

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(isExcludeUrl(request)){
			chain.doFilter(request, response);
		}else{
			HttpServletRequest req=(HttpServletRequest)request;
			chain.doFilter(new XssHttpServletRequestWrapper(req), response);
		}
	}
	
	private boolean isExcludeUrl(ServletRequest request){
		boolean exclude=false;
		if(StringUtils.isNotBlank(excludeUrls)){
			 String[]excludeUrl=excludeUrls.split("@");
			 if(excludeUrl!=null&&excludeUrl.length>0){
				 for(String url:excludeUrl){
					 if(URLHelper.getURI((HttpServletRequest)request).startsWith(url)){
						 exclude=true;
					 }
				 }
			 }
		}
		return exclude;
	}

}
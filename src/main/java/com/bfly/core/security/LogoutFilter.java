//package com.bfly.core.security;
//
//import com.bfly.common.web.CookieUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.web.filter.authc.LogoutFilter;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
///**
// * 用户退出 Shiro Filter
// *
// * @author andy_hulibo@163.com
// * @date 2018/12/1 21:33
// */
//public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
//
//    private static final String RETURN_URL = "returnUrl";
//
//    @Override
//    protected String getRedirectUrl(ServletRequest req, ServletResponse resp, Subject subject) {
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) resp;
//        String redirectUrl = request.getParameter(RETURN_URL);
//        String domain = request.getServerName();
//        String dot = ".";
//        if (domain.contains(dot)) {
//            domain = domain.substring(domain.indexOf(dot) + 1);
//        }
//        CookieUtils.addCookie(response, "JSESSIONID", null, 0, domain, "/");
//        CookieUtils.addCookie(response, "sso_logout", "true", null, domain, "/");
//        if (StringUtils.isBlank(redirectUrl)) {
//            redirectUrl = getRedirectUrl();
//        }
//        return redirectUrl;
//    }
//}

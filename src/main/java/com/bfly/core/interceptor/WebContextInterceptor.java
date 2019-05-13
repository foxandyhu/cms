package com.bfly.core.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Web访问请求拦截器
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/6 15:33
 */
@Component
public class WebContextInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(WebContextInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
//        Subject subject = SecurityUtils.getSubject();
//        if (subject.isAuthenticated() || subject.isRemembered()) {
//            String username = (String) subject.getPrincipal();
////            Member user = null;//cmsUserMng.findByUsername(username);
////            request.setAttribute(Constants.USER_LOGIN_KEY, user);
//        }
//        checkEquipment(request);
        return true;
    }

    /**
     * 检查访问方式
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/2 12:20
     */
    private void checkEquipment(HttpServletRequest request) {
//        String ua = (String) request.getSession().getAttribute(Constants.USER_AGENT_KEY);
//        if (null == ua) {
//            try {
//                String userAgent = request.getHeader("USER-AGENT").toLowerCase();
//                ua = CheckMobile.check(userAgent) ? "mobile" : "pc";
//                request.setAttribute(Constants.USER_AGENT_KEY, ua);
//                request.getSession().setAttribute(Constants.USER_AGENT_KEY, ua);
//            } catch (Exception e) {
//                logger.error("检查请求浏览器类型出错", e);
//            }
//        }
    }
}
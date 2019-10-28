package com.bfly.core.interceptor;

import com.bfly.cms.member.entity.Member;
import com.bfly.cms.system.entity.SiteConfig;
import com.bfly.common.reflect.ReflectUtils;
import com.bfly.core.Constants;
import com.bfly.core.context.*;
import com.bfly.core.exception.UnAuthException;
import com.bfly.core.security.Login;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * Web访问请求拦截器
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/6 15:33
 */
@Component
public class WebInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (siteClosed(request)) {
            if (request.getRequestURI().contains(Constants.SITE_CLOSE_URI)) {
                return true;
            }
            response.sendRedirect(Constants.SITE_CLOSE_URI);
            return false;
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Member member = ContextUtil.getLoginMember();
            if (needLogin(method)) {
                if (member == null) {
                    throw new UnAuthException("未登录!");
                }
            }
            MemberThreadLocal.set(member);
        }
        IpThreadLocal.set(ContextUtil.getClientIp(request));
        ServletRequestThreadLocal.set(request);
        checkEquipment(request);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        IpThreadLocal.clear();
        ServletRequestThreadLocal.clear();
        PagerThreadLocal.clear();
        MemberThreadLocal.clear();
    }

    /**
     * 判断资源是否需要登录
     *
     * @param mth
     * @return
     * @author 胡礼波
     * 2012-4-25 下午06:32:47
     */
    private boolean needLogin(Method mth) {
        Login login = ReflectUtils.getActionAnnotationValue(mth, Login.class);
        return login == null ? false : login.required();
    }

    /**
     * 检查访问方式
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/2 12:20
     */
    private void checkEquipment(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        String from = "pc";
        if (operatingSystem != null) {
            DeviceType deviceType = operatingSystem.getDeviceType();
            if (deviceType != DeviceType.COMPUTER) {
                from = "mobile";
            }
        }
        request.setAttribute(Constants.USER_AGENT_KEY, from);
    }

    /**
     * 判断站点是否关闭
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/20 15:30
     */
    private boolean siteClosed(HttpServletRequest request) {
        SiteConfig config = ContextUtil.getSiteConfig(request.getServletContext());
        return !config.isOpenSite();
    }
}
package com.bfly.core.interceptor;

import com.bfly.cms.user.entity.User;
import com.bfly.common.reflect.ReflectUtils;
import com.bfly.core.Constants;
import com.bfly.core.cache.UserRightContainer;
import com.bfly.core.context.*;
import com.bfly.core.exception.UnAuthException;
import com.bfly.core.exception.UnRightException;
import com.bfly.core.security.Login;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 管理员后台拦截器判断用户权限信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 11:58
 */
@Component
public class ManageInterceptor extends HandlerInterceptorAdapter {

    /**
     * 授权密钥长度
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/26 16:06
     */
    private final int APP_AUTH_LENGTH = 32;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            IpThreadLocal.set(ContextUtil.getClientIp(request));
            ServletRequestThreadLocal.set(request);

            if (needLogin(method)) {
                String appAuth = request.getHeader(Constants.APP_AUTH);
                if (appAuth == null || appAuth.length() < APP_AUTH_LENGTH) {
                    throw new UnAuthException("未授权!");
                }
                Object appAuthObj = request.getSession().getAttribute(Constants.APP_AUTH);
                appAuth = appAuthObj == null ? null : appAuthObj.toString();
                if (appAuth == null || appAuth.length() < APP_AUTH_LENGTH) {
                    throw new UnAuthException("未授权!");
                }
                User admin = (User) request.getSession().getAttribute(Constants.USER_LOGIN_KEY);
                UserThreadLocal.set(admin);
                if (admin == null) {
                    throw new UnAuthException("未授权!");
                }
                if (admin.getStatus() != User.AVAILABLE_STATUS) {
                    throw new UnAuthException("未授权!");
                }
                if (!hasRight(admin, request)) {
                    throw new UnRightException("没有权限访问!");
                }
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        IpThreadLocal.clear();
        ServletRequestThreadLocal.clear();
        UserThreadLocal.clear();
        PagerThreadLocal.clear();
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
        return login == null ? true : login.required();
    }

    /**
     * 盘但是否具有访问权限
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/13 18:18
     */
    private boolean hasRight(User admin, HttpServletRequest request) {
        if(admin.isSuperAdmin()){
            return true;
        }
        String url = String.valueOf(request.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"));
        //先判断真实请求的URL地址
        if (UserRightContainer.exist(admin, url)) {
            return true;
        }
        //再判断模糊匹配URL
        url = String.valueOf(request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern"));
        return UserRightContainer.exist(admin, url);
    }
}
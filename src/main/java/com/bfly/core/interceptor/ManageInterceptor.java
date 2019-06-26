package com.bfly.core.interceptor;

import com.bfly.cms.user.entity.User;
import com.bfly.common.reflect.ReflectUtils;
import com.bfly.core.Constants;
import com.bfly.core.exception.UnAuthException;
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
            if (needLogin(method)) {
                String appAuth = request.getHeader(Constants.APP_AUTH);
                if (appAuth == null) {
                    Object appAuthObj = request.getSession().getAttribute(Constants.APP_AUTH);
                    appAuth = appAuthObj == null ? null : appAuthObj.toString();
                }
                if (appAuth == null || appAuth.length() < APP_AUTH_LENGTH) {
                    throw new UnAuthException("未授权!");
                }
                User admin = (User) request.getSession().getAttribute(Constants.USER_LOGIN_KEY);
                if (admin == null) {
                    throw new UnAuthException("未授权!");
                }
                if (admin.getStatus() != User.AVAILABLE_STATUS) {
                    throw new UnAuthException("未授权!");
                }
            }
        }
        return true;
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
}
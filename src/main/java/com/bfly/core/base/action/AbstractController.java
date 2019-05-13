package com.bfly.core.base.action;

import com.bfly.core.Constants;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * web站点controller抽象类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/29 11:46
 */
public abstract class AbstractController {

    /**
     * 返回 HttpServletRequest
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 11:52
     */
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) getRequestAttribute()).getRequest();
    }

    /**
     * 返回HttpServletSession
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 11:54
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    private RequestAttributes getRequestAttribute() {
        return RequestContextHolder.currentRequestAttributes();
    }

    /**
     * 判断请求是否来自移动端
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 11:54
     */
    protected boolean isMobileRequest() {
        String equipment = (String) getRequest().getAttribute(Constants.USER_AGENT_KEY);
        return "mobile".equalsIgnoreCase(equipment);
    }
}

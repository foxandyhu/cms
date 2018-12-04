package com.bfly.core.base.action;

import com.bfly.cms.user.entity.CmsAdmin;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.Constants;
import com.bfly.core.exception.ApiException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 后台管理接口父类Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/3 16:44
 */
public class BaseAdminController {

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
     * 获得当前登录管理员对象
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/3 20:36
     */
    public CmsAdmin getAdmin() {
        return (CmsAdmin) getSession().getAttribute(Constants.ADMIN_KEY);
    }

    /**
     * 后台管理Controller统一异常处理
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/3 16:51
     */
    @ExceptionHandler
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        if (e instanceof ApiException) {
            ApiException e1 = (ApiException) e;
            ResponseUtils.renderApiJson(response, request, e1.getResponse());
        } else {
            ResponseUtils.renderText(response, e.getMessage());
        }
    }
}

package com.bfly.core.base.action;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsUser;
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
     * 站点KEY标识
     */
    private static final String SITE_KEY = "_site_key";

    /**
     * 用户KEY标识
     */
    private static final String USER_KEY = "_user_key";

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

    /**
     * 判断请求是否来自移动端
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 11:54
     */
    protected boolean isMobileRequest() {
        String equipment = (String) getRequest().getAttribute("ua");
        return "mobile".equalsIgnoreCase(equipment);
    }

    private RequestAttributes getRequestAttribute() {
        return RequestContextHolder.currentRequestAttributes();
    }

    /**
     * 获得用户
     *
     * @return 用户
     * @author andy_hulibo@163.com
     * @date 2018/11/29 14:53
     */
    public CmsUser getUser() {
        return (CmsUser) getRequest().getAttribute(USER_KEY);
    }

    /**
     * 获得站点
     *
     * @return 站点
     * @author andy_hulibo@163.com
     * @date 2018/11/29 13:43
     */
    public CmsSite getSite() {
        return (CmsSite) getRequest().getAttribute(SITE_KEY);
    }

}

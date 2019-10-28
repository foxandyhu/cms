package com.bfly.core.base.action;

import com.bfly.core.Constants;
import com.bfly.core.config.editor.DateEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * 时间的处理
     * @author 胡礼波-Andy
     * @2014年11月13日下午4:20:44
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class,new DateEditor());
    }
}

package com.bfly.core.interceptor;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.service.CmsSiteMng;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.common.util.CheckMobile;
import com.bfly.core.Constants;
import com.bfly.core.web.CmsThreadVariable;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * CMS上下文信息拦截器
 * <p>
 * 包括登录信息、权限信息、站点信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 15:27
 */
@Component
public class WebContextInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(WebContextInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        CmsSite site = cmsSiteMng.getSite();
        if (site == null) {
            throw new RuntimeException("no site record in database!");
        }
        request.setAttribute(Constants.SITE_KEY, site);
        CmsThreadVariable.setSite(site);
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            String username = (String) subject.getPrincipal();
            CmsUser user = cmsUserMng.findByUsername(username);
            request.setAttribute(Constants.USER_KEY, user);
            CmsThreadVariable.setUser(user);
        }
        checkEquipment(request);
        return true;
    }

    /**
     * 检查访问方式
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/2 12:20
     */
    private void checkEquipment(HttpServletRequest request) {
        String ua = (String) request.getSession().getAttribute(Constants.USER_AGENT_KEY);
        if (null == ua) {
            try {
                String userAgent = request.getHeader("USER-AGENT").toLowerCase();
                ua = CheckMobile.check(userAgent) ? "mobile" : "pc";
                request.setAttribute(Constants.USER_AGENT_KEY, ua);
                request.getSession().setAttribute(Constants.USER_AGENT_KEY, ua);
            } catch (Exception e) {
                logger.error("检查请求浏览器类型出错", e);
            }
        }
    }

    @Autowired
    private CmsSiteMng cmsSiteMng;
    @Autowired
    private CmsUserMng cmsUserMng;
}
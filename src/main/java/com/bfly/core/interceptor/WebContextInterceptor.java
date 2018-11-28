package com.bfly.core.interceptor;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.service.CmsSiteMng;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.common.util.CheckMobile;
import com.bfly.common.web.CookieUtils;
import com.bfly.core.exception.SiteNotFoundException;
import com.bfly.core.web.CmsThreadVariable;
import com.bfly.core.web.util.CmsUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

;

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
    public static final String SITE_COOKIE = "_site_id_cookie";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws ServletException {
        CmsSite site = null;
        List<CmsSite> list = cmsSiteMng.getListFromCache();
        int size = list.size();
        if (size == 0) {

            throw new RuntimeException("no site record in database!");
        } else if (size == 1) {
            site = list.get(0);
        } else {
            String server = request.getServerName();
            String alias, redirect;
            for (CmsSite s : list) {
                // 检查域名
                if (s.getDomain().equals(server)) {
                    site = s;
                    break;
                }
                // 检查域名别名
                alias = s.getDomainAlias();
                if (!StringUtils.isBlank(alias)) {
                    for (String a : StringUtils.split(alias, ',')) {
                        if (a.equals(server)) {
                            site = s;
                            break;
                        }
                    }
                }
                // 检查重定向
                redirect = s.getDomainRedirect();
                if (!StringUtils.isBlank(redirect)) {
                    for (String r : StringUtils.split(redirect, ',')) {
                        if (r.equals(server)) {
                            try {
                                response.sendRedirect(s.getUrl());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            return false;
                        }
                    }
                }
            }
            if (site == null) {
                site = getByCookie(request);
            }
            if (site == null) {
                site = getByDefault();
            }
            if (site == null) {
                throw new SiteNotFoundException(server);
            }
        }
        if (site != null) {
            CookieUtils.addCookie(request, response, SITE_COOKIE, site.getId().toString(), null, null, "/");
        }
        CmsUtils.setSite(request, site);
        CmsThreadVariable.setSite(site);
        Subject subject = SecurityUtils.getSubject();
        CmsUser user = null;
        if (subject.isAuthenticated() || subject.isRemembered()) {
            String username = (String) subject.getPrincipal();
            user = cmsUserMng.findByUsername(username);
            CmsUtils.setUser(request, user);
            // Site加入线程变量
            CmsThreadVariable.setUser(user);
        }
        checkEquipment(request, response);
        return true;
    }

    /**
     * 检查访问方式
     */
    public void checkEquipment(HttpServletRequest request, HttpServletResponse response) {
        String ua = (String) request.getSession().getAttribute("ua");
        if (null == ua) {
            try {
                String userAgent = request.getHeader("USER-AGENT").toLowerCase();
                if (null == userAgent) {
                    userAgent = "";
                }
                if (CheckMobile.check(userAgent)) {
                    ua = "mobile";
                } else {
                    ua = "pc";
                }
                request.getSession().setAttribute("ua", ua);
            } catch (Exception e) {
            }
        }
        if (StringUtils.isNotBlank((ua))) {
            request.setAttribute("ua", ua);
        }
    }

    private CmsSite getByCookie(HttpServletRequest request) {
        Cookie cookie = CookieUtils.getCookie(request, SITE_COOKIE);
        if (cookie != null) {
            String v = cookie.getValue();
            if (!StringUtils.isBlank(v)) {
                try {
                    Integer siteId = Integer.parseInt(v);
                    return cmsSiteMng.findById(siteId);
                } catch (NumberFormatException e) {
                }
            }
        }
        return null;
    }

    private CmsSite getByDefault() {
        List<CmsSite> list = cmsSiteMng.getListFromCache();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Autowired
    private CmsSiteMng cmsSiteMng;
    @Autowired
    private CmsUserMng cmsUserMng;
}
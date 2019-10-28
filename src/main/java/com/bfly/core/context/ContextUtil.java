package com.bfly.core.context;

import com.bfly.cms.member.entity.Member;
import com.bfly.cms.system.entity.SiteConfig;
import com.bfly.cms.user.entity.User;
import com.bfly.common.StringUtil;
import com.bfly.core.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 系统上下文工具类
 *
 * @author 胡礼波
 * 2012-11-1 上午10:59:08
 */
public class ContextUtil {

    private static Logger logger = LoggerFactory.getLogger(ContextUtil.class);

    /**
     * 获得应用程序的根目录
     *
     * @return
     * @author 胡礼波
     * 2012-7-7 下午03:54:59
     */
    public static String getAppPath() {
        String path = new File(StringUtil.class.getClassLoader().getResource("").getPath()).getParentFile().getParentFile().getPath();
        try {
            path = URLDecoder.decode(path, Constants.ENCODEING_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 获得WEBAPP的路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/20 13:55
     */
    public static String getWebApp() {
        return System.getProperty(WebUtils.DEFAULT_WEB_APP_ROOT_KEY);
    }

    /**
     * 获得ClassLoader目录
     *
     * @param path XX/XX
     * @return
     * @author 胡礼波
     * 2012-7-7 下午03:52:57
     */
    public static String getClassLoaderPath(String path) {
        path = StringUtil.class.getClassLoader().getResource(path).getPath();
        try {
            path = URLDecoder.decode(path, Constants.ENCODEING_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 获得客户端请求的IP
     *
     * @param request
     * @return
     * @author 胡礼波
     * 2012-10-28 下午5:03:48
     */
    public static String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            //Ngnix代理
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 判断请求是否来自一个站点 防盗链作用
     *
     * @return
     * @author 胡礼波
     * 2014-5-17 下午5:43:10
     */
    public static boolean isSelfSiteRequest(HttpServletRequest request) {
        //传来的页面
        String refererTo = request.getHeader("referer");
        //防盗链
        if (refererTo == null || refererTo.trim().equals("") || !refererTo.contains(request.getServerName())) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否是ajax请求
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/6 14:48
     */
    public static boolean isAjax(HttpServletRequest request) {
        String xmlRequest = request.getHeader("X-Requested-With");
        String ajaxFlag = "XMLHttpRequest";
        return ajaxFlag.equals(xmlRequest);
    }

    /**
     * 获得站点配置
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/19 12:25
     */
    public static SiteConfig getSiteConfig(ServletContext context) {
        return (SiteConfig) context.getAttribute("site");
    }

    /**
     * 设置站点配置ServletContext
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/19 12:25
     */
    public static void setSiteConfig(SiteConfig config, ServletContext context) {
        context.setAttribute("site", config);
    }

    /**
     * 获得登录会员
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/16 22:08
     */
    public static Member getLoginMember() {
        Member member = null;
        try {
            Subject subject = SecurityUtils.getSubject();
            member = null;
            if (subject.isAuthenticated() && subject.getPrincipal() instanceof Member) {
                member = (Member) subject.getPrincipal();
            }
        } catch (Exception e) {
        }
        return member;
    }

    /**
     * 获得登录用户
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/16 22:09
     */
    public static User getLoginUser() {
        User user = null;
        try {
            Subject subject = SecurityUtils.getSubject();
            user = null;
            if (subject.isAuthenticated() && subject.getPrincipal() instanceof User) {
                user = (User) subject.getPrincipal();
            }
        } catch (Exception e) {
        }
        return user;
    }
}

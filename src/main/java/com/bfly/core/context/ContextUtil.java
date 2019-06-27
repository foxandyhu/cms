package com.bfly.core.context;

import com.bfly.common.StringUtil;
import com.bfly.core.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * 获得WEB-INF下的目录结构
     *
     * @param path 格式为"/XXX"
     * @return
     * @author 胡礼波
     * 2012-7-7 下午03:48:14
     */
    public static String getWebInfPath(String path) {
        path = new File(StringUtil.class.getClassLoader().getResource("").getPath()).getParentFile().getPath() + path;
        try {
            path = URLDecoder.decode(path, Constants.ENCODEING_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return path;
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

}

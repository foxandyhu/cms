package com.bfly.core.interceptor;

import com.bfly.common.web.RequestUtils;
import com.bfly.config.FireWallProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

/**
 * 网站防火墙拦截器
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 14:05
 */
@Component
public class FireWallInterceptor extends HandlerInterceptorAdapter {

    private static final String FIREWALL_CONFIG_LAST_MODIFIED = "firewall_config_last_modified";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Boolean configFileModified = false;
        Long configLastModifiedTime = getFireWallConfigFileLastModifiedTime(request);
        if (configLastModifiedTime == null || fireWallProperty.getFireWallFile().lastModified() > configLastModifiedTime) {
            configFileModified = true;
            changeConfigModifiedTime(request);
        }
        if (configFileModified) {
            fireWallProperty.reload();
        }
        String open = "1";
        if (!open.equals(fireWallProperty.getOpen())) {
            return true;
        }

        String[] ipArrays = StringUtils.split(fireWallProperty.getIps(), ",");
        String[] weekArrays = StringUtils.split(fireWallProperty.getWeek(), ",");
        String[] hourArrays = StringUtils.split(fireWallProperty.getHour(), ",");

        String requestIp = RequestUtils.getIpAddr(request);
        if (!isAuthDomain(fireWallProperty.getDomain(), request.getServerName())) {
            return false;
        }
        if (!isAuthIp(ipArrays, requestIp)) {
            return false;
        }
        if (!isAuthWeek(weekArrays)) {
            return false;
        }
        return isAuthHour(hourArrays);
    }

    private Boolean isAuthDomain(String domain, String requestDomain) {
        return StringUtils.isBlank(domain) || domain.equals(requestDomain);
    }

    private Boolean isAuthIp(String[] ips, String requestIp) {
        return isCheck(ips, requestIp);
    }

    private Boolean isAuthWeek(String[] weeks) {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return isCheck(weeks, String.valueOf(dayOfWeek));
    }

    private Boolean isAuthHour(String[] hours) {
        Calendar c = Calendar.getInstance();
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        return isCheck(hours, String.valueOf(hourOfDay));
    }

    private boolean isCheck(String[] times, String time) {
        if (times == null) {
            return true;
        }
        for (String t : times) {
            if (t.equals(time)) {
                return true;
            }
        }
        return false;
    }

    private Long getFireWallConfigFileLastModifiedTime(HttpServletRequest request) {
        return (Long) request.getSession().getServletContext().getAttribute(FIREWALL_CONFIG_LAST_MODIFIED);
    }

    private void changeConfigModifiedTime(HttpServletRequest request) {
        request.getSession().getServletContext().setAttribute(FIREWALL_CONFIG_LAST_MODIFIED, Calendar.getInstance().getTime().getTime());
    }

    @Autowired
    private FireWallProperty fireWallProperty;
}
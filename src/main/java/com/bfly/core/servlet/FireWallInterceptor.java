package com.bfly.core.servlet;

import com.bfly.common.web.RequestUtils;
import com.bfly.core.web.FireWallProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

/**
 * 网站防火墙拦截器
 */
//@Component
public class FireWallInterceptor extends HandlerInterceptorAdapter implements DisposableBean {
    public static final String FIREWALL_CONFIG_LASTMODIFIED = "firewall_config_lastmodified";

    @Override
    public void destroy() throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        Boolean configFileModified = false;
        Long configLastModifiedTime = getFireWallConfigFileLastModifiedTime(request);
        if (configLastModifiedTime == null || fireWallProperty.getFireWallFile().lastModified() > configLastModifiedTime) {
            configFileModified = true;
            changeConfigModifiedTime(request);
        }
        if (configFileModified) {
            fireWallProperty.reload();
        }
        String[] ipArrays = StringUtils.split(fireWallProperty.getIps(), ",");
        String[] weekArrays = StringUtils.split(fireWallProperty.getWeek(), ",");
        String[] hourArrays = StringUtils.split(fireWallProperty.getHour(), ",");

        String requestIp = RequestUtils.getIpAddr(request);
        if ("1".equals(fireWallProperty.getOpen())) {
            if (!isAuthDomain(fireWallProperty.getDomain(), request.getServerName())) {
                return false;
            } else {
                if (!isAuthIp(ipArrays, requestIp)) {
                    return false;
                } else {
                    if (!isAuthWeek(weekArrays)) {
                        return false;
                    } else {
                        if (!isAuthHour(hourArrays)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private Boolean isAuthDomain(String domain, String requestDomain) {
        if (StringUtils.isNotBlank(domain)) {
            if (domain.equals(requestDomain)) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private Boolean isAuthIp(String[] ips, String requestIp) {
        if (ips != null && ips.length > 0) {
            for (String ip : ips) {
                if (ip.equals(requestIp)) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    private Boolean isAuthWeek(String[] weeks) {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK);
        if (weeks != null && weeks.length > 0) {
            for (String week : weeks) {
                if (week.equals(day_of_week + "")) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    private Boolean isAuthHour(String[] hours) {
        Calendar c = Calendar.getInstance();
        int hour_of_day = c.get(Calendar.HOUR_OF_DAY);
        if (hours != null && hours.length > 0) {
            for (String hour : hours) {
                if (hour.equals(hour_of_day + "")) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    private Long getFireWallConfigFileLastModifiedTime(HttpServletRequest request) {
        return (Long) request.getSession().getServletContext().getAttribute(FIREWALL_CONFIG_LASTMODIFIED);
    }

    private void changeConfigModifiedTime(HttpServletRequest request) {
        request.getSession().getServletContext().setAttribute(FIREWALL_CONFIG_LASTMODIFIED, Calendar.getInstance().getTime().getTime());
    }

    @Autowired
    private FireWallProperty fireWallProperty;
}
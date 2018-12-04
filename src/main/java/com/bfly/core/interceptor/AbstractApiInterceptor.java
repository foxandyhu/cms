package com.bfly.core.interceptor;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.service.CmsSiteMng;
import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.cms.webservice.service.ApiAccountMng;
import com.bfly.common.web.CookieUtils;
import com.bfly.core.Constants;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 抽象的拦截器
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 13:44
 */
public abstract class AbstractApiInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = Logger.getLogger(AbstractApiInterceptor.SITE_PARAM);

    @Autowired
    protected ApiAccountMng apiAccountMng;

    @Autowired
    protected CmsSiteMng cmsSiteMng;

    private static final String SITE_PARAM = "_site_id_param";
    private static final String SITE_COOKIE = "_site_id_cookie";

    @Deprecated
    protected Object[] validateSign(HttpServletRequest request, WebErrors errors) throws Exception {
        boolean valid = true;
        String sign = request.getParameter(Constants.COMMON_PARAM_SIGN);
        String appId = request.getParameter(Constants.COMMON_PARAM_APPID);
        ApiAccount apiAccount = apiAccountMng.findByAppId(appId);
        errors = ApiValidate.validateApiAccount(request, errors, apiAccount);
        String code = "";
        Object[] result = new Object[2];
        if (errors.hasErrors()) {
            code = ResponseCode.API_CODE_APP_PARAM_ERROR;
            valid = false;
        } else {
            // 验证签名
            ApiValidate.validateSign(request, apiAccount, sign);
            // Account可能获取不到，需要再次判断
            if (errors.hasErrors()) {
                code = ResponseCode.API_CODE_SIGN_ERROR;
                valid = false;
            }
        }
        result[0] = valid;
        result[1] = code;
        return result;
    }

    protected CmsSite getByCookie(HttpServletRequest request) {
        Cookie cookie = CookieUtils.getCookie(request, SITE_COOKIE);
        if (cookie != null) {
            String v = cookie.getValue();
            if (!StringUtils.isBlank(v)) {
                try {
                    Integer siteId = Integer.parseInt(v);
                    return cmsSiteMng.findById(siteId);
                } catch (NumberFormatException e) {
                    log.warn("cookie site id format exception", e);
                }
            }
        }
        return null;
    }

    protected CmsSite getByParams(HttpServletRequest request, HttpServletResponse response) {
        String p = request.getParameter(SITE_PARAM);
        if (!StringUtils.isBlank(p)) {
            try {
                Integer siteId = Integer.parseInt(p);
                CmsSite site = cmsSiteMng.findById(siteId);
                if (site != null) {
                    // 若使用参数选择站点，则应该把站点保存至cookie中才好。
                    CookieUtils.addCookie(request, response, SITE_COOKIE, site.getId().toString(), null, null);
                    return site;
                }
            } catch (NumberFormatException e) {
                log.warn("param site id format exception", e);
            }
        }
        return null;
    }

    protected CmsSite getByDefault() {
        CmsSite site = cmsSiteMng.getSite();
        return site;
    }

    protected CmsSite getByDomain(HttpServletRequest request) {
        String domain = request.getServerName();
        if (!StringUtils.isBlank(domain)) {
            return cmsSiteMng.findByDomain(domain);
        }
        return null;
    }
}
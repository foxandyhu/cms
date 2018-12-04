package com.bfly.core.interceptor;

import com.bfly.cms.user.entity.CmsAdmin;
import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.cms.webservice.entity.ApiUserLogin;
import com.bfly.cms.webservice.service.ApiRecordMng;
import com.bfly.cms.webservice.service.ApiUserLoginMng;
import com.bfly.common.web.RequestUtils;
import com.bfly.core.Constants;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.exception.ApiException;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

/**
 * 管理员后台拦截器判断用户权限信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 11:58
 */
@Component
public class AdminApiInterceptor extends AbstractApiInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        // 验证appId是否有效
        ApiAccount apiAccount = apiAccountMng.getApiAccount(request);
        if (apiAccount == null || apiAccount.getDisabled()) {
            throw new ApiException("appId参数错误", ResponseCode.API_CODE_APP_PARAM_ERROR);
        }
        boolean needValidateSign = false;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        SignValidate annotation = method.getAnnotation(SignValidate.class);
        if (annotation != null) {
            needValidateSign = annotation.need();
        }
        if (needValidateSign) {
            String sign = request.getParameter(Constants.COMMON_PARAM_SIGN);
            ApiValidate.validateSign(request, apiAccount, sign);
            apiRecordMng.callApiRecord(RequestUtils.getIpAddr(request), apiAccount.getAppId(), request.getRequestURI(), sign);
        }

        // 不在验证的范围内
        if (exclude(uri)) {
            return true;
        }
        CmsAdmin admin = (CmsAdmin) request.getSession().getAttribute(Constants.ADMIN_KEY);
        if (admin == null) {
            throw new ApiException("用户未登录", ResponseCode.API_CODE_USER_NOT_LOGIN);
        }
        Short userStatus = apiUserLoginMng.getStatus(apiAccount, request, response);
        // 判断用户活跃状态
        if (userStatus.equals(ApiUserLogin.USER_STATUS_LOGOVERTIME)) {
            throw new ApiException("用户登录超时", ResponseCode.API_CODE_USER_NOT_LOGIN);
        }
        // 用户是否拥有本API调用的权限
        if (!hasUrlPerm(admin, uri)) {
            throw new ApiException("用户没有权限", ResponseCode.API_CODE_USER_NOT_LOGIN);
        }
        // 刷新用户活跃时间
        apiUserLoginMng.userActive(request, response);
        return true;
    }

    private boolean exclude(String uri) {
        if (excludeUrls != null) {
            for (String exc : excludeUrls) {
                if (exc.equals(uri)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasUrlPerm(CmsAdmin admin, String url) {
        Set<String> perms = admin.getPerms();
        if (perms == null) {
            return false;
        }
        Iterator<String> it = perms.iterator();
        String perm;
        while (it.hasNext()) {
            perm = it.next();
            if ("*".equals(perm) || ("/api/admin" + url).equals(perm)) {
                return true;
            }
        }
        return false;
    }

    @Autowired
    private ApiUserLoginMng apiUserLoginMng;
    @Autowired
    private ApiRecordMng apiRecordMng;
    private String[] excludeUrls;

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }
}
package com.bfly.core.interceptor;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserSite;
import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.cms.webservice.entity.ApiUserLogin;
import com.bfly.cms.webservice.service.ApiRecordMng;
import com.bfly.cms.webservice.service.ApiUserLoginMng;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.Constants;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.security.CmsAuthorizingRealm;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.CmsThreadVariable;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashSet;
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
        // 正常状态
        String uri = getURI(request);
        WebErrors errors = WebErrors.create(request);
        // 验证appId是否有效
        String code = ResponseCode.API_CODE_USER_NOT_LOGIN;
        ApiAccount apiAccount = apiAccountMng.getApiAccount(request);
        if (apiAccount != null && !apiAccount.getDisabled()) {
            // 获取用户并且刷新用户活跃时间
            CmsUser user = apiUserLoginMng.getUser(apiAccount, request);
            // 获取用户状态
            Short userStatus = apiUserLoginMng.getStatus(apiAccount, request, response);
            if (user == null) {
                errors.addErrorString(Constants.API_MESSAGE_USER_NOT_LOGIN);
            } else {
                // 获得站点
                CmsSite site = getSite(user, request, response);
                CmsUtils.setSite(request, site);
                boolean hasNoSiteRight = false;
                // 没有该站管理权限(则切换站点？)
                if (site != null && user.getUserSite(site.getId()) == null) {
                    Set<CmsUserSite> userSites = user.getUserSites();
                    if (userSites != null && userSites.size() > 0) {
                        CmsSite s = userSites.iterator().next().getSite();
                        authorizingRealm.removeUserAuthorizationInfoCache(user.getUsername());
                        CmsUtils.setSite(request, s);
                        CmsThreadVariable.setSite(s);
                        hasNoSiteRight = true;
                    }
                }
                // Site加入线程变量
                CmsThreadVariable.setSite(CmsUtils.getSite(request));
                // 不在验证的范围内
                if (exclude(uri)) {
                    return true;
                }
                // 判断用户活跃状态
                if (userStatus.equals(ApiUserLogin.USER_STATUS_LOGOVERTIME)) {
                    errors.addErrorString(Constants.API_MESSAGE_USER_OVER_TIME);
                    code = ResponseCode.API_CODE_USER_STATUS_OVER_TIME;
                } else {
                    // 用户不是管理员，提示无权限。用户是否拥有本API调用的权限
                    if (!user.getAdmin() || !hasUrlPerm(user, uri) || hasNoSiteRight) {
                        errors.addErrorString(Constants.API_MESSAGE_USER_NOT_HAS_PERM);
                        code = ResponseCode.API_CODE_USER_NOT_HAS_PERM;
                    } else {
                        boolean needValidateSign = false;

                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                        Method method = handlerMethod.getMethod();
                        SignValidate annotation = method.getAnnotation(SignValidate.class);
                        if (annotation != null) {
                            needValidateSign = annotation.need();
                        }
                        if (needValidateSign) {
                            if (user.getViewonlyAdmin()) {
                                errors.addErrorString(Constants.API_MESSAGE_USER_NOT_HAS_PERM);
                                code = ResponseCode.API_CODE_USER_NOT_HAS_PERM;
                            } else {
                                Object[] result = validateSign(request, errors);
                                Boolean succ = (Boolean) result[0];
                                if (!succ) {
                                    code = (String) result[1];
                                } else {
                                    // 需要验证签名是否重复请求数据
                                    String sign = RequestUtils.getQueryParam(request, Constants.COMMON_PARAM_SIGN);
                                    apiRecordMng.callApiRecord(RequestUtils.getIpAddr(request),
                                            apiAccount.getAppId(), request.getRequestURI(), sign);
                                }
                            }
                        }
                        CmsUtils.setUser(request, user);
                        // User加入线程变量
                        CmsThreadVariable.setUser(user);
                    }
                    // 刷新用户活跃时间
                    apiUserLoginMng.userActive(request, response);
                }
            }
        } else {
            code = ResponseCode.API_CODE_APP_PARAM_ERROR;
            errors.addErrorString(Constants.API_MESSAGE_APP_PARAM_ERROR);
        }
        if (errors.hasErrors()) {
            String message = errors.getErrors().get(0);
            String body = "\"\"";
            ApiResponse apiResponse = new ApiResponse(request, body, message, code);
            ResponseUtils.renderApiJson(response, request, apiResponse);
            return false;
        }
        return true;
    }

    /**
     * 按参数、cookie、域名、默认。
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param user     CmsUser
     * @return 不会返回null，如果站点不存在，则抛出异常。
     */
    private CmsSite getSite(CmsUser user, HttpServletRequest request, HttpServletResponse response) {
        CmsSite site = getByParams(request, response);
        if (site == null) {
            site = getByCookie(request);
        }
        if (site == null) {
            if (!hasRepeatDomainSite(request)) {
                site = getByDomain(request);
            }
        }
        if (site == null) {
            site = getByUserSites(user);
        }
        if (site == null) {
            site = getByDefault();
        }
        if (site == null) {
            throw new RuntimeException("cannot get site!");
        }
        return site;
    }

    private CmsSite getByUserSites(CmsUser user) {
        if (user != null) {
            Set<CmsSite> sites = user.getSites();
            if (sites != null) {
                return sites.iterator().next();
            }
        }
        return null;
    }

    private boolean hasRepeatDomainSite(HttpServletRequest request) {
        String domain = request.getServerName();
        return StringUtils.isNotBlank(domain) && cmsSiteMng.hasRepeatByProperty("domain");
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

    /**
     * 获得第三个路径分隔符的位置
     *
     * @param request HttpServletRequest
     * @throws IllegalStateException 访问路径错误，没有三(四)个'/'
     */
    private static String getURI(HttpServletRequest request) throws IllegalStateException {
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        String ctxPath = helper.getOriginatingContextPath(request);
        int start = 0, i = 0, count = 2;
        if (!StringUtils.isBlank(ctxPath)) {
            count++;
        }
        while (i < count && start != -1) {
            start = uri.indexOf('/', start + 1);
            i++;
        }
        if (start <= 0) {
            throw new IllegalStateException("admin access path not like '/jeeadmin/jeecms/...' pattern: " + uri);
        }
        return uri.substring(start);
    }

    private boolean hasUrlPerm(CmsUser user, String url) {
        Set<String> perms = getUserPermission(user);
        if (perms == null) {
            return true;
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

    private Set<String> getUserPermission(CmsUser user) {
        Set<String> perms = user.getPerms();
        Set<String> userPermission = new HashSet<>();
        if (perms != null) {
            for (String perm : perms) {
                userPermission.add(perm);
            }
        }
        return userPermission;
    }


    @Autowired
    private ApiUserLoginMng apiUserLoginMng;
    @Autowired
    private ApiRecordMng apiRecordMng;
    @Autowired
    private CmsAuthorizingRealm authorizingRealm;
    private String[] excludeUrls;

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }
}
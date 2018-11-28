package com.bfly.core.interceptor;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.cms.webservice.entity.ApiUserLogin;
import com.bfly.cms.webservice.service.ApiAccountMng;
import com.bfly.cms.webservice.service.ApiRecordMng;
import com.bfly.cms.webservice.service.ApiUserLoginMng;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.Constants;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.CmsThreadVariable;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 会员中心API拦截器
 * <p>
 * 判断用户
 *
 * @author tom
 */
@Component("memberApiInterceptor")
public class MemberApiInterceptor extends AbstractApiInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获得站点
        CmsSite site = getSite(request, response);
        CmsUtils.setSite(request, site);
        // Site加入线程变量
        CmsThreadVariable.setSite(site);
        WebErrors errors = WebErrors.create(request);
        //验证appId是否有效
        String code = ResponseCode.API_CODE_USER_NOT_LOGIN;
        ApiAccount apiAccount = apiAccountMng.getApiAccount(request);
        if (apiAccount != null && !apiAccount.getDisabled()) {
            //获取用户并且刷新用户活跃时间
            CmsUser user = apiUserLoginMng.getUser(apiAccount, request);
            //获取用户状态
            Short userStatus = apiUserLoginMng.getStatus(apiAccount, request, response);
            CmsUtils.setUser(request, user);
            // User加入线程变量
            CmsThreadVariable.setUser(user);
            if (user == null) {
                errors.addErrorString(Constants.API_MESSAGE_USER_NOT_LOGIN);
            } else {
                //判断用户活跃状态
                if (userStatus.equals(ApiUserLogin.USER_STATUS_LOGOVERTIME)) {
                    errors.addErrorString(Constants.API_MESSAGE_USER_OVER_TIME);
                    code = ResponseCode.API_CODE_USER_STATUS_OVER_TIME;
                } else {
                    boolean needValidateSign = false;
                    String url = "/api/member/ueditor/upload";
                    if (!request.getRequestURI().endsWith(url)) {
                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                        Method method = handlerMethod.getMethod();
                        SignValidate annotation = method.getAnnotation(SignValidate.class);
                        if (annotation != null) {
                            needValidateSign = annotation.need();
                        }
                        //是否添加、修改、删除操作，是需要校验签名
                        if (needValidateSign) {
                            Object[] result = validateSign(request, errors);
                            Boolean succ = (Boolean) result[0];
                            if (!succ) {
                                code = (String) result[1];
                            } else {
                                String sign = RequestUtils.getQueryParam(request, Constants.COMMON_PARAM_SIGN);
                                apiRecordMng.callApiRecord(RequestUtils.getIpAddr(request)
                                        , apiAccount.getAppId(), request.getRequestURI(), sign);
                            }
                        }
                    }
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

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView mav)
            throws Exception {
        //刷新用户活跃时间
        apiUserLoginMng.userActive(request, response);
    }

    /**
     * 按参数、cookie、域名、默认。
     *
     * @param request HttpServletRequest
     * @return 不会返回null，如果站点不存在，则抛出异常。
     */
    private CmsSite getSite(HttpServletRequest request, HttpServletResponse response) {
        CmsSite site = getByParams(request, response);
        if (site == null) {
            site = getByCookie(request);
        }
        if (site == null) {
            site = getByDomain(request);
        }
        if (site == null) {
            site = getByDefault();
        }
        if (site == null) {
            throw new RuntimeException("cannot get site!");
        } else {
            return site;
        }
    }

    @Autowired
    private ApiAccountMng apiAccountMng;
    @Autowired
    private ApiUserLoginMng apiUserLoginMng;
    @Autowired
    private ApiRecordMng apiRecordMng;

}
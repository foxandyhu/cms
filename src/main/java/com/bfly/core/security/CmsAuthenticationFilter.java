package com.bfly.core.security;

import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.UnifiedUser;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.cms.user.service.UnifiedUserMng;
import com.bfly.common.web.CookieUtils;
import com.bfly.common.web.RequestUtils;
import com.bfly.core.exception.*;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/28 16:08
 * CmsAuthenticationFilter自定义登录认证filter
 */
@Component
public class CmsAuthenticationFilter extends FormAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(CmsAuthenticationFilter.class);

    private static final String CAPTCHA_PARAM = "captcha";

    private static final String RETURN_URL = "returnUrl";

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "create AuthenticationToken error";
            throw new IllegalStateException(msg);
        }
        HttpServletRequest req = (HttpServletRequest) request;
        String username = (String) token.getPrincipal();
        //验证码校验
        if (isCaptchaRequired(username, req)) {
            String captcha = request.getParameter(CAPTCHA_PARAM);
            if (captcha != null) {
                if (!imageCaptchaService.validateResponseForID(((HttpServletRequest) request).getSession().getId(), captcha)) {
                    return onLoginFailure(token, new CaptchaErrorException(), request, response);
                }
            }
            return onLoginFailure(token, new CaptchaRequiredException(), request, response);
        }
        CmsUser user = userMng.findByUsername(username);
        if (user != null) {
            if (user.getDisabled()) {
                return onLoginFailure(token, new DisabledException(), request, response);
            }
            if (!isActive(user)) {
                return onLoginFailure(token, new InactiveException(), request, response);
            }
            if (!isChecked(user)) {
                return onLoginFailure(token, new UserUnCheckedException(), request, response);
            }
        }
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    /**
     * 登录成功
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/1 21:29
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String username = (String) subject.getPrincipal();
        CmsUser user = cmsUserMng.findByUsername(username);
        String ip = RequestUtils.getIpAddr(req);
        String userSessionId = ((HttpServletRequest) request).getSession().getId();
        userMng.updateLoginInfo(user.getId(), ip, new Date(), userSessionId);
        unifiedUserMng.updateLoginSuccess(user.getId(), ip);
        loginCookie(username, req, res);
        return super.onLoginSuccess(token, subject, request, response);
    }

    /**
     * 登录失败
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/1 21:29
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        String username = (String) token.getPrincipal();
        HttpServletRequest req = (HttpServletRequest) request;
        String ip = RequestUtils.getIpAddr(req);
        CmsUser user = userMng.findByUsername(username);
        if (user != null) {
            unifiedUserMng.updateLoginError(user.getId(), ip);
        }
        return super.onLoginFailure(token,e, request,response);
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String successUrl = req.getParameter(RETURN_URL);
        if (StringUtils.isBlank(successUrl)) {
            successUrl = getSuccessUrl();
        }
        WebUtils.getAndClearSavedRequest(request);
        WebUtils.issueRedirect(request, response, successUrl, null, true);
    }

    private void loginCookie(String username, HttpServletRequest request, HttpServletResponse response) {
        String domain = request.getServerName();
        String dot = ".";
        if (domain.contains(dot)) {
            domain = domain.substring(domain.indexOf(dot) + 1);
        }
        CookieUtils.addCookie(response, "JSESSIONID", request.getSession().getId(), null, domain, "/");
        try {
            CookieUtils.addCookie(response, "username", URLEncoder.encode(username, "utf-8"), null, domain, "/");
        } catch (UnsupportedEncodingException e) {
            logger.error("登录Cookie出错", e);
        }
        CookieUtils.addCookie(response, "sso_logout", null, 0, domain, "/");
    }

    private boolean isCaptchaRequired(String username, HttpServletRequest request) {
        Integer errorRemaining = unifiedUserMng.errorRemaining(username);
        String captcha = RequestUtils.getQueryParam(request, CAPTCHA_PARAM);
        // 如果输入了验证码，那么必须验证；如果没有输入验证码，则根据当前用户判断是否需要验证码。
        return !StringUtils.isBlank(captcha) || (errorRemaining != null && errorRemaining < 0);
    }

    /**
     * 用户激活了返回true 未查找到用户或者非禁用返回false
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/28 16:08
     */
    private boolean isActive(CmsUser user) {
        UnifiedUser unifiedUser = unifiedUserMng.findById(user.getId());
        if (unifiedUser != null) {
            return unifiedUser.getActivation();
        }
        return false;
    }

    /**
     * 判断用户当前是否已审核.审核返回true,其它状态返回false
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/20 16:21
     */
    private boolean isChecked(CmsUser user) {
        return CmsUser.USER_STATU_CHECKED.equals(user.getStatu());
    }


    @Autowired
    private CmsUserMng userMng;
    @Autowired
    private UnifiedUserMng unifiedUserMng;
    @Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private CmsUserMng cmsUserMng;
}

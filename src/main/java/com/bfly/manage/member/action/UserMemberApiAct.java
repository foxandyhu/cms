package com.bfly.manage.member.action;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserExt;
import com.bfly.cms.user.service.CmsUserExtMng;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.cms.webservice.entity.ApiUserLogin;
import com.bfly.cms.webservice.service.ApiAccountMng;
import com.bfly.cms.webservice.service.ApiUserLoginMng;
import com.bfly.common.util.AES128Util;
import com.bfly.common.util.DateUtils;
import com.bfly.common.web.LoginUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.Constants;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping(value = "/api/member")
public class UserMemberApiAct {

    /**
     * 获取用户状态API
     *
     * @param username   用户名 必选
     * @param sessionKey 会话标识 必选
     * @param appId      appID 必选
     */
    @SignValidate
    @RequestMapping(value = "/user/getStatus")
    public void getUserStatus(
            String username, String sessionKey, String appId,
            HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        ApiAccount apiAccount = null;
        CmsUser user = null;
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, sessionKey, appId, username);
        if (!errors.hasErrors()) {
            apiAccount = apiAccountMng.findByAppId(appId);
            user = cmsUserMng.findByUsername(username);
            if (user != null) {
                String aesKey = apiAccount.getAesKey();
                String decryptSessionKey = null;
                try {
                    decryptSessionKey = AES128Util.decrypt(sessionKey, aesKey, apiAccount.getIvKey());
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                if (StringUtils.isNotBlank(decryptSessionKey)) {
                    ApiUserLogin userLogin = apiUserLoginMng.findUserLogin(username, decryptSessionKey);
                    if (userLogin != null) {
                        message = Constants.API_MESSAGE_USER_STATUS_OVER_TIME;
                        code = ResponseCode.API_CODE_USER_STATUS_OVER_TIME;
                        if (userLogin.getActiveTime() != null) {
                            Date now = Calendar.getInstance().getTime();
                            Double timeOver = DateUtils.getDiffMinuteTwoDate(userLogin.getActiveTime(), now);
                            if (timeOver <= Constants.USER_OVER_TIME) {
                                message = Constants.API_MESSAGE_USER_STATUS_LOGIN;
                                code = ResponseCode.API_CODE_USER_STATUS_LOGIN;
                                LoginUtils.loginShiro(request, response, username);
                            } else {
                                CmsUser currUser = CmsUtils.getUser(request);
                                if (currUser != null) {
                                    apiUserLoginMng.userActive(request, response);
                                } else {
                                    //如果记住登录的
                                    Subject subject = SecurityUtils.getSubject();
                                    if (subject.isRemembered()) {
                                        String rememberUser = (String) subject.getPrincipal();
                                        LoginUtils.loginShiro(request, response, rememberUser);
                                    } else {
                                        LoginUtils.logout();
                                    }
                                }
                            }
                        }
                    } else {
                        message = Constants.API_MESSAGE_USER_STATUS_NOT_LOGIN;
                        code = ResponseCode.API_CODE_USER_STATUS_LOGOUT;
                        LoginUtils.logout();
                    }
                } else {
                    message = Constants.API_MESSAGE_PARAM_ERROR;
                    code = ResponseCode.API_CODE_PARAM_ERROR;
                }
            } else {
                //用户不存在
                message = Constants.API_MESSAGE_USER_NOT_FOUND;
                code = ResponseCode.API_CODE_USER_NOT_FOUND;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }


    /**
     * 修改会员资料
     *
     * @param username    用户名   必选
     * @param realname    真实姓名 非必选
     * @param gender      性别 非必选
     * @param birthdayStr 生日 格式"yyyy-MM-dd" 例如"1980-01-01" 非必选
     * @param phone       电话 非必选
     * @param mobile      手机 非必选
     * @param qq          qq号  非必选
     * @param userImg     用户头像 非必选
     */
    @SignValidate
    @RequestMapping(value = "/user/edit")
    public void userEdit(
            String username, String realname, Boolean gender,
            String birthdayStr, String phone, String mobile, String qq,
            String userImg,
            HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        CmsUser user = null;
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, username);
        if (!errors.hasErrors()) {
            user = cmsUserMng.findByUsername(username);
            if (user != null) {
                CmsUserExt userExt = user.getUserExt();
                if (StringUtils.isNotBlank(birthdayStr)) {
                    userExt.setBirthday(DateUtils.parseDayStrToDate(birthdayStr));
                }
                userExt.setGender(gender);
                if (StringUtils.isNotBlank(mobile)) {
                    userExt.setMobile(mobile);
                }
                if (StringUtils.isNotBlank(phone)) {
                    userExt.setPhone(phone);
                }
                if (StringUtils.isNotBlank(qq)) {
                    userExt.setQq(qq);
                }
                if (StringUtils.isNotBlank(realname)) {
                    userExt.setRealname(realname);
                }
                if (StringUtils.isNotBlank(userImg)) {
                    userExt.setUserImg(userImg);
                }
                cmsUserExtMng.update(userExt, user);
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            } else {
                //用户不存在
                message = Constants.API_MESSAGE_USER_NOT_FOUND;
                code = ResponseCode.API_CODE_USER_NOT_FOUND;
            }
        }

        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 修改会员密码
     *
     * @param username 用户名   必选
     * @param email    邮箱 非必选
     * @param origPwd  原密码  必选
     * @param newPwd   新密码 必选
     */
    @SignValidate
    @RequestMapping(value = "/user/pwd")
    public void pwdEdit(String username, String origPwd, String newPwd, String email, HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        CmsUser user = null;
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, username);
        if (!errors.hasErrors()) {
            user = cmsUserMng.findByUsername(username);
            if (user != null) {
                //原密码错误
                if (!cmsUserMng.isPasswordValid(user.getId(), origPwd)) {
                    message = Constants.API_MESSAGE_ORIGIN_PWD_ERROR;
                    code = ResponseCode.API_CODE_ORIGIN_PWD_ERROR;
                } else {
                    cmsUserMng.updatePwdEmail(user.getId(), newPwd, email);
                    message = Constants.API_MESSAGE_SUCCESS;
                    code = ResponseCode.API_CODE_CALL_SUCCESS;
                }
            } else {
                //用户不存在
                message = Constants.API_MESSAGE_USER_NOT_FOUND;
                code = ResponseCode.API_CODE_USER_NOT_FOUND;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 获取用户信息
     *
     * @param username 用户名必选
     */
    @RequestMapping(value = "/user/get")
    public void getUserInfo(Integer https, String username, HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        String body = "\"\"";
        String message;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        if (https == null) {
            https = Constants.URL_HTTP;
        }
        WebErrors errors = WebErrors.create(request);
        CmsUser user = null;
        CmsSite site = CmsUtils.getSite(request);
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, username);
        if (!errors.hasErrors()) {
            user = cmsUserMng.findByUsername(username);
            if (user != null) {
                try {
                    body = user.convertToJson(site, https, null).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                message = Constants.API_MESSAGE_SUCCESS;
            } else {
                //用户不存在
                message = Constants.API_MESSAGE_USER_NOT_FOUND;
            }
        } else {
            message = Constants.API_MESSAGE_PARAM_REQUIRED;
            code = ResponseCode.API_CODE_PARAM_REQUIRED;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private String weiXinJsCode2SessionUrl;

    public String getWeiXinJsCode2SessionUrl() {
        return weiXinJsCode2SessionUrl;
    }

    public void setWeiXinJsCode2SessionUrl(String weiXinJsCode2SessionUrl) {
        this.weiXinJsCode2SessionUrl = weiXinJsCode2SessionUrl;
    }

    @Autowired
    private ApiAccountMng apiAccountMng;
    @Autowired
    private CmsUserMng cmsUserMng;
    @Autowired
    private CmsUserExtMng cmsUserExtMng;
    @Autowired
    private ApiUserLoginMng apiUserLoginMng;
}


package com.bfly.admin;

import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.cms.webservice.entity.ApiRecord;
import com.bfly.cms.webservice.service.ApiAccountMng;
import com.bfly.cms.webservice.service.ApiRecordMng;
import com.bfly.cms.webservice.service.ApiUserLoginMng;
import com.bfly.common.util.AES128Util;
import com.bfly.common.web.LoginUtils;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.Constants;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员后台Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/27 16:57
 */
@Controller
public class AdminController {

    /**
     * 后台首页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/20 10:20
     */
    @GetMapping(value = "/admin/index.html")
    public String index() {
        return "/admin/index.html";
    }

    /**
     * 后台管理员登录
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/2 9:23
     */
    @RequestMapping(value = "/api/admin/user/login")
    public void userLogin(String username, String aesPassword, String appId, String nonceStr, String sign, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = "\"\"";
        String message;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        WebErrors errors = WebErrors.create(request);
        ApiAccount apiAccount = null;
        CmsUser user;
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, appId, nonceStr, sign, username, aesPassword);
        if (!errors.hasErrors()) {
            apiAccount = apiAccountMng.findByAppId(appId);
            errors = ApiValidate.validateApiAccount(request, errors, apiAccount);
            if (errors.hasErrors()) {
                code = ResponseCode.API_CODE_APP_PARAM_ERROR;
            } else {
                //验证签名
                errors = ApiValidate.validateSign(request, errors, apiAccount, sign);
                if (errors.hasErrors()) {
                    code = ResponseCode.API_CODE_SIGN_ERROR;
                }
            }
        } else {
            code = ResponseCode.API_CODE_PARAM_REQUIRED;
        }
        if (errors.hasErrors()) {
            message = errors.getErrors().get(0);
        } else {
            //签名数据不可重复利用
            ApiRecord record = apiRecordMng.findBySign(sign, appId);
            if (record != null) {
                message = Constants.API_MESSAGE_REQUEST_REPEAT;
                code = ResponseCode.API_CODE_REQUEST_REPEAT;
            } else {
                user = cmsUserMng.findByUsername(username);
                if (user != null) {
                    String aesKey = apiAccount.getAesKey();
                    //解密用户输入的密码
                    String encryptPass = AES128Util.decrypt(aesPassword, aesKey, apiAccount.getIvKey());
                    //验证用户密码
                    if (cmsUserMng.isPasswordValid(user.getId(), encryptPass)) {
                        if (user.getSites() == null || user.getSites().size() == 0) {
                            message = "用户没有站群权限";
                            code = ResponseCode.API_CODE_USER_NOT_HAS_PERM;
                            ApiResponse apiResponse = new ApiResponse(request, body, message, code);
                            ResponseUtils.renderApiJson(response, request, apiResponse);
                            return;
                        }
                        //解决会话固定漏洞
                        LoginUtils.logout();
                        //sessionID加密后返回 ,该值作为用户数据交互识别的关键值
                        //调用接口端将该值保存，调用用户数据相关接口传递加密sessionID后的值，服务器端解密后查找用户
                        String sessionKey = request.getSession().getId();
                        apiUserLoginMng.userLogin(username, appId, sessionKey, request, response);
                        //前后台统一登录 api和web
                        LoginUtils.loginShiro(request, response, username);
                        CmsUtils.setUser(request, user);
                        //加密返回
                        body = "\"" + AES128Util.encrypt(sessionKey, aesKey, apiAccount.getIvKey()) + "\"";
                        apiRecordMng.callApiRecord(RequestUtils.getIpAddr(request), appId, "/user/login", sign);
                        message = Constants.API_MESSAGE_SUCCESS;
                    } else {
                        //密码错误
                        message = Constants.API_MESSAGE_PASSWORD_ERROR;
                        code = ResponseCode.API_CODE_PASSWORD_ERROR;
                    }
                } else {
                    //用户不存在
                    message = Constants.API_MESSAGE_USER_NOT_FOUND;
                    code = ResponseCode.API_CODE_USER_NOT_FOUND;
                }
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 后台管理员退出
     *
     * @param sessionKey 会话标识 必选
     * @param appId      appID 必选
     */
    @RequestMapping(value = "/api/admin/user/logout")
    public void userLogout(String appId, String sessionKey, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = "\"\"";
        String message;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            //已登录则退出
            String decryptSessionKey = "";
            if (StringUtils.isNotBlank(appId)) {
                ApiAccount apiAccount = apiAccountMng.findByAppId(appId);
                if (apiAccount != null) {
                    String aesKey = apiAccount.getAesKey();
                    decryptSessionKey = AES128Util.decrypt(sessionKey, aesKey, apiAccount.getIvKey());
                }
            }
            if (StringUtils.isNotBlank(decryptSessionKey)) {
                apiUserLoginMng.userLogout(user.getUsername(), appId, decryptSessionKey);
                LoginUtils.logout();
            }
            message = Constants.API_MESSAGE_SUCCESS;
        } else {
            //用户不存在
            message = Constants.API_MESSAGE_USER_NOT_FOUND;
            code = ResponseCode.API_CODE_USER_NOT_FOUND;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @Autowired
    private ApiAccountMng apiAccountMng;
    @Autowired
    private CmsUserMng cmsUserMng;
    @Autowired
    private ApiUserLoginMng apiUserLoginMng;
    @Autowired
    private ApiRecordMng apiRecordMng;
}

package com.bfly.admin;

import com.bfly.cms.user.entity.CmsAdmin;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.service.CmsAdminService;
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
import com.bfly.core.base.action.BaseAdminController;
import com.bfly.core.exception.ApiException;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 管理员后台Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/27 16:57
 */
@Controller
public class AdminController extends BaseAdminController {

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
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, appId, nonceStr, sign, username, aesPassword);
        if (!errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        ApiRecord record = apiRecordMng.findBySign(sign, appId);
        if (record != null) {
            throw new ApiException("重复请求API", ResponseCode.API_CODE_REQUEST_REPEAT);
        }
        CmsAdmin admin = adminService.findByUsername(username);
        if (admin == null) {
            throw new ApiException("用户名或密码错误", ResponseCode.API_CODE_USER_NOT_FOUND);
        }
        ApiAccount apiAccount = apiAccountMng.findByAppId(appId);
        String aesKey = apiAccount.getAesKey();
        //解密用户输入的密码
        String encryptPass = AES128Util.decrypt(aesPassword, aesKey, apiAccount.getIvKey());
        if (!adminService.isPasswordValid(admin.getId(), encryptPass)) {
            throw new ApiException("用户名或密码错误", ResponseCode.API_CODE_PASSWORD_ERROR);
        }
        getSession().setAttribute(Constants.ADMIN_KEY, admin);

        //sessionID加密后返回 ,该值作为用户数据交互识别的关键值
        //调用接口端将该值保存，调用用户数据相关接口传递加密sessionID后的值，服务器端解密后查找用户
        String sessionKey = request.getSession().getId();
        admin.setSessionId(sessionKey);
        admin.setLastLoginIp(RequestUtils.getIpAddr(request));
        admin.setLastLoginTime(new Date());
        adminService.updateLoginInfo(admin);

        //加密返回
        String body = "\"" + AES128Util.encrypt(sessionKey, aesKey, apiAccount.getIvKey()) + "\"";
        apiRecordMng.callApiRecord(RequestUtils.getIpAddr(request), appId, "/user/login", sign);
        ApiResponse apiResponse = new ApiResponse(request, body, Constants.API_MESSAGE_SUCCESS, code);
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
        CmsUser user = CmsUtils.getUser(request);
        if (user == null) {
            throw new ApiException("用户名不存在", ResponseCode.API_CODE_USER_NOT_FOUND);
        }
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
        String body = "\"\"";
        ApiResponse apiResponse = new ApiResponse(request, body, Constants.API_MESSAGE_SUCCESS, ResponseCode.API_CODE_CALL_SUCCESS);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @Autowired
    private ApiAccountMng apiAccountMng;
    @Autowired
    private ApiUserLoginMng apiUserLoginMng;
    @Autowired
    private ApiRecordMng apiRecordMng;
    @Autowired
    private CmsAdminService adminService;
}

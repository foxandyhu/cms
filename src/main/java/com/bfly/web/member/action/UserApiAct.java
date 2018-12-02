package com.bfly.web.member.action;

import com.bfly.cms.resource.service.ImageSvc;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.entity.Ftp;
import com.bfly.cms.system.entity.CmsConfig;
import com.bfly.cms.system.service.CmsConfigMng;
import com.bfly.cms.user.entity.CmsThirdAccount;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserExt;
import com.bfly.cms.user.service.CmsThirdAccountMng;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.cms.user.service.PwdEncoder;
import com.bfly.cms.user.service.UnifiedUserMng;
import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.cms.webservice.entity.ApiRecord;
import com.bfly.cms.webservice.entity.ApiUserLogin;
import com.bfly.cms.webservice.service.ApiAccountMng;
import com.bfly.cms.webservice.service.ApiRecordMng;
import com.bfly.cms.webservice.service.ApiUserLoginMng;
import com.bfly.common.util.AES128Util;
import com.bfly.common.util.DateUtils;
import com.bfly.common.util.Num62;
import com.bfly.common.web.HttpClientUtil;
import com.bfly.common.web.LoginUtils;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.config.SocialInfoConfig;
import com.bfly.core.Constants;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/front")
public class UserApiAct {

    /**
     * 添加会员用户
     *
     * @param username      用户名   必选
     * @param email         邮箱 非必选
     * @param loginPassword 密码  必选
     * @param realname      真实姓名 非必选
     * @param gender        性别 非必选
     * @param birthdayStr   生日 格式"yyyy-MM-dd" 例如"1980-01-01" 非必选
     * @param phone         电话 非必选
     * @param mobile        手机 非必选
     * @param qq            qq号  非必选
     * @param userImg       用户头像  非必选
     */
    @SignValidate
    @RequestMapping(value = "/user/add")
    public void userAdd(
            String username, String email, String loginPassword,
            String realname, Boolean gender, String birthdayStr,
            String phone, String mobile, String qq, String userImg,
            HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        CmsUser user = null;
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, username, loginPassword);
        if (!errors.hasErrors()) {
            user = cmsUserMng.findByUsername(username);
            if (user == null) {
                String ip = RequestUtils.getIpAddr(request);
                Map<String, String> attrs = RequestUtils.getRequestMap(request, "attr_");
                boolean disabled = false;
                CmsSite site = CmsUtils.getSite(request);
                CmsConfig config = site.getConfig();
                if (config.getMemberConfig().isCheckOn()) {
                    disabled = true;
                }
                CmsUserExt userExt = new CmsUserExt();
                if (StringUtils.isNotBlank(birthdayStr)) {
                    userExt.setBirthday(DateUtils.parseDayStrToDate(birthdayStr));
                }
                userExt.setGender(gender);
                userExt.setMobile(mobile);
                userExt.setPhone(phone);
                userExt.setQq(qq);
                userExt.setRealname(realname);
                userExt.setUserImg(userImg);
                user = cmsUserMng.registerMember(username, email, loginPassword, ip, null, null, disabled, userExt, attrs);
                body = "{\"id\":" + "\"" + user.getId() + "\"}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            } else {
                //用户名已存在
                message = Constants.API_MESSAGE_USERNAME_EXIST;
                code = ResponseCode.API_CODE_USERNAME_EXIST;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }




    /**
     * 获取用户状态API
     *
     * @param username   用户名 必选
     * @param sessionKey 会话标识 必选
     * @param appId      appID 必选
     * @param nonce_str  随机字符串 必选
     * @param sign       签名必选
     */
    @RequestMapping(value = "/user/getStatus")
    public void getUserStatus(
            String username, String sessionKey,
            String appId, String nonce_str, String sign,
            HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_STATUS_FAIL;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        WebErrors errors = WebErrors.create(request);
        ApiAccount apiAccount = null;
        CmsUser user = null;
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, appId,
                nonce_str, sign, username, sessionKey);
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
            message = "\"" + errors.getErrors().get(0) + "\"";
        } else {
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
     * 微信小程序-微信用户登录获取sessionKey和openid API
     *
     * @param js_code    微信小程序登录code 必选
     * @param grant_type 非必选
     * @param appId      appID 必选
     * @param nonce_str  随机字符串 必选
     * @param sign       签名必选
     */
    @RequestMapping(value = "/user/weixinLogin")
    public void weixinAppLogin(
            String js_code, String grant_type,
            String appId, String nonce_str, String sign,
            HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_STATUS_FAIL;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        WebErrors errors = WebErrors.create(request);
        if (StringUtils.isNotBlank(grant_type)) {
            grant_type = "authorization_code";
        }
        ApiAccount apiAccount = null;
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, appId,
                nonce_str, sign, js_code);
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
                Map<String, String> params = new HashMap<String, String>();
                CmsConfig config = configMng.get();
                params.put("appid", config.getWeixinAppId());
                params.put("secret", config.getWeixinAppSecret());
                params.put("js_code", js_code);
                params.put("grant_type", grant_type);
                String result = HttpClientUtil.postParams(socialInfoConfig.getWeixin().getJscode2sessionUrl(),
                        params);
                JSONObject json;
                Object openId = null;
                try {
                    json = new JSONObject(result);
                    openId = json.get("openid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String openid = null;
                if (openId != null) {
                    openid = (String) openId;
                }
                if (StringUtils.isNotBlank(openid)) {
                    body = thirdLoginGetSessionKey(apiAccount, openid, null,
                            Constants.THIRD_SOURCE_WEIXIN_APP, request, response);
                }
                message = Constants.API_MESSAGE_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 第三方登录API
     *
     * @param thirdKey  第三方key 必选
     * @param source    第三方来源 非必选 默认微信小程序
     * @param username  为第三方用户指定创建的用户名
     * @param appId     appID 必选
     * @param nonce_str 随机字符串 必选
     * @param sign      签名必选
     */
    @RequestMapping(value = "/user/thirdLogin")
    public void thirdLoginApi(
            String thirdKey, String source, String username,
            String appId, String nonce_str, String sign,
            HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        String body = "\"\"";
        String message = Constants.API_STATUS_FAIL;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        if (StringUtils.isNotBlank(source)) {
            source = Constants.THIRD_SOURCE_WEIXIN_APP;
        }
        WebErrors errors = WebErrors.create(request);
        ApiAccount apiAccount = null;
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, appId,
                nonce_str, sign, thirdKey);
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
                body = thirdLoginGetSessionKey(apiAccount, thirdKey,
                        username, source, request, response);
                apiRecordMng.callApiRecord(RequestUtils.getIpAddr(request),
                        appId, "/user/thirdLogin", sign);
                message = Constants.API_MESSAGE_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private String thirdLoginGetSessionKey(ApiAccount apiAccount,
                                           String thirdKey, String username, String source,
                                           HttpServletRequest request, HttpServletResponse response) {
        String aesKey = apiAccount.getAesKey();
        thirdKey = pwdEncoder.encodePassword(thirdKey);
        CmsThirdAccount thirdAccount = thirdAccountMng.findByKey(thirdKey);
        if (thirdAccount != null) {
            username = thirdAccount.getUsername();
        } else {
            //用户不存在,则新建用户
            //若是没有传递用户名则随机用户
            if (StringUtils.isBlank(username)) {
                username = getRandomUsername();
            } else {
                //若是传递的用户名存在则随机
                if (userExist(username)) {
                    username = getRandomUsername();
                }
            }
            CmsUserExt userExt = new CmsUserExt();
            //第三方授权来自微信小程序
            if (source.equals(Constants.THIRD_SOURCE_WEIXIN_APP)) {
                String nickName = request.getParameter("nickName");
                String avatarUrl = request.getParameter("avatarUrl");
                String gender = request.getParameter("gender");
                String province = request.getParameter("province");
                String city = request.getParameter("city");
                String country = request.getParameter("country");
                if (StringUtils.isNotBlank(gender)) {
                    if (gender.equals(2)) {
                        userExt.setGender(false);
                    } else if (gender.equals(1)) {
                        userExt.setGender(true);
                    } else {
                        userExt.setGender(null);
                    }
                }
                if (StringUtils.isNotBlank(nickName)) {
                    userExt.setRealname(nickName);
                }
                String comefrom = "";
                if (StringUtils.isNotBlank(country)) {
                    comefrom += country;
                }
                if (StringUtils.isNotBlank(province)) {
                    comefrom += province;
                }
                if (StringUtils.isNotBlank(city)) {
                    comefrom += city;
                }
                userExt.setComefrom(comefrom);
                String imageUrl = "";
                if (StringUtils.isNotBlank(avatarUrl)) {
                    CmsConfig config = configMng.get();
                    CmsSite site = CmsUtils.getSite(request);
                    Ftp ftp = site.getUploadFtp();
                    imageUrl = imgSvc.crawlImg(avatarUrl, config.getContextPath(),
                            config.getUploadToDb(), config.getDbFileUri(),
                            ftp, site.getUploadOss(), site.getUploadPath());
                }
                userExt.setUserImg(imageUrl);
            }
            String ip = RequestUtils.getIpAddr(request);
            boolean disabled = false;
            CmsSite site = CmsUtils.getSite(request);
            CmsConfig config = site.getConfig();
            if (config.getMemberConfig().isCheckOn()) {
                disabled = true;
            }
            CmsUser user = null;
            user = cmsUserMng.registerMember(username, null, thirdKey, ip, null, null, disabled, userExt, null);
            if (user != null) {
                //解决会话固定漏洞
                LoginUtils.logout();
                //绑定新建的用户
                thirdAccount = new CmsThirdAccount();
                thirdAccount.setUsername(username);
                thirdAccount.setAccountKey(thirdKey);
                thirdAccount.setSource(source);
                thirdAccount.setUser(user);
                thirdAccountMng.save(thirdAccount);
                LoginUtils.loginShiro(request, response, username);
                CmsUtils.setUser(request, user);
            }

        }
        String sessionKey = request.getSession().getId();
        apiUserLoginMng.userLogin(username, apiAccount.getAppId(), sessionKey, request, response);
        JSONObject json = new JSONObject();
        try {
            //加密返回
            json.put("sessionKey", AES128Util.encrypt(sessionKey, aesKey, apiAccount.getIvKey()));
            json.put("username", username);
        } catch (Exception e) {
        }
        return json.toString();
    }

    private String getRandomUsername() {
        SimpleDateFormat fomat = new SimpleDateFormat("yyyyMMddHHmmss");
        String username = fomat.format(Calendar.getInstance().getTime()) + RandomStringUtils.random(5, Num62.N10_CHARS);
        ;
        if (userExist(username)) {
            return getRandomUsername();
        } else {
            return username;
        }
    }

    private boolean userExist(String username) {
        if (unifiedUserMng.usernameExist(username)) {
            return true;
        } else {
            return false;
        }
    }

    @Autowired
    private SocialInfoConfig socialInfoConfig;
    @Autowired
    private ApiRecordMng apiRecordMng;
    @Autowired
    private ApiAccountMng apiAccountMng;
    @Autowired
    private CmsUserMng cmsUserMng;
    @Autowired
    private ApiUserLoginMng apiUserLoginMng;
    @Autowired
    private CmsThirdAccountMng thirdAccountMng;
    @Autowired
    private UnifiedUserMng unifiedUserMng;
    @Autowired
    private ImageSvc imgSvc;
    @Autowired
    private CmsConfigMng configMng;
    @Autowired
    private PwdEncoder pwdEncoder;
}


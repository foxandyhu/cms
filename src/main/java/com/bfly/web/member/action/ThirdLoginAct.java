package com.bfly.web.member.action;


import com.bfly.cms.resource.service.ImageSvc;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.entity.Ftp;
import com.bfly.cms.system.entity.CmsConfig;
import com.bfly.cms.system.service.CmsConfigMng;
import com.bfly.cms.user.entity.CmsThirdAccount;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserExt;
import com.bfly.cms.user.entity.UnifiedUser;
import com.bfly.cms.user.service.CmsThirdAccountMng;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.cms.user.service.PwdEncoder;
import com.bfly.cms.user.service.UnifiedUserMng;
import com.bfly.common.util.Num62;
import com.bfly.common.web.*;
import com.bfly.core.config.SocialInfoConfig;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.web.WebErrors;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.WebSubject.Builder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 第三方登录Action
 * 腾讯qq、新浪微博登陆、微信登陆
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 15:51
 */
@Controller
public class ThirdLoginAct extends RenderController {

    @RequestMapping(value = "/public_auth.html")
    public String auth(ModelMap model) {
        return renderPage("member/auth.html", model);
    }

    @RequestMapping(value = "/public_auth_login.html")
    public void authLogin(String key, String source, HttpServletResponse response) throws JSONException {
        if (StringUtils.isNotBlank(source)) {
            switch (source) {
                case CmsThirdAccount.QQ_PLAT:
                    getSession().setAttribute(CmsThirdAccount.QQ_KEY, key);
                    break;
                case CmsThirdAccount.QQ_WEBO_PLAT:
                    getSession().setAttribute(CmsThirdAccount.QQ_WEBO_KEY, key);
                    break;
                case CmsThirdAccount.SINA_PLAT:
                    getSession().setAttribute(CmsThirdAccount.SINA_KEY, key);
                    break;
                default:
            }

        }
        JSONObject json = new JSONObject();
        //库中存放的是加密后的key
        if (StringUtils.isNotBlank(key)) {
            key = pwdEncoder.encodePassword(key);
        }
        CmsThirdAccount account = accountMng.findByKey(key);
        if (account != null) {
            json.put("succ", true);
            //已绑定直接登陆
            loginByKey(key, response);
        } else {
            json.put("succ", false);
        }
        ResponseUtils.renderJson(response, json.toString());
    }

    @GetMapping(value = "/public_bind.html")
    public String bindGet(ModelMap model) {
        return renderPage("member/bind.html", model);
    }

    @PostMapping(value = "/public_bind.html")
    public String bindPost(HttpServletResponse response, String username, String password, ModelMap model) {
        boolean usernameExist = unifiedUserMng.usernameExist(username);
        WebErrors errors = WebErrors.create(getRequest());
        String source = "";
        if (!usernameExist) {
            //用户名不存在
            errors.addErrorCode("error.usernameNotExist");
        } else {
            UnifiedUser u = unifiedUserMng.getByUsername(username);
            boolean passwordValid = unifiedUserMng.isPasswordValid(u.getId(), password);
            if (!passwordValid) {
                errors.addErrorCode("error.passwordInvalid");
            } else {
                //获取用户来源
                String openId = (String) getSession().getAttribute(CmsThirdAccount.QQ_KEY);
                String uid = (String) getSession().getAttribute(CmsThirdAccount.SINA_KEY);
                String weboOpenId = (String) getSession().getAttribute(CmsThirdAccount.QQ_WEBO_KEY);
                String weixinOpenId = (String) getSession().getAttribute(CmsThirdAccount.WEIXIN_KEY);
                if (StringUtils.isNotBlank(openId)) {
                    source = CmsThirdAccount.QQ_PLAT;
                } else if (StringUtils.isNotBlank(uid)) {
                    source = CmsThirdAccount.SINA_PLAT;
                } else if (StringUtils.isNotBlank(weboOpenId)) {
                    source = CmsThirdAccount.QQ_WEBO_PLAT;
                } else if (StringUtils.isNotBlank(weixinOpenId)) {
                    source = CmsThirdAccount.WEIXIN_PLAT;
                }
                //提交登录并绑定账号
                loginByUsername(username, response);
            }
        }
        if (errors.hasErrors()) {
            errors.toModel(model);
            model.addAttribute("success", false);
        } else {
            model.addAttribute("success", true);
        }
        model.addAttribute("source", source);
        return renderPage("member/bind.html", model);
    }

    @RequestMapping(value = "/public_bind_username.html")
    public String bindUsernamePost(String username, String nickname, Integer sex, String province, String city, String headimgurl, HttpServletResponse response, ModelMap model) {
        WebErrors errors = WebErrors.create(getRequest());
        String source = "";
        if (StringUtils.isBlank(username)) {
            //用户名为空
            errors.addErrorCode("error.usernameRequired");
        } else {
            boolean usernameExist = unifiedUserMng.usernameExist(username);
            if (usernameExist) {
                //用户名存在
                errors.addErrorCode("error.usernameExist");
            } else {
                //获取用户来源
                String openId = (String) getSession().getAttribute(CmsThirdAccount.QQ_KEY);
                String uid = (String) getSession().getAttribute(CmsThirdAccount.SINA_KEY);
                String weboOpenId = (String) getSession().getAttribute(CmsThirdAccount.QQ_WEBO_KEY);
                String weixinOpenId = (String) getSession().getAttribute(CmsThirdAccount.WEIXIN_KEY);
                //(获取到登录授权key后可以注册用户)
                CmsUserExt ext = new CmsUserExt();
                if (StringUtils.isNotBlank(weixinOpenId)) {
                    String comefrom = "";
                    if (StringUtils.isNotBlank(province)) {
                        comefrom += province;
                    }
                    if (StringUtils.isNotBlank(city)) {
                        comefrom += city;
                    }
                    ext.setComefrom(comefrom);
                    if (StringUtils.isNotBlank(nickname)) {
                        ext.setRealname(nickname);
                    }
                    if (sex != null) {
                        if (sex.equals(1)) {
                            ext.setGender(true);
                        } else if (sex.equals(2)) {
                            ext.setGender(false);
                        }
                    }
                    if (StringUtils.isNotBlank(headimgurl)) {
                        CmsConfig config = cmsConfigMng.get();
                        CmsSite site = getSite();
                        Ftp ftp = site.getUploadFtp();
                        String imageUrl = imgSvc.crawlImg(headimgurl, config.getContextPath(), config.getUploadToDb(), config.getDbFileUri(), ftp, site.getUploadOss(), site.getUploadPath());
                        ext.setUserImg(imageUrl);
                    }
                }
                if (StringUtils.isNotBlank(openId) || StringUtils.isNotBlank(uid) || StringUtils.isNotBlank(weboOpenId) || StringUtils.isNotBlank(weixinOpenId)) {
                    //初始设置密码同用户名
                    cmsUserMng.registerMember(username, null, username, RequestUtils.getIpAddr(getRequest()), null, null, false, ext, null);
                }
                if (StringUtils.isNotBlank(openId)) {
                    source = CmsThirdAccount.QQ_PLAT;
                } else if (StringUtils.isNotBlank(uid)) {
                    source = CmsThirdAccount.SINA_PLAT;
                } else if (StringUtils.isNotBlank(weboOpenId)) {
                    source = CmsThirdAccount.QQ_WEBO_PLAT;
                } else if (StringUtils.isNotBlank(weixinOpenId)) {
                    source = CmsThirdAccount.WEIXIN_PLAT;
                }
                //提交登录并绑定账号
                loginByUsername(username, response);
            }
        }
        if (errors.hasErrors()) {
            errors.toModel(model);
            model.addAttribute("success", false);
        } else {
            model.addAttribute("success", true);
        }
        model.addAttribute("source", source);
        return renderPage("member/bind.html", model);
    }


    @RequestMapping(value = "/weixin_login.html")
    public String weixinLogin() {
        CmsSite site = getSite();
        String codeUrl;
        CmsConfig config = cmsConfigMng.get();
        String authUrl = "/weixin_auth.html";
        String redirectUri = site.getUrlPrefixWithNoDefaultPort();
        if (StringUtils.isNotBlank(site.getContextPath())) {
            redirectUri += site.getContextPath();
        }
        redirectUri += authUrl;
        codeUrl = socialInfoConfig.getWeixin().getAuth().getCodeUrl() + "&appid=" + config.getWeixinLoginId() + "&redirect_uri=" + redirectUri
                + "&state=" + RandomStringUtils.random(10, Num62.N36_CHARS) + "#wechat_redirect";
        return "redirect:" + codeUrl;
    }

    @RequestMapping(value = "/weixin_auth.html")
    public String weixinAuth(String code, HttpServletResponse response, ModelMap model) {
        CmsConfig config = cmsConfigMng.get();
        String tokenUrl = socialInfoConfig.getWeixin().getAuth().getAccessTokenUrl() + "&appid=" + config.getWeixinLoginId() + "&secret=" + config.getWeixinLoginSecret() + "&code=" + code;
        JSONObject json = null;
        try {
            //获取openid和access_token
            json = new JSONObject(HttpClientUtil.getInstance().get(tokenUrl));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        if (json != null) {
            try {
                String openid = json.getString("openid");
                String accessToken = json.getString("access_token");
                if (StringUtils.isNotBlank(openid) && StringUtils.isNotBlank(accessToken)) {
                    //库中存储的是加密后的
                    String md5OpenId = pwdEncoder.encodePassword(openid);
                    CmsThirdAccount account = accountMng.findByKey(md5OpenId);
                    if (account != null) {
                        //已绑定直接登陆
                        loginByKey(md5OpenId, response);
                        return renderPage("index/index.html");
                    }
                    String userUrl = socialInfoConfig.getWeixin().getAuth().getUserInfoUrl() + "&access_token=" + accessToken + "&openid=" + openid;
                    try {
                        //获取用户信息
                        json = new JSONObject(HttpClientUtil.getInstance().get(userUrl));
                        String nickname = (String) json.get("nickname");
                        Integer sex = (Integer) json.get("sex");
                        String province = (String) json.get("province");
                        String city = (String) json.get("city");
                        String headimgurl = (String) json.get("headimgurl");
                        model.addAttribute("nickname", nickname);
                        model.addAttribute("sex", sex);
                        model.addAttribute("province", province);
                        model.addAttribute("city", city);
                        model.addAttribute("headimgurl", headimgurl);
                        getSession().setAttribute(CmsThirdAccount.WEIXIN_KEY, openid);
                        return renderPage("member/bind.html", model);
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                WebErrors errors = WebErrors.create(getRequest());
                String errcode = null;
                try {
                    errcode = json.getString("errcode");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                if (StringUtils.isNotBlank(errcode)) {
                    errors.addErrorCode("weixin.auth.fail");
                }
                return renderErrorPage(model, errors);
            }
        }
        return renderMessagePage(model, "weixin.auth.succ");
    }

    /**
     * 判断用户是否登录
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 15:39
     */
    @RequestMapping(value = "/sso/authenticate.html")
    public void authenticate(String username, String sessionId, HttpServletResponse response) {
        CmsUser user = cmsUserMng.findByUsername(username);
        if (user != null && sessionId != null) {
            String userSessionId = user.getSessionId();
            if (StringUtils.isNotBlank(userSessionId)) {
                if (userSessionId.equals(sessionId)) {
                    ResponseUtils.renderJson(response, "true");
                }
            } else {
                ResponseUtils.renderJson(response, "false");
            }
        }
    }

    @RequestMapping(value = "/sso/login.html")
    public void loginSso(String username, String sessionId, String ssoLogout, HttpServletResponse response) {
        CmsUser user = getUser();
        if (StringUtils.isNotBlank(username)) {
            JSONObject object = new JSONObject();
            try {
                if (user == null) {
                    //未登录，其他地方已经登录，则登录自身
                    CmsConfig config = cmsConfigMng.get();
                    List<String> authenticateUrls = config.getSsoAuthenticateUrls();
                    String success = authenticate(username, sessionId, authenticateUrls);
                    if ("true".equals(success)) {
                        LoginUtils.loginShiro(getRequest(), response, username);
                        user = cmsUserMng.findByUsername(username);
                        if (user != null) {
                            cmsUserMng.updateLoginInfo(user.getId(), null, null, sessionId);
                        }
                        object.put("result", "login");
                    }
                } else if (StringUtils.isNotBlank(ssoLogout) && "true".equals(ssoLogout)) {
                    LoginUtils.logout();
                    object.put("result", "logout");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ResponseUtils.renderJson(response, object.toString());
        }
    }

    private String authenticate(String username, String sessionId, List<String> authenticateUrls) {
        String result = "false";
        for (String url : authenticateUrls) {
            result = authenticate(username, sessionId, url);
            if ("true".equals(result)) {
                break;
            }
        }
        return result;
    }

    private String authenticate(String username, String sessionId, String authenticateUrl) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("sessionId", sessionId);
        String success = "false";
        try {
            success = HttpRequestUtil.request(authenticateUrl, params, "post", "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 用户名登陆,绑定用户名和第三方账户key
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 15:28
     */
    private void loginByUsername(String username, HttpServletResponse response) {
        String openId = (String) getSession().getAttribute(CmsThirdAccount.QQ_KEY);
        String uid = (String) getSession().getAttribute(CmsThirdAccount.SINA_KEY);
        String weboOpenId = (String) getSession().getAttribute(CmsThirdAccount.QQ_WEBO_KEY);
        String weixinOpenId = (String) getSession().getAttribute(CmsThirdAccount.WEIXIN_KEY);
        if (StringUtils.isNotBlank(openId)) {
            loginShiro(response, username);
            //绑定账户
            bind(username, openId, CmsThirdAccount.QQ_PLAT);
        }
        if (StringUtils.isNotBlank(uid)) {
            loginShiro(response, username);
            //绑定账户
            bind(username, uid, CmsThirdAccount.SINA_PLAT);
        }
        if (StringUtils.isNotBlank(weboOpenId)) {
            loginShiro(response, username);
            //绑定账户
            bind(username, weboOpenId, CmsThirdAccount.QQ_WEBO_PLAT);
        }
        if (StringUtils.isNotBlank(weixinOpenId)) {
            loginShiro(response, username);
            //绑定账户
            bind(username, weixinOpenId, CmsThirdAccount.WEIXIN_PLAT);
        }
    }

    /**
     * 已绑定用户key登录
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 15:28
     */
    private void loginByKey(String key, HttpServletResponse response) {
        CmsThirdAccount account = accountMng.findByKey(key);
        if (StringUtils.isNotBlank(key) && account != null) {
            String username = account.getUsername();
            loginShiro(response, username);
        }
    }


    private void loginShiro(HttpServletResponse response, String username) {
        PrincipalCollection principals = new SimplePrincipalCollection(username, username);
        Builder builder = new WebSubject.Builder(getRequest(), response);
        builder.principals(principals);
        builder.authenticated(true);
        WebSubject subject = builder.buildWebSubject();
        ThreadContext.bind(subject);
    }

    private void bind(String username, String openId, String source) {
        CmsThirdAccount account = accountMng.findByKey(openId);
        if (account == null) {
            account = new CmsThirdAccount();
            account.setUsername(username);
            //第三方账号唯一码加密存储 防冒名登录
            openId = pwdEncoder.encodePassword(openId);
            account.setAccountKey(openId);
            account.setSource(source);
            account.setUser(cmsUserMng.findByUsername(username));
            accountMng.save(account);
        }
    }

    @Autowired
    private UnifiedUserMng unifiedUserMng;
    @Autowired
    private CmsUserMng cmsUserMng;
    @Autowired
    private CmsThirdAccountMng accountMng;
    @Autowired
    private PwdEncoder pwdEncoder;
    @Autowired
    private CmsConfigMng cmsConfigMng;
    @Autowired
    private ImageSvc imgSvc;
    @Autowired
    private SocialInfoConfig socialInfoConfig;
}

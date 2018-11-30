package com.bfly.cms.weixin.action;


import com.bfly.cms.funds.service.CmsUserAccountMng;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.system.entity.CmsConfigContentCharge;
import com.bfly.cms.system.entity.MemberConfig;
import com.bfly.cms.system.service.CmsConfigContentChargeMng;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.common.web.HttpClientUtil;
import com.bfly.common.web.ResponseUtils;
import com.bfly.config.SocialInfoConfig;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 微信授权登陆
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 16:16
 */
@Controller
public class WeiXinAuthAct extends RenderController {

    private static final Logger log = LoggerFactory.getLogger(WeiXinAuthAct.class);

    /**
     * 进入微信授权登录二维码页面(需要先登陆在进入扫码)
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/28 16:16
     */
    @GetMapping(value = "/member/weixin_auth_enter.html")
    public String weixinAuthEnter(ModelMap model) {
        CmsSite site = getSite();
        String url = "/member/weixin_auth.html";
        if (StringUtils.isNotBlank(site.getContextPath())) {
            url = site.getUrlPrefixWithNoDefaultPort() + site.getContextPath() + url;
        } else {
            url = site.getUrlPrefixWithNoDefaultPort() + url;
        }
        model.addAttribute("url", url);
        return renderPage("member/weixin_auth_enter.html", model);
    }

    /**
     * 进入微信授权登录二维码页面(需要先登陆在进入扫码)
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 15:45
     */
    @GetMapping(value = "/member/weixin_auth.html")
    public String weixinAuth(ModelMap model) {
        CmsSite site = getSite();
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        if (!mcfg.isMemberOn()) {
            return renderMessagePage(model, "member.memberClose");
        }
        if (getUser() == null) {
            return renderLoginPage(model);
        }
        String codeUrl;
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        String redirectUri = "/member/weixin_auth_call.html";
        if (StringUtils.isNotBlank(site.getContextPath())) {
            redirectUri = site.getUrlPrefixWithNoDefaultPort() + site.getContextPath() + redirectUri;
        } else {
            redirectUri = site.getUrlPrefixWithNoDefaultPort() + redirectUri;
        }
        codeUrl = socialInfoConfig.getWeixin().getAuth().getCodeUrl() + "?appid=" + config.getWeixinAppId() + "&redirect_uri=" + redirectUri + "&response_type=code&scope=snsapi_userinfo&state=jeecms#wechat_redirect";
        model.addAttribute("codeUrl", codeUrl);
        return renderPage("member/weixin_auth.html", model);
    }


    /**
     * 微信授权网页登陆回调地址
     * 设置用户账户的微信openid,用于提现，企业打款接口
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 15:48
     */
    @GetMapping(value = "/member/weixin_auth_call.html")
    public String weixinAuthCall(String code, ModelMap model) {
        CmsSite site = getSite();
        CmsUser user = getUser();
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        if (!mcfg.isMemberOn()) {
            return renderMessagePage(model, "member.memberClose");
        }
        if (user == null) {
            return renderLoginPage(model);
        }
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        String tokenUrl = socialInfoConfig.getWeixin().getAuth().getCodeUrl() + "&appid=" + config.getWeixinAppId() + "&secret=" + config.getWeixinSecret() + "&code=" + code;
        JSONObject json = null;
        try {
            json = new JSONObject(HttpClientUtil.getInstance().get(tokenUrl));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        if (json != null) {
            try {
                String openid = json.getString("openid");
                if (StringUtils.isNotBlank(openid)) {
                    userAccountMng.updateWeiXinOpenId(user.getId(), openid);
                }
            } catch (JSONException e) {
                WebErrors errors = WebErrors.create(getRequest());
                String errcode = null;
                try {
                    errcode = json.getString("errcode");
                } catch (JSONException e1) {
                    log.error("read json data error",e1);
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
     * 获取微信用户openid
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 15:49
     */
    @RequestMapping(value = "/common/getOpenId.html", method = RequestMethod.GET)
    public void getWeixinOpenId(String rediretUrl, HttpServletResponse response) {
        CmsSite site = getSite();
        String codeUrl;
        JSONObject json = new JSONObject();
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        String redirectUri = "/common/setOpenId.html";
        if (StringUtils.isNotBlank(site.getContextPath())) {
            redirectUri = site.getUrlPrefixWithNoDefaultPort() + site.getContextPath() + redirectUri;
        } else {
            redirectUri = site.getUrlPrefixWithNoDefaultPort() + redirectUri;
        }
        codeUrl = socialInfoConfig.getWeixin().getAuth().getCodeUrl() + "?appid=" + config.getWeixinAppId() + "&redirect_uri=" + redirectUri + "&response_type=code&scope=snsapi_base&state=jeecms#wechat_redirect";
        try {
            json.put("url", codeUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getSession().setAttribute("weChatAuthRediretUrl", rediretUrl);
        ResponseUtils.renderJson(response, json.toString());
    }

    /**
     * 静默模式获取用户openid
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 15:50
     */
    @GetMapping(value = "/common/setOpenId.html")
    public void setOpenId(String code,HttpServletResponse response) {
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        String tokenUrl = socialInfoConfig.getWeixin().getAuth().getCodeUrl() + "&appid=" + config.getWeixinAppId() + "&secret=" + config.getWeixinSecret() + "&code=" + code;
        JSONObject json = null;
        try {
            json = new JSONObject(HttpClientUtil.getInstance().get(tokenUrl));
        } catch (JSONException e2) {
            log.error("get token ->", e2.getMessage());
        }
        if (json != null) {
            try {
                String openid = json.getString("openid");
                if (StringUtils.isNotBlank(openid)) {
                    getSession().setAttribute("wxopenid", openid);
                }
            } catch (JSONException e) {
                String errcode;
                try {
                    errcode = json.getString("errcode");
                    log.error("get open id ->", errcode);
                } catch (JSONException e1) {
                    log.error("get open id ->", e1);
                }
            }
        }
        String weChatAuthRediretUrl = (String) getSession().getAttribute("weChatAuthRediretUrl");
        if (StringUtils.isNotBlank(weChatAuthRediretUrl)) {
            try {
                response.sendRedirect(weChatAuthRediretUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Autowired
    private CmsConfigContentChargeMng configContentChargeMng;
    @Autowired
    private CmsUserAccountMng userAccountMng;
    @Autowired
    private SocialInfoConfig socialInfoConfig;
}

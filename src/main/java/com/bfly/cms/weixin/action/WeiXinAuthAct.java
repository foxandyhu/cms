package com.bfly.cms.weixin.action;


import static com.bfly.core.Constants.TPLDIR_MEMBER;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfly.config.SocialInfoConfig;
import com.bfly.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bfly.cms.system.entity.CmsConfigContentCharge;
import com.bfly.cms.system.service.CmsConfigContentChargeMng;
import com.bfly.common.web.HttpClientUtil;
import com.bfly.common.web.ResponseUtils;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.system.entity.MemberConfig;
import com.bfly.cms.funds.service.CmsUserAccountMng;
import com.bfly.core.web.util.CmsUtils;
import com.bfly.core.web.util.FrontUtils;


/**
 * @author andy_hulibo@163.com
 * @date 2018/11/28 16:16
 * 微信授权登陆
 */
@Controller
public class WeiXinAuthAct {

    public static final String MEMBER_WEIXIN_AUTH = "tpl.weixinAuth";
    public static final String MEMBER_WEIXIN_AUTH_ENTER = "tpl.weixinAuthEnter";
    private static final Logger log = LoggerFactory.getLogger(WeiXinAuthAct.class);

    /**
     * 进入微信授权登录二维码页面(需要先登陆在进入扫码)
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/28 16:16
     */
    @RequestMapping(value = "/member/weixin_auth_enter.html", method = RequestMethod.GET)
    public String weixinAuthEnter(HttpServletRequest request,
                                  HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        String url = "/member/weixin_auth.html";
        if (StringUtils.isNotBlank(site.getContextPath())) {
            url = site.getUrlPrefixWithNoDefaultPort() + site.getContextPath() + url;
        } else {
            url = site.getUrlPrefixWithNoDefaultPort() + url;
        }
        model.addAttribute("url", url);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, MEMBER_WEIXIN_AUTH_ENTER);
    }

    //进入微信授权登录二维码页面(需要先登陆在进入扫码)
    @RequestMapping(value = "/member/weixin_auth.html", method = RequestMethod.GET)
    public String weixinAuth(HttpServletRequest request,
                             HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        String codeUrl;
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        String redirect_uri = "/member/weixin_auth_call.html";
        if (StringUtils.isNotBlank(site.getContextPath())) {
            redirect_uri = site.getUrlPrefixWithNoDefaultPort() + site.getContextPath() + redirect_uri;
        } else {
            redirect_uri = site.getUrlPrefixWithNoDefaultPort() + redirect_uri;
        }
        codeUrl = socialInfoConfig.getWeixin().getAuth().getCodeUrl() + "?appid=" + config.getWeixinAppId() + "&redirect_uri=" + redirect_uri
                + "&response_type=code&scope=snsapi_userinfo&state=jeecms#wechat_redirect";
        model.addAttribute("codeUrl", codeUrl);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, MEMBER_WEIXIN_AUTH);
    }


    /**
     * 微信授权网页登陆回调地址
     * 设置用户账户的微信openid,用于提现，企业打款接口
     *
     * @param code
     */
    @RequestMapping(value = "/member/weixin_auth_call.html", method = RequestMethod.GET)
    public String weixinAuthCall(String code, HttpServletRequest request,
                                 HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        String tokenUrl = socialInfoConfig.getWeixin().getAuth().getCodeUrl() + "&appid=" + config.getWeixinAppId() + "&secret=" + config.getWeixinSecret() + "&code=" + code;
        JSONObject json = null;
        try {
            json = new JSONObject(HttpClientUtil.getInstance().get(tokenUrl));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        FrontUtils.frontData(request, model, site);
        if (json != null) {
            try {
                String openid = json.getString("openid");
                if (StringUtils.isNotBlank(openid)) {
                    userAccountMng.updateWeiXinOpenId(user.getId(), openid);
                }
            } catch (JSONException e) {
                WebErrors errors = WebErrors.create(request);
                String errcode = null;
                try {
                    errcode = json.getString("errcode");
                } catch (JSONException e1) {
                }
                if (StringUtils.isNotBlank(errcode)) {
                    errors.addErrorCode("weixin.auth.fail");
                }
                return FrontUtils.showError(request, response, model, errors);
            }
        }
        return FrontUtils.showMessage(request, model, "weixin.auth.succ");
    }


    //获取微信用户openid
    @RequestMapping(value = "/common/getOpenId.html", method = RequestMethod.GET)
    public void getWeixinOpenId(String rediretUrl,
                                HttpServletRequest request,
                                HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        String codeUrl = "";
        JSONObject json = new JSONObject();
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        String redirect_uri = "/common/setOpenId.html";
        if (StringUtils.isNotBlank(site.getContextPath())) {
            redirect_uri = site.getUrlPrefixWithNoDefaultPort() + site.getContextPath() + redirect_uri;
        } else {
            redirect_uri = site.getUrlPrefixWithNoDefaultPort() + redirect_uri;
        }
        codeUrl = socialInfoConfig.getWeixin().getAuth().getCodeUrl() + "?appid=" + config.getWeixinAppId() + "&redirect_uri=" + redirect_uri
                + "&response_type=code&scope=snsapi_base&state=jeecms#wechat_redirect";
        try {
            json.put("url", codeUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("weChatAuthRediretUrl", rediretUrl);
        ResponseUtils.renderJson(response, json.toString());
    }

    /**
     * 静默模式获取用户openid
     *
     * @param code
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/common/setOpenId.html", method = RequestMethod.GET)
    public void setOpenId(String code, HttpServletRequest request,
                          HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        String tokenUrl = socialInfoConfig.getWeixin().getAuth().getCodeUrl() + "&appid=" + config.getWeixinAppId() + "&secret=" + config.getWeixinSecret() + "&code=" + code;
        JSONObject json = null;
        try {
            json = new JSONObject(HttpClientUtil.getInstance().get(tokenUrl));
        } catch (JSONException e2) {
            log.error("get token ->", e2.getMessage());
        }
        FrontUtils.frontData(request, model, site);
        if (json != null) {
            try {
                String openid = json.getString("openid");
                if (StringUtils.isNotBlank(openid)) {
                    request.getSession().setAttribute("wxopenid", openid);
                }
            } catch (JSONException e) {
                String errcode = null;
                try {
                    errcode = json.getString("errcode");
                    log.error("get open id ->", errcode);
                } catch (JSONException e1) {
                }
            }
        }
        String weChatAuthRediretUrl = (String) request.getSession().getAttribute("weChatAuthRediretUrl");
        if (StringUtils.isNotBlank(weChatAuthRediretUrl)) {
            try {
                response.sendRedirect(weChatAuthRediretUrl);
            } catch (IOException e) {
                //e.printStackTrace();
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

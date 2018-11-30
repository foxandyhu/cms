package com.bfly.cms.member.action;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.system.service.ConfigMng;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.service.UnifiedUserMng;
import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.cms.webservice.entity.ApiUserLogin;
import com.bfly.cms.webservice.service.ApiAccountMng;
import com.bfly.cms.webservice.service.ApiUserLoginMng;
import com.bfly.common.util.AES128Util;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.annotation.Token;
import com.bfly.core.base.action.RenderController;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

import static org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/28 16:18
 */
@Controller
public class CasLoginAct extends RenderController {

    private Logger logger= LoggerFactory.getLogger(CasLoginAct.class);

    @Token(save = true)
    @GetMapping(value = "/login.html")
    public String input(String returnUrl, ModelMap model) {
        CmsSite site = getSite();
        Integer errorTimes = configMng.getConfigLogin().getErrorTimes();
        model.addAttribute("errorTimes", errorTimes);
        model.addAttribute("site", site.getSolutionPath());
        if (StringUtils.isBlank(returnUrl)) {
            getSession().setAttribute("loginSource", null);
        }
        Object source = getSession().getAttribute("loginSource");
        if (source != null) {
            String loginSource = (String) source;
            model.addAttribute("loginSource", loginSource);
        }
        return renderPage("member/login_input.html", model);
    }

    @Token(remove = true)
    @PostMapping(value = "/login.html")
    public String submit(String username, ModelMap model) {
        Object error = getRequest().getAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("errorRemaining", unifiedUserMng.errorRemaining(username));
        }
        getSession().setAttribute("loginSource", null);
        return renderPage("member/login_input.html", model);
    }

    @PostMapping(value = "/adminLogin.html")
    public void adminLogin(HttpServletResponse response) {
        CmsUser user = getUser();
        ApiAccount apiAccount = apiAccountMng.findByDefault();
        JSONObject json = new JSONObject();
        String encryptSessionKey;
        //登陆API后台
        if (user != null && apiAccount != null) {
            String aesKey = apiAccount.getAesKey();
            String sessionKey = getSession().getId();
            apiUserLoginMng.userLogin(user.getUsername(), apiAccount.getAppId(), sessionKey, getRequest(), response);
            try {
                encryptSessionKey = AES128Util.encrypt(sessionKey, aesKey, apiAccount.getIvKey());
                json.put("sessionKey", encryptSessionKey);
                json.put("userName", user.getUsername());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ResponseUtils.renderJson(response, json.toString());
    }

    @PostMapping(value = "/adminLogout.html")
    public void adminLogout(String userName, String sessionKey, HttpServletResponse response) {
        ApiAccount apiAccount = apiAccountMng.findByDefault();
        JSONObject json = new JSONObject();
        //登出API后台
        if (StringUtils.isNotBlank(userName) &&
                StringUtils.isNotBlank(sessionKey) && apiAccount != null) {
            String decryptSessionKey = "";
            try {
                decryptSessionKey = AES128Util.decrypt(sessionKey, apiAccount.getAesKey(), apiAccount.getIvKey());
            } catch (Exception e) {
                logger.error("decrypt key error",e);
            }
            //检查是否登陆
            if (StringUtils.isNotBlank(decryptSessionKey)) {
                ApiUserLogin userLogin = apiUserLoginMng.findUserLogin(userName, decryptSessionKey);
                if (userLogin != null) {
                    apiUserLoginMng.userLogout(userName, null, decryptSessionKey);
                }
            }
        }
        ResponseUtils.renderJson(response, json.toString());
    }

    @RequestMapping(value = "/login_csi.html")
    public String csi(ModelMap model) {
        model.putAll(RequestUtils.getQueryParams(getRequest()));
        return renderPage("csi/login_csi.html", model);
    }

    @Autowired
    private UnifiedUserMng unifiedUserMng;
    @Autowired
    private ConfigMng configMng;
    @Autowired
    private ApiAccountMng apiAccountMng;
    @Autowired
    private ApiUserLoginMng apiUserLoginMng;
}

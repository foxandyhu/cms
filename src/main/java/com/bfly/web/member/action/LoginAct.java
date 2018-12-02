package com.bfly.web.member.action;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.system.service.ConfigMng;
import com.bfly.cms.user.service.UnifiedUserMng;
import com.bfly.common.web.RequestUtils;
import com.bfly.core.annotation.Token;
import com.bfly.core.base.action.RenderController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;

/**
 * 会员中心登录Controller
 *
 * @author andy_hulibo@163.com
 * @author andy_hulibo@163.com
 * @date 2018/12/2 10:23
 * @date 2018/11/28 16:18
 */
@Controller
public class LoginAct extends RenderController {

    /**
     * 跳转到会员中心登录页面
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/1 11:27
     */
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

    @RequestMapping(value = "/login_csi.html")
    public String csi(ModelMap model) {
        model.putAll(RequestUtils.getQueryParams(getRequest()));
        return renderPage("csi/login_csi.html", model);
    }

    @Autowired
    private UnifiedUserMng unifiedUserMng;
    @Autowired
    private ConfigMng configMng;
}

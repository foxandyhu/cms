package com.bfly.cms.member.action;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.system.entity.CmsConfig;
import com.bfly.cms.system.entity.CmsConfigItem;
import com.bfly.cms.system.entity.EmailSender;
import com.bfly.cms.system.entity.MemberConfig;
import com.bfly.cms.system.service.CmsConfigItemMng;
import com.bfly.cms.system.service.ConfigMng;
import com.bfly.cms.system.service.MessageTemplate;
import com.bfly.cms.user.entity.CmsUserExt;
import com.bfly.cms.user.entity.UnifiedUser;
import com.bfly.cms.user.service.CmsUserExtMng;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.cms.user.service.UnifiedUserMng;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.common.web.springmvc.MessageResolver;
import com.bfly.core.annotation.Token;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.FrontUtils;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 前台会员注册Action
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 17:28
 */
@Controller
public class RegisterAct extends RenderController {
    private static final Logger log = LoggerFactory.getLogger(RegisterAct.class);

    /**
     * 跳转注册页面
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 17:10
     */
    @Token(save = true)
    @GetMapping(value = "/register.html")
    public String input(ModelMap model) {
        CmsSite site = getSite();
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return renderMessagePage(model, "没有开启会员功能");
        }
        // 没有开启会员注册
        if (!mcfg.isRegisterOn()) {
            return renderMessagePage(model, "没有开启会员注册");
        }
        List<CmsConfigItem> items = cmsConfigItemMng.getList(site.getConfig().getId(), CmsConfigItem.CATEGORY_REGISTER);
        CmsConfig config = site.getConfig();
        Integer validateType = 0;
        if (config.getValidateType() != null) {
            validateType = config.getValidateType();
        }
        model.addAttribute("mcfg", mcfg);
        model.addAttribute("items", items);
        //验证类型  0:无 1：邮件验证    2： SMS短信验证
        model.addAttribute("validateType", validateType);
        return renderPage("member/register.html", model);
    }

    /**
     * 提交注册信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 17:10
     */
    @Token(remove = true)
    @PostMapping(value = "/register.html")
    public String submit(String username, String email, String loginPassword, CmsUserExt userExt, String captcha, String nextUrl, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        CmsSite site = getSite();
        CmsConfig config = getSite().getConfig();
        WebErrors errors;
        // 判断是否开启的是SMS验证
        if (config.getValidateType() == 2) {
            errors = validateSmsSubmit(userExt.getMobile(), username, loginPassword, captcha);
        } else {
            errors = validateSubmit(username, email, loginPassword, captcha, site, request);
        }
        boolean disabled = false;
        if (config.getMemberConfig().isCheckOn()) {
            disabled = true;
        }
        if (errors.hasErrors()) {
            return renderErrorPage(model, errors);
        }
        String ip = RequestUtils.getIpAddr(request);
        Map<String, String> attrs = RequestUtils.getRequestMap(request, "attr_");
        // 邮件验证
        if (config.getValidateType() == 1) {
            EmailSender sender = configMng.getEmailSender();
            MessageTemplate msgTpl = configMng.getRegisterMessageTemplate();
            if (sender == null) {
                // 邮件服务器没有设置好
                model.addAttribute("status", 4);
            } else if (msgTpl == null) {
                // 邮件模板没有设置好
                model.addAttribute("status", 5);
            } else {
                try {
                    cmsUserMng.registerMember(username, email, loginPassword, ip, null, disabled, userExt, attrs, false, sender, msgTpl);
                    model.addAttribute("status", 0);
                } catch (UnsupportedEncodingException e) {
                    model.addAttribute("status", 100);
                    model.addAttribute("message", e.getMessage());
                    log.error("send email exception.", e);
                } catch (MessagingException e) {
                    model.addAttribute("status", 101);
                    model.addAttribute("message", e.getMessage());
                    log.error("send email exception.", e);
                }
            }
            log.info("member register success. username={}", username);
            if (!StringUtils.isBlank(nextUrl)) {
                return redirect(nextUrl);
            }
            return renderPage("member/register_result.html", model);
        }
        cmsUserMng.registerMember(username, email, loginPassword, ip, null, null, disabled, userExt, attrs);
        log.info("member register success. username={}", username);
        model.addAttribute("success", true);
        return renderPagination("member/login_input.html", model);
    }

    @GetMapping(value = "/active.html")
    public String active(String key, ModelMap model) throws IOException {
        String username = getRequest().getParameter("username");
        WebErrors errors = validateActive(username, key);
        if (errors.hasErrors()) {
            return renderErrorPage(model, errors);
        }
        unifiedUserMng.active(username, key);
        return renderPage("member/register_active_success.html", model);
    }

    /**
     * 用户名唯一性校验
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 17:26
     */
    @RequestMapping(value = "/username_unique.html")
    public void usernameUnique(HttpServletResponse response) {
        String username = getRequest().getParameter("username");
        if (StringUtils.isBlank(username)) {
            ResponseUtils.renderJson(response, "false");
            return;
        }
        CmsConfig config = getSite().getConfig();
        // 保留字检查不通过，返回false。
        if (!config.getMemberConfig().checkUsernameReserved(username)) {
            ResponseUtils.renderJson(response, "false");
            return;
        }
        // 用户名存在，返回false。
        if (unifiedUserMng.usernameExist(username)) {
            ResponseUtils.renderJson(response, "false");
            return;
        }
        ResponseUtils.renderJson(response, "true");
    }

    /**
     * 手机号码唯一性校验
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 17:27
     */
    @RequestMapping(value = "/mobilePhone_unique.html")
    public void mobilePhoneUnique(HttpServletResponse response) {
        String mobilePhone = getRequest().getParameter("mobile");
        if (StringUtils.isBlank(mobilePhone)) {
            ResponseUtils.renderJson(response, "false");
            return;
        }
        // mobilePhone存在，返回false。
        int countByPhone = userExtManager.countByPhone(mobilePhone);
        if (countByPhone != 0) {
            ResponseUtils.renderJson(response, "false");
            return;
        }
        ResponseUtils.renderJson(response, "true");
    }

    /**
     * 邮件地址唯一性校验
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 17:27
     */
    @RequestMapping(value = "/email_unique.html")
    public void emailUnique(HttpServletResponse response) {
        String email = getRequest().getParameter("email");
        if (StringUtils.isBlank(email)) {
            ResponseUtils.renderJson(response, "false");
            return;
        }
        if (unifiedUserMng.emailExist(email)) {
            ResponseUtils.renderJson(response, "false");
            return;
        }
        ResponseUtils.renderJson(response, "true");
    }

    private WebErrors validateSubmit(String username, String email, String loginPassword, String captcha, CmsSite site, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        try {
            if (!imageCaptchaService.validateResponseForID(request.getSession().getId(), captcha)) {
                errors.addErrorCode("error.invalidCaptcha");
                return errors;
            }
        } catch (CaptchaServiceException e) {
            errors.addErrorCode("error.exceptionCaptcha");
            log.warn("", e);
            return errors;
        }
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        if (errors.ifOutOfLength(username, MessageResolver.getMessage(request, "field.username"), mcfg.getUsernameMinLen(), 100, true)) {
            return errors;
        }
        if (errors.ifNotUsername(username, MessageResolver.getMessage(request, "field.username"), mcfg.getUsernameMinLen(), 100, true)) {
            return errors;
        }
        if (errors.ifOutOfLength(loginPassword, MessageResolver.getMessage(request, "field.password"), mcfg.getPasswordMinLen(), 100, true)) {
            return errors;
        }
        if (errors.ifNotEmail(email, MessageResolver.getMessage(request, "field.email"), 100, true)) {
            return errors;
        }
        // 保留字检查不通过，返回false。
        if (!mcfg.checkUsernameReserved(username)) {
            errors.addErrorCode("error.usernameReserved");
            return errors;
        }
        // 用户名存在，返回false。
        if (unifiedUserMng.usernameExist(username)) {
            errors.addErrorCode("error.usernameExist");
            return errors;
        }
        return errors;
    }

    private WebErrors validateSmsSubmit(String mobile, String username, String loginPassword, String captcha) {
        MemberConfig mcfg = getSite().getConfig().getMemberConfig();
        WebErrors errors = WebErrors.create(getRequest());
        // 验证码有效时间
        Serializable autoCodeTime = (String) getSession().getAttribute("AUTO_CODE_CREAT_TIME");
        // 验证码值
        Serializable autoCode = (String) getSession().getAttribute("AUTO_CODE");
        // 判断验证码是否在有效时间范围
        if (autoCodeTime != null && autoCode != null) {
            Long effectiveTime = Long.parseLong(autoCodeTime.toString());
            if (effectiveTime > System.currentTimeMillis()) {
                // 验证码验证码是否正确
                if (captcha.equals(autoCode.toString())) {
                    getSession().setAttribute("AUTO_CODE_CREAT_TIME", null);
                } else {
                    // 验证码不正确
                    errors.addErrorCode("error.invalidCaptcha");
                }
            } else {
                // 验证码失效
                errors.addErrorCode("error.invalidCaptcha");
            }
        } else {
            // 验证码错误
            errors.addErrorCode("error.invalidCaptcha");
        }
        if (errors.ifOutOfLength(username, "用户名", mcfg.getUsernameMinLen(), 100, true)) {
            return errors;
        }
        if (errors.ifNotUsername(username, "用户名", mcfg.getUsernameMinLen(), 100, true)) {
            return errors;
        }
        if (errors.ifOutOfLength(loginPassword, "密码", mcfg.getPasswordMinLen(), 100, true)) {
            return errors;
        }
        // 保留字检查不通过，返回false。
        if (!mcfg.checkUsernameReserved(username)) {
            errors.addErrorCode("error.usernameReserved");
            return errors;
        }
        // 用户名存在，返回false。
        if (unifiedUserMng.usernameExist(username)) {
            errors.addErrorCode("error.usernameExist");
            return errors;
        }
        // 手机号存在，返回false。
        if (userExtManager.countByPhone(mobile) != 0) {
            errors.addErrorCode("error.mobilePhoneExist");
            return errors;
        }
        return errors;
    }

    private WebErrors validateActive(String username, String activationCode) {
        WebErrors errors = WebErrors.create(getRequest());
        if (StringUtils.isBlank(username) || StringUtils.isBlank(activationCode)) {
            errors.addErrorCode("error.exceptionParams");
            return errors;
        }
        UnifiedUser user = unifiedUserMng.getByUsername(username);
        if (user == null) {
            errors.addErrorCode("error.usernameNotExist");
            return errors;
        }
        if (StringUtils.isNotBlank(user.getActivationCode()) && !user.getActivationCode().equals(activationCode)) {
            errors.addErrorCode("error.exceptionActivationCode");
            return errors;
        }
        return errors;
    }

    @Autowired
    private UnifiedUserMng unifiedUserMng;
    @Autowired
    private CmsUserMng cmsUserMng;
    @Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private ConfigMng configMng;
    @Autowired
    private CmsConfigItemMng cmsConfigItemMng;
    @Autowired
    private CmsUserExtMng userExtManager;
}

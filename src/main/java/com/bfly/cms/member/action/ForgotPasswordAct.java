package com.bfly.cms.member.action;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.system.entity.CmsConfig;
import com.bfly.cms.system.entity.EmailSender;
import com.bfly.cms.system.service.ConfigMng;
import com.bfly.cms.system.service.MessageTemplate;
import com.bfly.cms.user.entity.UnifiedUser;
import com.bfly.cms.user.service.PwdEncoder;
import com.bfly.cms.user.service.UnifiedUserMng;
import com.bfly.core.Constants;
import com.bfly.core.annotation.Token;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
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

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/28 16:12
 * 找回密码Action
 * <p>
 * 用户忘记密码后点击找回密码链接，输入用户名、邮箱和验证码<li>
 * 如果信息正确，返回一个提示页面，并发送一封找回密码的邮件，邮件包含一个链接及新密码，点击链接新密码即生效<li>
 * 如果输入错误或服务器邮箱等信息设置不完整，则给出提示信息<li>
 */
@Controller
public class ForgotPasswordAct extends RenderController {

    private static Logger log = LoggerFactory.getLogger(ForgotPasswordAct.class);

    /**
     * @author andy_hulibo@163.com
     * @date 2018/11/29 17:31
     * 找回密码输入页
     */
    @Token(save = true)
    @GetMapping(value = "/member/forgot_password.html")
    public String forgotPasswordInput(HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsConfig config = site.getConfig();

        Integer validateType = 0;
        if (config.getValidateType() != null) {
            validateType = config.getValidateType();
        }
        //是否开启SMS找回密码
        Integer isSMSForgotPassword = 0;
        if (validateType == 2) {
            isSMSForgotPassword = 1;
        }
        model.addAttribute("isSMSForgotPassword", isSMSForgotPassword);
        return renderPage("member/forgot_password_input.html", model);
    }

    /**
     * 找回密码提交页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 17:34
     */
    @Token(remove = true)
    @PostMapping(value = "/member/forgot_password.html")
    public String forgotPasswordSubmit(String email, String captcha, ModelMap model, String mobile) {
        CmsSite site = getSite();
        CmsConfig config = site.getConfig();
        WebErrors errors;
        String username = getRequest().getParameter("username");

        //是否开启SMS找回密码
        Boolean isSMSForgotPassword = false;
        if (config.getValidateType() == 2) {
            isSMSForgotPassword = true;
        }

        if (isSMSForgotPassword) {
            errors = validateSMSForgotPasswordSubmit(username, mobile, captcha);
        } else {
            errors = validateForgotPasswordSubmit(username, email, captcha);
        }
        if (errors.hasErrors()) {
            return renderErrorPage(model, errors);
        }

        if (isSMSForgotPassword) {
            //把用户保存到SESSION中
            UnifiedUser user = unifiedUserMng.getByUsername(username);
            getSession().setAttribute("FOTGOTPWD_USER_ID", user.getId());
            //跳转到创建新密码页面
            model.addAttribute("username", username);
            return renderPage("member/set_new_password.html", model);
        }
        UnifiedUser user = unifiedUserMng.getByUsername(username);
        EmailSender sender = configMng.getEmailSender();
        MessageTemplate msgTpl = configMng.getForgotPasswordMessageTemplate();
        model.addAttribute("user", user);
        if (user == null) {
            // 用户名不存在
            model.addAttribute("status", 1);
        } else if (StringUtils.isBlank(user.getEmail())) {
            // 用户没有设置邮箱
            model.addAttribute("status", 2);
        } else if (!user.getEmail().equals(email)) {
            // 邮箱输入错误
            model.addAttribute("status", 3);
        } else if (sender == null) {
            // 邮件服务器没有设置好
            model.addAttribute("status", 4);
        } else if (msgTpl == null) {
            // 邮件模板没有设置好
            model.addAttribute("status", 5);
        } else {
            try {
                unifiedUserMng.passwordForgotten(user.getId(), sender, msgTpl);
                model.addAttribute("status", 0);
            } catch (Exception e) {
                // 发送邮件异常
                model.addAttribute("status", 100);
                model.addAttribute("message", e.getMessage());
                log.error("send email exception.", e);
            }
        }
        return renderPage("member/forgot_password_result.html", model);
    }

    @GetMapping(value = "/member/password_reset.html")
    public String passwordReset(Integer uid, String key, ModelMap model) {
        WebErrors errors = validatePasswordReset(uid, key);
        if (errors.hasErrors()) {
            return renderErrorPage(model, errors);
        }
        UnifiedUser user = unifiedUserMng.findById(uid);
        if (user == null) {
            // 用户不存在
            model.addAttribute("status", 1);
        } else if (StringUtils.isBlank(user.getResetKey())) {
            // resetKey不存在
            model.addAttribute("status", 2);
        } else if (!user.getResetKey().equals(key)) {
            // 重置key错误
            model.addAttribute("status", 3);
        } else {
            unifiedUserMng.resetPassword(uid);
            model.addAttribute("status", 0);
        }
        return renderPage("member/password_reset.html", model);
    }

    /**
     * SMS验证密码修改
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 17:41
     */
    @PostMapping(value = "sms_password_reset.html")
    public String smsRestPassword(String username, String loginPassword, ModelMap model) {
        WebErrors errors = WebErrors.create(getRequest());
        Integer userId = (Integer) getSession().getAttribute("FOTGOTPWD_USER_ID");

        if (StringUtils.isBlank(username) || StringUtils.isBlank(loginPassword)) {
            errors.addErrorString(Constants.API_MESSAGE_PARAM_REQUIRED);
            errors.addErrorString(ResponseCode.API_CODE_PARAM_REQUIRED);
        } else if (userId == null) {
            //验证过期
            errors.addErrorString(Constants.API_MESSAGE_USER_STATUS_OVER_TIME);
            errors.addErrorString(ResponseCode.API_CODE_USER_STATUS_OVER_TIME);
        }
        if (errors.hasErrors()) {
            return renderErrorPage(model, errors);
        }
        UnifiedUser user = unifiedUserMng.findById(userId);
        if (user == null) {
            errors.addErrorString(Constants.API_MESSAGE_USER_NOT_FOUND);
            errors.addErrorString(ResponseCode.API_CODE_USER_NOT_FOUND);
        } else if (!username.equals(user.getUsername())) {
            errors.addErrorString(Constants.API_MESSAGE_PARAM_ERROR);
            errors.addErrorString(ResponseCode.API_CODE_PARAM_ERROR);
        }

        if (errors.hasErrors()) {
            return renderErrorPage(model, errors);
        }
        user.setPassword(pwdEncoder.encodePassword(loginPassword));
        unifiedUserMng.restPassword(user);
        model.addAttribute("status", 0);
        return renderPage("member/password_reset.html", model);
    }


    private WebErrors validateForgotPasswordSubmit(String username, String email, String captcha) {
        WebErrors errors = WebErrors.create(getRequest());
        if (errors.ifBlank(username, "username", 100, true)) {
            return errors;
        }
        if (errors.ifBlank(email, "email", 100, true)) {
            return errors;
        }
        if (errors.ifBlank(captcha, "captcha", 20, true)) {
            return errors;
        }
        try {
            if (!imageCaptchaService.validateResponseForID(getSession().getId(), captcha)) {
                errors.addErrorCode("error.invalidCaptcha");
                return errors;
            }
        } catch (CaptchaServiceException e) {
            errors.addErrorCode("error.exceptionCaptcha");
            log.warn("", e);
            return errors;
        }
        return errors;
    }

    private WebErrors validateSMSForgotPasswordSubmit(String username, String mobile, String captcha) {
        WebErrors errors = WebErrors.create(getRequest());
        if (errors.ifBlank(username, "username", 100, true)) {
            return errors;
        }
        if (errors.ifBlank(mobile, "mobile", 20, true)) {
            return errors;
        }
        if (errors.ifBlank(captcha, "captcha", 20, true)) {
            return errors;
        }
        // 验证码有效时间
        Serializable autoCodeTime = (String) getSession().getAttribute("FORGOTPWD_AUTO_CODE_CREAT_TIME");
        // 验证码值
        Serializable autoCode = (String) getSession().getAttribute("FORGOTPWD_AUTO_CODE");
        // 判断验证码是否在有效时间范围
        if (autoCodeTime != null && autoCode != null) {
            Long effectiveTime = Long.parseLong(autoCodeTime.toString());
            if (effectiveTime > System.currentTimeMillis()) {
                // 验证码验证码是否正确
                if (captcha.equals(autoCode.toString())) {
                    getSession().setAttribute("FORGOTPWD_AUTO_CODE_CREAT_TIME", null);
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
        return errors;
    }

    private WebErrors validatePasswordReset(Integer uid, String key) {
        WebErrors errors = WebErrors.create(getRequest());
        if (errors.ifNull(uid, "uid", true)) {
            return errors;
        }
        if (errors.ifBlank(key, "key", 50, true)) {
            return errors;
        }
        return errors;
    }

    @Autowired
    private UnifiedUserMng unifiedUserMng;
    @Autowired
    private ConfigMng configMng;
    @Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private PwdEncoder pwdEncoder;
}

package com.bfly.web.member.action;

import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.cms.member.service.impl.MemberLoginLogsServiceImpl;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.ValidateUtil;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.context.AutoLoginThreadLocal;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.enums.LoginType;
import com.bfly.core.enums.LogsType;
import com.bfly.core.exception.AccountUnActiveException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author andy_hulibo@163.com
 * @date 2019/8/28 9:35
 */
@Controller("MemberIndexController")
public class IndexController extends RenderController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IMemberService memberService;
    @Autowired
    private ImageCaptchaService captchaService;
    @Autowired
    private MemberLoginLogsServiceImpl memberLoginLogsService;

    /**
     * 登录页面
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/25 16:52
     */
    @GetMapping(value = "/login")
    public String login(HttpServletResponse response) {
        if (ContextUtil.getLoginMember() != null) {
            return redirect("/member/index.html");
        }
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered()) {
            // 记住我 自动登录
            Member member = (Member) subject.getPrincipal();
            AutoLoginThreadLocal.set(true);
            return login(member, response, 1, true);
        }
        if (ContextUtil.isAjax(getRequest())) {
            return renderTplPath("login_dialog.html", "common");
        }
        String returnUrl = getRequest().getParameter("returnUrl");
        getSession().setAttribute("returnUrl", returnUrl);
        return renderTplPath("login.html", "common");
    }

    /**
     * 提交登录
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/28 9:38
     */
    @PostMapping(value = "/login")
    public String login(Member member, HttpServletResponse response, @RequestParam(value = "type", required = false) Integer loginType, @RequestParam(value = "remember", required = false, defaultValue = "false") Boolean isRemember) {
        boolean isFromAjax = ContextUtil.isAjax(getRequest());
        loginType = loginType == null ? 1 : loginType;
        isRemember = isRemember == null ? false : isRemember;
        boolean loginSuccess = false;
        try {
            if (getRequest().getParameterMap().containsKey("captcha")) {
                // 存在验证码则校验验证码
                String captcha = getRequest().getParameter("captcha");
                Boolean success = captchaService.validateResponseForID(getSession().getId(), captcha);
                Assert.isTrue(success == null ? false : success.booleanValue(), "验证码不正确!");
            }
            memberService.login(member.getUserName(), member.getPassword(), isRemember, loginType == 1 ? LoginType.NORMAL : (loginType == 2 ? LoginType.DYNAMIC : null));
            loginSuccess = true;
            if (isFromAjax) {
                ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
                return "";
            }
        } catch (AccountUnActiveException e) {
            // 未激活跳转到激活提示页面
            return redirect("/register/success.html?email=" + member.getUserName());
        } catch (Exception e) {
            if (isFromAjax) {
                // 前端 ajax异常捕捉 更新页面
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            String tpl = isFromAjax ? "login_dialog.html" : "login.html";
            getRequest().setAttribute("error", e.getMessage());
            getRequest().setAttribute("loginType", loginType);
            return renderTplPath(tpl, "common");
        } finally {
            memberLoginLogsService.updateLoginInfo(member.getUserName(), loginSuccess);
            memberLoginLogsService.saveLoginLogs(member.getUserName(), "会员登录", LogsType.LOGIN_LOG, loginSuccess);
        }
        Object returnUrlObj = getSession().getAttribute("returnUrl");
        String returnUrl = returnUrlObj == null ? null : String.valueOf(returnUrlObj);
        if (!ValidateUtil.isUrl(returnUrl)) {
            returnUrl = "/member/index.html";
        }
        getSession().removeAttribute("returnUrl");
        return redirect(returnUrl);
    }

    /**
     * 注册
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/18 10:59
     */
    @PostMapping(value = "/register")
    public String register(Member member) {
        try {
            String captcha = getRequest().getParameter("captcha");
            Boolean success = captchaService.validateResponseForID(getSession().getId(), captcha);
            Assert.isTrue(success == null ? false : success.booleanValue(), "验证码不正确!");
            memberService.register(member, false);
        } catch (AccountUnActiveException e) {
            return redirect("/register/success.html?email=" + member.getUserName());
        } catch (Exception e) {
            getRequest().setAttribute("error", e.getMessage());
            getRequest().setAttribute("loginType", 3);
            return renderTplPath("login.html", "common");
        }
        return redirect("/register/success.html?email=" + member.getUserName());
    }

    /**
     * 注册成功页面
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/25 12:29
     */
    @GetMapping(value = "/register/success")
    public String registerSuccess(@RequestParam(value = "email", required = false) String username) {
        if (!StringUtils.hasLength(username)) {
            return redirect("/login.html");
        }
        Member member = memberService.getMember(username);
        if (member == null || member.isActivated()) {
            return redirect("/login.html");
        }
        getRequest().setAttribute("userName", username);
        return renderTplPath("register_success.html", "common");
    }

    /**
     * 用户名激活
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/24 14:45
     */
    @GetMapping(value = "/active")
    public String active(@RequestParam("email") String userName, @RequestParam("certifyid") String certifyId) {
        try {
            memberService.activeMemberForMail(userName, certifyId);
        } catch (Exception e) {
            getRequest().setAttribute("isActivated", false);
            getRequest().setAttribute("error", e.getMessage());
            return renderTplPath("active_result.html", "common");
        }
        getRequest().setAttribute("isActivated", true);
        return renderTplPath("active_result.html", "common");
    }

    /**
     * 重新发送激活邮件
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/25 13:23
     */
    @GetMapping(value = "/active/mail")
    public void resendActiveMail(@RequestParam("email") String userName, HttpServletResponse response) {
        memberService.resendActiveMail(userName);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 网站协议
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/27 18:45
     */
    @GetMapping(value = "/xieyi")
    public String xieYi() {
        return renderTplPath("xieyi.html", "common");
    }

    /**
     * 忘记密码页面1
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/21 14:56
     */
    @GetMapping(value = "/forget/pwd")
    public String forgetPassword1() {
        return renderTplPath("forget_pwd_1.html", "common");
    }

    /**
     * 忘记密码页面2
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/21 15:01
     */
    @GetMapping(value = "/forget/submit")
    public String forgetPassword2() {
        String userName = getRequest().getParameter("email");
        String captcha = getRequest().getParameter("captcha");
        try {
            Assert.hasText(userName, "邮箱地址不能为空!");
            Assert.isTrue(ValidateUtil.isEmail(userName), "邮箱格式不正确!");
            Boolean success = captchaService.validateResponseForID(getSession().getId(), captcha);
            Assert.isTrue(success == null ? false : success.booleanValue(), "验证码不正确!");

            memberService.forgetPwdForMail(userName);
        } catch (Exception e) {
            getRequest().setAttribute("error", e.getMessage());
            return forgetPassword1();
        }
        return renderTplPath("forget_pwd_2.html", "common");
    }

    /**
     * 忘记密码页面3
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/21 15:01
     */
    @GetMapping(value = "/forget/reset")
    public String forgetPassword3() {
        String userName = getRequest().getParameter("email");
        String certifyId = getRequest().getParameter("certifyid");
        try {
            Assert.hasText(userName, "邮箱不能为空!");
            Assert.isTrue(ValidateUtil.isEmail(userName), "邮箱地址格式不正确!");
            Assert.hasText(certifyId, "验证码不能为空!");

            Member member = memberService.getMember(userName);
            Assert.notNull(member, "不存在的会员信息!");
            String sign = DigestUtils.md5Hex(member.getUserName().concat(member.getSalt()).concat(member.getPassword()));
            Assert.isTrue(sign.equals(certifyId), "验证码不正确");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return redirect("/forget/pwd.html");
        }
        getRequest().setAttribute("email", userName);
        getRequest().setAttribute("certifyId", certifyId);
        return renderTplPath("forget_pwd_3.html", "common");
    }

    /**
     * 忘记密码--密码重置成功
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/21 17:40
     */
    @GetMapping(value = "/forget/success")
    public String forgetPassword4() {
        return renderTplPath("forget_pwd_4.html", "common");
    }

    /**
     * 忘记密码密码重置
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/21 17:35
     */
    @PostMapping(value = "/forget/reset")
    public String forgetPasswordRest() {
        String userName = getRequest().getParameter("email");
        String certifyId = getRequest().getParameter("certifyId");
        String newPwd = getRequest().getParameter("password");
        try {
            Assert.hasText(userName, "缺少参数!");
            Assert.isTrue(ValidateUtil.isEmail(userName), "缺少参数!");
            Assert.hasText(certifyId, "验证码不能为空!");
            Assert.hasText(newPwd, "新密码不能为空!");

            Member member = memberService.getMember(userName);
            Assert.notNull(member, "参数错误!");
            String sign = DigestUtils.md5Hex(member.getUserName().concat(member.getSalt()).concat(member.getPassword()));
            Assert.isTrue(sign.equals(certifyId), "参数错误");

            memberService.editMemberPassword(member.getUserName(), newPwd);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return redirect("/forget/pwd.html");
        }
        return redirect("/forget/success.html");
    }
}


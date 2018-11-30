package com.bfly.cms.member.action;

import com.bfly.cms.funds.service.CmsUserAccountMng;
import com.bfly.cms.system.entity.MemberConfig;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserExt;
import com.bfly.cms.user.service.CmsUserExtMng;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 会员中心Action
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 17:27
 */
@Controller
public class MemberAct extends RenderController {

    private static final Logger log = LoggerFactory.getLogger(MemberAct.class);

    /**
     * 校验
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 16:17
     */
    private String check(ModelMap model) {
        MemberConfig mcfg = getSite().getConfig().getMemberConfig();
        if (!mcfg.isMemberOn()) {
            return renderMessagePage(model, "member.memberClose");
        }
        if (getUser() == null) {
            return renderLoginPage(model);
        }
        return null;
    }

    /**
     * 会员中心页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 16:37
     */
    @GetMapping(value = "/member/index.html")
    public String index(ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        return renderPage("member/member_center.html", model);
    }

    /**
     * 个人资料输入页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 16:39
     */
    @GetMapping(value = "/member/profile.html")
    public String profileInput(ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        return renderPage("member/member_profile.html", model);
    }

    /**
     * 更换头像
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 16:39
     */
    @GetMapping(value = "/member/portrait.html")
    public String portrait(ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        return renderPage("member/member_portrait.html", model);
    }

    /**
     * 个人资料提交页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 16:40
     */
    @PostMapping(value = "/member/profile.html")
    public String profileSubmit(CmsUserExt ext, String nextUrl, ModelMap model) throws IOException {
        String result = check(model);
        if (result != null) {
            return result;
        }
        CmsUser user = getUser();
        ext.setId(user.getId());
        cmsUserExtMng.update(ext, user);
        log.info("update CmsUserExt success. id={}", user.getId());
        return renderSuccessPage(model, nextUrl);
    }

    /**
     * 密码修改输入页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 16:41
     */
    @GetMapping(value = "/member/pwd.html")
    public String passwordInput(ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        return renderPage("member/member_password.html", model);
    }

    /**
     * 密码修改提交页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 16:42
     */
    @PostMapping(value = "/member/pwd.html")
    public String passwordSubmit(String origPwd, String newPwd, String email, String nextUrl, ModelMap model) throws IOException {
        String result = check(model);
        if (result != null) {
            return result;
        }
        CmsUser user = getUser();
        WebErrors errors = validatePasswordSubmit(user.getId(), origPwd, newPwd, email);
        if (errors.hasErrors()) {
            return renderErrorPage(model, errors);
        }
        cmsUserMng.updatePwdEmail(user.getId(), newPwd, email);
        return renderSuccessPage(model, nextUrl);
    }

    /**
     * 完善账户资料
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 16:45
     */
    @GetMapping(value = "/member/account.html")
    public String accountInput(ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        return renderPage("member/member_account.html", model);
    }

    /**
     * 完善用户账户资料
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 16:44
     */
    @PostMapping(value = "/member/account.html")
    public String accountSubmit(String accountWeiXin, String accountAlipy, Short drawAccount, String nextUrl, ModelMap model) throws IOException {
        String result = check(model);
        if (result != null) {
            return result;
        }
        WebErrors errors = WebErrors.create(getRequest());
        if (drawAccount == null) {
            errors.addErrorCode("error.needParams");
        } else {
            boolean flag = !(drawAccount == 0 && StringUtils.isNotBlank(accountWeiXin) || drawAccount == 1 && StringUtils.isNotBlank(accountAlipy));
            if (flag) {
                errors.addErrorCode("error.needParams");
            }
        }
        if (errors.hasErrors()) {
            return renderErrorPage(model, errors);
        }
        cmsUserAccountMng.updateAccountInfo(accountWeiXin, accountAlipy, drawAccount, getUser());
        log.info("update CmsUserExt success. id={}", getUser().getId());
        return renderSuccessPage(model, nextUrl);
    }


    /**
     * 验证密码是否正确
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 16:44
     */
    @RequestMapping("/member/checkPwd.html")
    public void checkPwd(String origPwd, HttpServletResponse response) {
        CmsUser user = CmsUtils.getUser(getRequest());
        boolean pass = cmsUserMng.isPasswordValid(user.getId(), origPwd);
        ResponseUtils.renderJson(response, pass ? "true" : "false");
    }

    private WebErrors validatePasswordSubmit(Integer id, String origPwd, String newPwd, String email) {
        WebErrors errors = WebErrors.create(getRequest());
        if (errors.ifBlank(origPwd, "origPwd", 100, true)) {
            return errors;
        }
        if (errors.ifMaxLength(newPwd, "newPwd", 100, true)) {
            return errors;
        }
        if (errors.ifNotEmail(email, "email", 100, true)) {
            return errors;
        }
        if (!cmsUserMng.isPasswordValid(id, origPwd)) {
            errors.addErrorCode("member.origPwdInvalid");
            return errors;
        }
        return errors;
    }

    @Autowired
    private CmsUserMng cmsUserMng;
    @Autowired
    private CmsUserExtMng cmsUserExtMng;
    @Autowired
    private CmsUserAccountMng cmsUserAccountMng;
}

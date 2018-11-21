package com.bfly.cms.action.member;

import static com.bfly.cms.Constants.TPLDIR_MEMBER;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfly.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bfly.cms.entity.assist.CmsWebservice;
import com.bfly.cms.manager.assist.CmsWebserviceMng;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.entity.CmsSite;
import com.bfly.core.entity.CmsUser;
import com.bfly.core.entity.CmsUserExt;
import com.bfly.core.entity.MemberConfig;
import com.bfly.core.manager.CmsUserAccountMng;
import com.bfly.core.manager.CmsUserExtMng;
import com.bfly.core.manager.CmsUserMng;
import com.bfly.core.web.util.CmsUtils;
import com.bfly.core.web.util.FrontUtils;

/**
 * 会员中心Action
 */
@Controller
public class MemberAct {
    private static final Logger log = LoggerFactory.getLogger(MemberAct.class);

    public static final String MEMBER_CENTER = "tpl.memberCenter";
    public static final String MEMBER_PROFILE = "tpl.memberProfile";
    public static final String MEMBER_PORTRAIT = "tpl.memberPortrait";
    public static final String MEMBER_PASSWORD = "tpl.memberPassword";
    public static final String MEMBER_ACCOUNT = "tpl.memberAccount";

    /**
     * 会员中心页
     * <p>
     * 如果没有登录则跳转到登陆页
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/index.html", method = RequestMethod.GET)
    public String index(HttpServletRequest request,
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, MEMBER_CENTER);
    }

    /**
     * 个人资料输入页
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/profile.html", method = RequestMethod.GET)
    public String profileInput(HttpServletRequest request,
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, MEMBER_PROFILE);
    }

    /**
     * 更换头像
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/portrait.html", method = RequestMethod.GET)
    public String portrait(HttpServletRequest request,
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, MEMBER_PORTRAIT);
    }

    /**
     * 个人资料提交页
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/member/profile.html", method = RequestMethod.POST)
    public String profileSubmit(CmsUserExt ext, String nextUrl,
                                HttpServletRequest request, HttpServletResponse response,
                                ModelMap model) throws IOException {
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
        ext.setId(user.getId());
        ext = cmsUserExtMng.update(ext, user);
        cmsWebserviceMng.callWebService("false", user.getUsername(), null,
                null, ext, CmsWebservice.SERVICE_TYPE_UPDATE_USER);
        log.info("update CmsUserExt success. id={}", user.getId());
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * 密码修改输入页
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/pwd.html", method = RequestMethod.GET)
    public String passwordInput(HttpServletRequest request,
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, MEMBER_PASSWORD);
    }

    /**
     * 密码修改提交页
     *
     * @param origPwd  原始密码
     * @param newPwd   新密码
     * @param email    邮箱
     * @param nextUrl  下一个页面地址
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/member/pwd.html", method = RequestMethod.POST)
    public String passwordSubmit(String origPwd, String newPwd, String email,
                                 String nextUrl, HttpServletRequest request,
                                 HttpServletResponse response, ModelMap model) throws IOException {
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
        WebErrors errors = validatePasswordSubmit(user.getId(), origPwd,
                newPwd, email, request);
        /*
		if(user.getUsername().equals("test")){
			errors.addErrorString("修改密码哦，您这样可不好哟!");
		}
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		*/
        cmsUserMng.updatePwdEmail(user.getId(), newPwd, email);
        cmsWebserviceMng.callWebService("false", user.getUsername(), newPwd,
                email, null, CmsWebservice.SERVICE_TYPE_UPDATE_USER);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * 完善账户资料
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/account.html", method = RequestMethod.GET)
    public String accountInput(HttpServletRequest request,
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, MEMBER_ACCOUNT);
    }

    //完善用户账户资料
    @RequestMapping(value = "/member/account.html", method = RequestMethod.POST)
    public String accountSubmit(String accountWeiXin, String accountAlipy,
                                Short drawAccount, String nextUrl, HttpServletRequest request,
                                HttpServletResponse response, ModelMap model) throws IOException {
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

        WebErrors errors = WebErrors.create(request);
        if (drawAccount == null) {
            errors.addErrorCode("error.needParams");
        } else {
            if (!(drawAccount == 0 && StringUtils.isNotBlank(accountWeiXin)
                    || drawAccount == 1 && StringUtils.isNotBlank(accountAlipy))) {
                errors.addErrorCode("error.needParams");
            }
        }
        if (errors.hasErrors()) {
            return FrontUtils.showError(request, response, model, errors);
        }
        cmsUserAccountMng.updateAccountInfo(accountWeiXin, accountAlipy, drawAccount, user);
        log.info("update CmsUserExt success. id={}", user.getId());
        return FrontUtils.showSuccess(request, model, nextUrl);
    }


    /**
     * 验证密码是否正确
     *
     * @param origPwd  原密码
     * @param request
     * @param response
     */
    @RequestMapping("/member/checkPwd.html")
    public void checkPwd(String origPwd, HttpServletRequest request,
                         HttpServletResponse response) {
        CmsUser user = CmsUtils.getUser(request);
        boolean pass = cmsUserMng.isPasswordValid(user.getId(), origPwd);
        ResponseUtils.renderJson(response, pass ? "true" : "false");
    }

    private WebErrors validatePasswordSubmit(Integer id, String origPwd,
                                             String newPwd, String email, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
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
    @Autowired
    private CmsWebserviceMng cmsWebserviceMng;
}

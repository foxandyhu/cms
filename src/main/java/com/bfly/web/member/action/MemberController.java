package com.bfly.web.member.action;

import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.entity.MemberExt;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.cms.member.service.impl.MemberLoginLogsServiceImpl;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.ipseek.IpSeekerUtil;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.enums.LogsType;
import com.bfly.core.security.ActionModel;
import com.bfly.core.security.Md5PwdEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 会员中心
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/28 9:42
 */
@Controller("MemberCenterController")
@RequestMapping(value = "/member")
public class MemberController extends RenderController {

    @Autowired
    private IMemberService memberService;
    @Autowired
    private MemberLoginLogsServiceImpl memberLoginLogsService;

    /**
     * 会员中心首页
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/28 9:33
     */
    @GetMapping(value = "/index")
    public String index() {
        Member member = memberService.get(getMember().getId());
        getRequest().setAttribute("member", member);
        getRequest().setAttribute("location", IpSeekerUtil.getIPLocation(member.getLastLoginIp()));
        return renderTplPath("index.html", "member");
    }

    /**
     * 登出
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/6 17:09
     */
    @GetMapping(value = "/logout")
    public String logout() {
        String userName = getMember().getUserName();
        memberService.logout(userName);
        memberLoginLogsService.saveLoginLogs(userName, "会员登出", LogsType.LOGOUT_LOG, true);
        return redirect("/");
    }

    /**
     * 修改密码
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/28 11:50
     */
    @GetMapping(value = "/password")
    public String editPwd() {
        return renderTplPath("pwd.html", "member");
    }

    /**
     * 修改密码
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/17 20:45
     */
    @PostMapping(value = "/password")
    public void editPwd(HttpServletResponse response) {
        String oldPwd = getRequest().getParameter("password");
        Assert.hasLength(oldPwd, "原密码为空!");

        Member member = memberService.get(getMember().getId());
        String enPwd = new Md5PwdEncoder().encodePassword(oldPwd, member.getSalt());
        Assert.isTrue(member.getPassword().equals(enPwd), "原密码不正确!");

        String newPwd = getRequest().getParameter("newPwd");
        memberService.editMemberPassword(member.getUserName(), newPwd);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 我的评论
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/28 11:50
     */
    @GetMapping(value = "/comment")
    public String comment() {
        return renderTplPath("comment.html", "member");
    }

    /**
     * 我的留言
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/28 11:50
     */
    @GetMapping(value = "/guestbook")
    public String guestBook() {
        return renderTplPath("guestbook.html", "member");
    }

    /**
     * 个人信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/19 11:10
     */
    @GetMapping(value = "/info")
    public String memberInfo() {
        Member member = memberService.get(getMember().getId());
        getRequest().setAttribute("memberInfo", member);
        return renderTplPath("info.html", "member");
    }

    /**
     * 编辑个人信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/19 11:16
     */
    @PostMapping(value = "/info/edit")
    @ActionModel(value = "编辑个人信息")
    public String editMemberInfo(MemberExt extInfo) {
        extInfo.setId(getMember().getId());
        memberService.editMemberExtInfo(extInfo);
        return redirect("/member/info.html");
    }

    /**
     * 修改头像页面
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/19 20:41
     */
    @GetMapping(value = "/face/edit")
    public String toEditFace() {
        return renderTplPath("edit_face.html", "member");
    }

    /**
     * 修改头像
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/19 17:21
     */
    @PostMapping(value = "/face/edit")
    @ActionModel(value = "修改头像")
    public String editFace(@RequestParam("base64Face") String base64Face) {
        memberService.uploadMemberFace(getMember().getUserName(), base64Face);
        return redirect("/member/info.html");
    }
}
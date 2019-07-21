package com.bfly.manage.member;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.entity.MemberConfig;
import com.bfly.cms.member.service.IMemberConfigService;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.cms.system.entity.EmailProvider;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.MemberStatus;
import com.bfly.core.enums.SysError;
import com.bfly.core.exception.WsResponseException;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/10 14:57
 */
@RestController
@RequestMapping(value = "/manage/member")
public class MemberController extends BaseManageController {

    @Autowired
    private IMemberService memberService;
    @Autowired
    private IMemberConfigService configService;

    /**
     * 会员列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 14:59
     */
    @GetMapping("/list")
    @ActionModel(value = "会员列表", need = false)
    public void listMember(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> exactMap = new HashMap<>(1);
        String statusStr = request.getParameter("status");
        if (statusStr != null) {
            exactMap.put("status", DataConvertUtils.convertToInteger(statusStr));
        }

        Map<String, String> unExactMap = new HashMap<>(3);
        String userName = request.getParameter("userName");
        if (userName != null) {
            unExactMap.put("userName", userName);
        }
        String email = request.getParameter("email");
        if (email != null) {
            unExactMap.put("email", email);
        }
        Pager pager = memberService.getPage(exactMap, unExactMap, null);
        JSONObject json = JsonUtil.toJsonFilter(pager, "thirdAccounts", "memberExt");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 新增会员
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:00
     */
    @PostMapping(value = "/add")
    @ActionModel("新增会员")
    public void addMember(@RequestBody @Valid Member member, BindingResult result, HttpServletResponse response) {
        validData(result);
        memberService.save(member);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改会员
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:45
     */
    @PostMapping(value = "/edit")
    @ActionModel("编辑会员信息")
    public void editMember(@RequestBody @Valid Member member, BindingResult result, HttpServletResponse response) {
        validData(result);
        memberService.edit(member);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获取会员基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:50
     */
    @GetMapping(value = "/{memberId}")
    @ActionModel(value = "会员详情", need = false)
    public void viewMember(@PathVariable("memberId") int memberId, HttpServletResponse response) {
        Member member = memberService.get(memberId);
        if (StringUtils.hasLength(member.getMemberExt().getFace())) {
            member.getMemberExt().setFace(ResourceConfig.getServer() + member.getMemberExt().getFace());
        }
        JSONObject json = JsonUtil.toJsonFilter(member, "thirdAccounts", "member");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 删除会员信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:53
     */
    @PostMapping(value = "/del")
    @ActionModel("删除会员")
    public void removeMember(HttpServletResponse response, @RequestBody Integer... ids) {
        memberService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 用户名检查
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:40
     */
    @PostMapping(value = "/check")
    @ActionModel(value = "用户名重复检查", need = false)
    public void checkUserName(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        long count = memberService.getCount(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = 4990379673489715199L;

            {
                put("userName", username);
            }
        });
        if (count > 0) {
            throw new WsResponseException(SysError.DATA_REPEAT, "用户名已存在!");
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改状态
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/20 9:37
     */
    @GetMapping(value = "/edit/{memberId}-{status}")
    @ActionModel("修改会员账户状态")
    public void editMemberStatus(HttpServletResponse response, @PathVariable("memberId") int memberId, @PathVariable("status") int status) {
        memberService.editMemberStatus(memberId, MemberStatus.getStatus(status));
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改会员账户密码
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/20 10:21
     */
    @PostMapping(value = "/editpwd")
    @ActionModel("修改会员账户密码")
    public void editMemberPasswor(HttpServletResponse response, @RequestBody Map<String, String> params) {
        int memberId = DataConvertUtils.convertToInteger(params.get("memberId"));
        String password = params.get("password");
        memberService.editMemberPassword(memberId, password);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获得会员配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:44
     */
    @GetMapping(value = "/config/info")
    @ActionModel(value = "会员配置详情", need = false)
    public void getMemberConfig(HttpServletResponse response) {
        MemberConfig config = configService.getMemberConfig();
        if(config!=null){
            if(config.getRegistConfig()!=null && config.getRegistConfig().getEmailProvider()!=null)
            {
                EmailProvider provider=new EmailProvider();
                provider.setId(config.getRegistConfig().getEmailProvider().getId());
                provider.setName(config.getRegistConfig().getEmailProvider().getName());
                config.getRegistConfig().setEmailProvider(provider);
            }
            if(config.getLoginConfig()!=null && config.getLoginConfig().getEmailProvider()!=null)
            {
                EmailProvider provider=new EmailProvider();
                provider.setId(config.getLoginConfig().getEmailProvider().getId());
                provider.setName(config.getLoginConfig().getEmailProvider().getName());
                config.getLoginConfig().setEmailProvider(provider);
            }
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(config));
    }

    /**
     * 修改会员登录配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:46
     */
    @PostMapping(value = "/config/login/edit")
    @ActionModel("修改会员登录配置信息")
    public void editMemberLoginConfig(@RequestBody MemberConfig config, BindingResult result, HttpServletResponse response) {
        validData(result);
        configService.editMemberLoginConfig(config);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }


    /**
     * 修改会员注册配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:46
     */
    @PostMapping(value = "/config/regist/edit")
    @ActionModel("修改会员登录配置信息")
    public void editMemberRegistConfig(@RequestBody MemberConfig config, BindingResult result, HttpServletResponse response) {
        validData(result);
        configService.editMemberRegistConfig(config);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}

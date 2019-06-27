package com.bfly.manage.member;

import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.entity.MemberConfig;
import com.bfly.cms.member.service.IMemberConfigService;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.core.context.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.SysError;
import com.bfly.core.exception.WsResponseException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void listMember(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> property = new HashMap<String, Object>(3) {
            private static final long serialVersionUID = -9126101626116724049L;

            {
                put("username", request.getParameter("username"));
                put("email", request.getParameter("email"));
                put("status", request.getParameter("status"));
            }
        };
        Pager pager = memberService.getPage(property);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 新增会员
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:00
     */
    @PostMapping(value = "/add")
    public void addMember(@Valid Member member, BindingResult result, HttpServletResponse response) {
        validData(result);
        memberService.save(member);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 修改会员
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:45
     */
    @PostMapping(value = "/edit")
    public void editMember(Member member, HttpServletResponse response) {
        memberService.edit(member);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 获取会员基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:50
     */
    @GetMapping(value = "/{memberId}")
    public void viewMember(@PathVariable("memberId") int memberId, HttpServletResponse response) {
        Member member = memberService.get(memberId);
        ResponseUtil.writeJson(response, member);
    }

    /**
     * 删除会员信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:53
     */
    @PostMapping(value = "/del")
    public void removeMember(HttpServletRequest request, HttpServletResponse response) {
        String memberIdStr = request.getParameter("ids");
        Integer[] memberIds = DataConvertUtils.convertToIntegerArray(memberIdStr.split(","));
        memberService.remove(memberIds);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 用户名检查
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:40
     */
    @PostMapping(value = "/check")
    public void checkUserName(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        long count = memberService.getCount(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = 4990379673489715199L;

            {
                put("username", username);
            }
        });
        if (count > 0) {
            throw new WsResponseException(SysError.DATA_REPEAT, "用户名已存在!");
        }
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 获得会员配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:44
     */
    @GetMapping(value = "/config/info")
    public void getMemberConfig(HttpServletResponse response) {
        MemberConfig config = configService.getMemberConfig();
        ResponseUtil.writeJson(response, config);
    }

    /**
     * 修改会员配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:46
     */
    @PostMapping(value = "/config/edit")
    public void editMemberConfig(@Valid MemberConfig config,BindingResult result, HttpServletResponse response) {
        validData(result);
        configService.edit(config);
        ResponseUtil.writeJson(response, "");
    }
}

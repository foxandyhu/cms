package com.bfly.manage.member;

import com.bfly.cms.member.entity.MemberGroup;
import com.bfly.cms.member.service.IMemberGroupService;
import com.bfly.core.context.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 会员组Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/10 16:26
 */
@RestController
@RequestMapping(value = "/manage/member/group")
public class MemberGroupController extends BaseManageController {

    @Autowired
    private IMemberGroupService groupService;

    /**
     * 会员组列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 16:53
     */
    @GetMapping("/list")
    public void listMemberGroup(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Pager pager = groupService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 新增会员组
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:00
     */
    @PostMapping(value = "/add")
    public void addMemberGroup(@Valid MemberGroup group, BindingResult result, HttpServletResponse response) {
        validData(result);
        groupService.save(group);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 修改会员组
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:45
     */
    @PostMapping(value = "/edit")
    public void editMemberGroup(MemberGroup group, HttpServletResponse response) {
        groupService.edit(group);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 获取会员组基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:50
     */
    @GetMapping(value = "/{groupId}")
    public void viewMemberGroup(@PathVariable("groupId") int groupId, HttpServletResponse response) {
        MemberGroup group = groupService.get(groupId);
        ResponseUtil.writeJson(response, group);
    }

    /**
     * 删除会员组
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 16:54
     */
    @GetMapping("/del")
    public void removeMemberGroup(HttpServletRequest request, HttpServletResponse response) {
        String accountIdStr = request.getParameter("ids");
        Integer[] accountIds = DataConvertUtils.convertToIntegerArray(accountIdStr.split(","));
        groupService.remove(accountIds);
        ResponseUtil.writeJson(response, "");
    }
}

package com.bfly.manage.friendlink;

import com.bfly.cms.friendlink.entity.FriendLinkType;
import com.bfly.cms.friendlink.service.IFriendLinkTypeService;
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
 * 友情链接类型Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:53
 */
@RestController
@RequestMapping(value = "/manage/friendlink/type")
public class FriendLinkTypeController extends BaseManageController {

    @Autowired
    private IFriendLinkTypeService friendLinkTypeService;

    /**
     * 友情链接类型列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:53
     */
    @GetMapping("/list")
    public void listFriendLink(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Pager pager = friendLinkTypeService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 新增友情链接类型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:57
     */
    @PostMapping(value = "/add")
    public void addFriendLink(@Valid FriendLinkType friendLinkType, BindingResult result, HttpServletResponse response) {
        validData(result);
        friendLinkTypeService.save(friendLinkType);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 修改友情链接类型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:01
     */
    @PostMapping(value = "/edit")
    public void editFriendLink(FriendLinkType friendLinkType, HttpServletResponse response) {
        friendLinkTypeService.edit(friendLinkType);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 获取友情链接类型基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @GetMapping(value = "/{typeId}")
    public void viewFriendLinkType(@PathVariable("typeId") int typeId, HttpServletResponse response) {
        FriendLinkType friendLinkType = friendLinkTypeService.get(typeId);
        ResponseUtil.writeJson(response, friendLinkType);
    }

    /**
     * 删除友情链接类型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @PostMapping(value = "/del")
    public void removeFriendLinkType(HttpServletRequest request, HttpServletResponse response) {
        String memberIdStr = request.getParameter("ids");
        Integer[] memberIds = DataConvertUtils.convertToIntegerArray(memberIdStr.split(","));
        friendLinkTypeService.remove(memberIds);
        ResponseUtil.writeJson(response, "");
    }
}

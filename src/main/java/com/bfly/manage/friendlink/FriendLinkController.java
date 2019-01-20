package com.bfly.manage.friendlink;

import com.bfly.cms.friendlink.entity.FriendLink;
import com.bfly.cms.friendlink.service.IFriendLinkService;
import com.bfly.common.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 友情链接Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:53
 */
@RestController
@RequestMapping(value = "/manage/friendlink")
public class FriendLinkController extends BaseManageController {

    @Autowired
    private IFriendLinkService friendLinkService;

    /**
     * 友情链接列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:53
     */
    @GetMapping("/list")
    public void listFriendLink(HttpServletRequest request, HttpServletResponse response) {
        ContextUtil.initPager(request);
        Map<String, Object> property = new HashMap<String, Object>(3) {
            private static final long serialVersionUID = -9126101626116724049L;

            {
                put("category", request.getParameter("category"));
            }
        };
        Pager pager = friendLinkService.getPage(property);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 新增友情链接
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:57
     */
    @PostMapping(value = "/add")
    public void addFriendLink(@Valid FriendLink friendLink, BindingResult result, HttpServletResponse response) {
        validData(result);
        friendLinkService.save(friendLink);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 修改友情链接
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:01
     */
    @PostMapping(value = "/edit")
    public void editFriendLink(FriendLink friendLink, HttpServletResponse response) {
        friendLinkService.edit(friendLink);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 获取友情链接基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @GetMapping(value = "/{linkId}")
    public void viewFriendLink(@PathVariable("linkId") int linkId, HttpServletResponse response) {
        FriendLink friendLink = friendLinkService.get(linkId);
        ResponseUtil.writeJson(response, friendLink);
    }

    /**
     * 删除友情链接
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @PostMapping(value = "/del")
    public void removeFriendLink(HttpServletRequest request, HttpServletResponse response) {
        String friendLinkIdStr = request.getParameter("ids");
        Integer[] friendLinkIds = DataConvertUtils.convertToIntegerArray(friendLinkIdStr.split(","));
        friendLinkService.remove(friendLinkIds);
        ResponseUtil.writeJson(response, "");
    }
}

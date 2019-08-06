package com.bfly.manage.friendlink;

import com.bfly.cms.friendlink.entity.FriendLinkType;
import com.bfly.cms.friendlink.service.IFriendLinkTypeService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @ActionModel(value = "友情链接类型列表", need = false)
    public void listFriendLink(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Pager pager = friendLinkTypeService.getPage(null, null, getSortParam());
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 获得所有的类型集合
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 17:10
     */
    @GetMapping(value = "/all")
    @ActionModel(value = "获得所有友情链接类型集合", need = false)
    public void getAllFriendLink(HttpServletResponse response) {
        List<FriendLinkType> list = friendLinkTypeService.getList(null, null, getSortParam());
        ResponseUtil.writeJson(response, ResponseData.getSuccess(list));
    }

    /**
     * 排序Map
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 21:25
     */
    private Map<String, Sort.Direction> getSortParam() {
        Map<String, Sort.Direction> sortMap = new HashMap<>(1);
        sortMap.put("seq", Sort.Direction.ASC);
        return sortMap;
    }

    /**
     * 新增友情链接类型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:57
     */
    @PostMapping(value = "/add")
    @ActionModel(value = "新增友情链接类型")
    public void addFriendLink(@RequestBody @Valid FriendLinkType friendLinkType, BindingResult result, HttpServletResponse response) {
        validData(result);
        friendLinkTypeService.save(friendLinkType);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改友情链接类型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:01
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "修改友情链接类型")
    public void editFriendLink(@RequestBody @Valid FriendLinkType friendLinkType, BindingResult result, HttpServletResponse response) {
        validData(result);
        friendLinkTypeService.edit(friendLinkType);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获取友情链接类型基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @GetMapping(value = "/{typeId}")
    @ActionModel(value = "友情链接类型详情", need = false)
    public void viewFriendLinkType(@PathVariable("typeId") int typeId, HttpServletResponse response) {
        FriendLinkType friendLinkType = friendLinkTypeService.get(typeId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(friendLinkType));
    }

    /**
     * 删除友情链接类型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @PostMapping(value = "/del")
    @ActionModel(value = "删除友情链接类型")
    public void removeFriendLinkType(HttpServletResponse response, @RequestBody Integer... ids) {
        friendLinkTypeService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 友情链接类型排序
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/4 14:54
     */
    @GetMapping(value = "/sort/{upItemId}-{downItemId}")
    @ActionModel("友情链接类型排序")
    public void sortFriendLinkType(HttpServletResponse response, @PathVariable("upItemId") int upItemId, @PathVariable("downItemId") int downItemId) {
        friendLinkTypeService.sortFriendLinkType(upItemId, downItemId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}

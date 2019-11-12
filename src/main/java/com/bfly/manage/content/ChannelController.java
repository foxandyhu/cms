package com.bfly.manage.content;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.content.entity.Channel;
import com.bfly.cms.content.service.IChannelService;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 栏目Controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/4 15:08
 */
@RestController
@RequestMapping(value = "/manage/channel")
public class ChannelController extends BaseManageController {

    @Autowired
    private IChannelService channelService;

    /**
     * 栏目列表
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:10
     */
    @GetMapping(value = "/list")
    @ActionModel(value = "栏目列表", need = false)
    public void listChannel(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest(),100);

        Map<String, Object> exactMap = new HashMap<>(1);
        String parentId = getRequest().getParameter("parentId");
        if (parentId != null) {
            exactMap.put("parentId", DataConvertUtils.convertToInteger(parentId));
        }
        Map<String, Sort.Direction> sortMap = new HashMap<>(1);
        sortMap.put("seq", Sort.Direction.ASC);
        Pager pager = channelService.getPage(exactMap, null, sortMap);
        JSONObject json = JsonUtil.toJsonFilter(pager);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 所有栏目
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/5 18:45
     */
    @GetMapping(value = "/all")
    @ActionModel(value = "所有栏目信息", need = false)
    public void getAllChannel(HttpServletResponse response) {
        List<Map<String, Object>> list = channelService.getAllChannel();
        ResponseUtil.writeJson(response, ResponseData.getSuccess(list));
    }

    /**
     * 添加栏目
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:11
     */
    @PostMapping(value = "/add")
    @ActionModel("添加栏目")
    public void addChannel(@RequestBody @Valid Channel channel, BindingResult result, HttpServletResponse response) {
        validData(result);
        channelService.save(channel);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }


    /**
     * 修改栏目
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:11
     */
    @PostMapping(value = "/edit")
    @ActionModel("编辑栏目")
    public void editChannel(@RequestBody @Valid Channel channel, BindingResult result, HttpServletResponse response) {
        validData(result);
        channelService.edit(channel);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 查看栏目信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:12
     */
    @GetMapping(value = "/{channelId}")
    @ActionModel(value = "栏目详情", need = false)
    public void viewChannel(@PathVariable("channelId") int channelId, HttpServletResponse response) {
        Channel channel = channelService.get(channelId);
        JSONObject json = JsonUtil.toJsonFilter(channel, "child");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 删除栏目
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:13
     */
    @PostMapping(value = "/del")
    @ActionModel("删除栏目")
    public void delChannel(HttpServletResponse response, @RequestBody Integer... ids) {
        channelService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 栏目排序
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/4 14:54
     */
    @GetMapping(value = "/sort/{upItemId}-{downItemId}")
    @ActionModel("栏目排序")
    public void sortChannel(HttpServletResponse response, @PathVariable("upItemId") int upItemId, @PathVariable("downItemId") int downItemId) {
        channelService.sortChannel(upItemId, downItemId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}

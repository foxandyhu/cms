package com.bfly.manage.ad;

import com.alibaba.fastjson.JSONArray;
import com.bfly.cms.ad.entity.AdvertisingSpace;
import com.bfly.cms.ad.service.IAdvertisingSpaceService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告位管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:53
 */
@RestController
@RequestMapping(value = "/manage/advertising/space")
public class AdvertisingSpaceController extends BaseManageController {

    @Autowired
    private IAdvertisingSpaceService advertisingSpaceService;

    /**
     * 广告位列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:53
     */
    @GetMapping("/list")
    @ActionModel(value = "广告位列表", need = false)
    public void listAdvertisingSpace(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Pager pager = advertisingSpaceService.getPage(null);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 获得所有的广告位集合
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/18 12:49
     */
    @GetMapping(value = "/all")
    @ActionModel("获得所有的广告位集合")
    public void getAllSpace(HttpServletResponse response) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("enabled", true);
        List<AdvertisingSpace> list = advertisingSpaceService.getList(params);
        JSONArray array = JsonUtil.toJsonFilterForArray(list, "remark", "enabled");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(array));
    }

    /**
     * 新增广告位
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:57
     */
    @PostMapping(value = "/add")
    @ActionModel("新增广告位")
    public void addAdvertisingSpace(@RequestBody @Valid AdvertisingSpace advertisingSpace, BindingResult result, HttpServletResponse response) {
        validData(result);
        advertisingSpaceService.save(advertisingSpace);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改广告位
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:01
     */
    @PostMapping(value = "/edit")
    @ActionModel("编辑广告位")
    public void editAdvertisingSpace(@RequestBody @Valid AdvertisingSpace advertisingSpace, BindingResult result, HttpServletResponse response) {
        validData(result);
        advertisingSpaceService.edit(advertisingSpace);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获取广告位基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @GetMapping(value = "/{adId}")
    @ActionModel(value = "广告位详情", need = false)
    public void viewAdvertisingSpace(@PathVariable("adId") int adId, HttpServletResponse response) {
        AdvertisingSpace advertisingSpace = advertisingSpaceService.get(adId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(advertisingSpace));
    }

    /**
     * 删除广告位
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @PostMapping(value = "/del")
    @ActionModel("删除广告位")
    public void removeAdvertisingSpace(HttpServletResponse response, @RequestBody Integer... ids) {
        advertisingSpaceService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}

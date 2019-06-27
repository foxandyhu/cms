package com.bfly.manage.ad;

import com.bfly.cms.ad.entity.AdvertisingSpace;
import com.bfly.cms.ad.service.IAdvertisingSpaceService;
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
    public void listAdvertisingSpace(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Pager pager = advertisingSpaceService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 新增广告位
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:57
     */
    @PostMapping(value = "/add")
    public void addAdvertisingSpace(@Valid AdvertisingSpace advertisingSpace, BindingResult result, HttpServletResponse response) {
        validData(result);
        advertisingSpaceService.save(advertisingSpace);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 修改广告位
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:01
     */
    @PostMapping(value = "/edit")
    public void editAdvertisingSpace(AdvertisingSpace advertisingSpace, HttpServletResponse response) {
        advertisingSpaceService.edit(advertisingSpace);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 获取广告位基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @GetMapping(value = "/{adId}")
    public void viewAdvertisingSpace(@PathVariable("adId") int adId, HttpServletResponse response) {
        AdvertisingSpace advertisingSpace = advertisingSpaceService.get(adId);
        ResponseUtil.writeJson(response, advertisingSpace);
    }

    /**
     * 删除广告位
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @PostMapping(value = "/del")
    public void removeAdvertisingSpace(HttpServletRequest request, HttpServletResponse response) {
        String adIdStr = request.getParameter("ids");
        Integer[] adIds = DataConvertUtils.convertToIntegerArray(adIdStr.split(","));
        advertisingSpaceService.remove(adIds);
        ResponseUtil.writeJson(response, "");
    }
}

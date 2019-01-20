package com.bfly.manage.ad;

import com.bfly.cms.ad.entity.Advertising;
import com.bfly.cms.ad.service.IAdvertisingService;
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

/**
 * 广告管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:53
 */
@RestController
@RequestMapping(value = "/manage/advertising")
public class AdvertisingController extends BaseManageController {

    @Autowired
    private IAdvertisingService advertisingService;

    /**
     * 广告列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:53
     */
    @GetMapping("/list")
    public void listAdvertising(HttpServletRequest request, HttpServletResponse response) {
        ContextUtil.initPager(request);
        Pager pager = advertisingService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 新增广告
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:57
     */
    @PostMapping(value = "/add")
    public void addAdvertising(@Valid Advertising advertising, BindingResult result, HttpServletResponse response) {
        validData(result);
        advertisingService.save(advertising);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 修改广告
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:01
     */
    @PostMapping(value = "/edit")
    public void editAdvertising(Advertising advertising, HttpServletResponse response) {
        advertisingService.edit(advertising);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 获取广告基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @GetMapping(value = "/{adId}")
    public void viewAdvertising(@PathVariable("adId") int adId, HttpServletResponse response) {
        Advertising advertising = advertisingService.get(adId);
        ResponseUtil.writeJson(response, advertising);
    }

    /**
     * 删除广告
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @PostMapping(value = "/del")
    public void removeFriendLink(HttpServletRequest request, HttpServletResponse response) {
        String adIdStr = request.getParameter("ids");
        Integer[] adIds = DataConvertUtils.convertToIntegerArray(adIdStr.split(","));
        advertisingService.remove(adIds);
        ResponseUtil.writeJson(response, "");
    }
}

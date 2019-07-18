package com.bfly.manage.ad;

import com.bfly.cms.ad.entity.Advertising;
import com.bfly.cms.ad.service.IAdvertisingService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @ActionModel(value = "广告列表", need = false)
    public void listAdvertising(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Pager pager = advertisingService.getPage(null);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 新增广告
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:57
     */
    @PostMapping(value = "/add")
    @ActionModel("新增广告")
    public void addAdvertising(@RequestBody @Valid Advertising advertising, BindingResult result, HttpServletResponse response) {
        validData(result);
        advertisingService.save(advertising);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改广告
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:01
     */
    @PostMapping(value = "/edit")
    @ActionModel("修改广告")
    public void editAdvertising(@RequestBody @Valid Advertising advertising, BindingResult result, HttpServletResponse response) {
        validData(result);
        advertisingService.edit(advertising);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获取广告基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @GetMapping(value = "/{adId}")
    @ActionModel(value = "广告详情", need = false)
    public void viewAdvertising(@PathVariable("adId") int adId, HttpServletResponse response) {
        Advertising advertising = advertisingService.get(adId);
        if (advertising != null && advertising.getAttr() != null) {
            String adImg = advertising.getAttr().get("pic_url");
            if (StringUtils.hasLength(adImg)) {
                advertising.getAttr().put("pic_url", ResourceConfig.getServer() + adImg);
            }
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(advertising));
    }

    /**
     * 删除广告
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @PostMapping(value = "/del")
    @ActionModel("删除广告")
    public void removeFriendLink(HttpServletResponse response, @RequestBody Integer... ids) {
        advertisingService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}

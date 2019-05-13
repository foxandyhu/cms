package com.bfly.manage.system;

import com.bfly.cms.system.entity.OssInfo;
import com.bfly.cms.system.service.IOssInfoService;
import com.bfly.common.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 云存储配置Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:32
 */
@RestController
@RequestMapping(value = "/manage/oss")
public class OssInfoController extends BaseManageController {

    @Autowired
    private IOssInfoService ossInfoService;

    /**
     * Oss配置列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:32
     */
    @GetMapping(value = "/list")
    public void listOssInfo(HttpServletResponse response) {
        ContextUtil.initPager(getRequest());
        Pager pager = ossInfoService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 添加Oss配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:34
     */
    @PostMapping(value = "/add")
    public void addOssInfo(@Valid OssInfo ossInfo, BindingResult result, HttpServletResponse response) {
        validData(result);
        ossInfoService.save(ossInfo);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 编辑Oss配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:34
     */
    @PostMapping(value = "/edit")
    public void editOssInfo(@Valid OssInfo ossInfo,BindingResult result, HttpServletResponse response) {
        validData(result);
        ossInfoService.save(ossInfo);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * oss配置详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:35
     */
    @GetMapping(value = "/{ossInfoId}")
    public void viewOssInfo(@PathVariable("ossInfoId") int ossInfoId, HttpServletResponse response) {
        OssInfo ossInfo = ossInfoService.get(ossInfoId);
        ResponseUtil.writeJson(response, ossInfo);
    }

    /**
     * 删除Oss配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:36
     */
    @PostMapping(value = "/del")
    public void delOssInfo(HttpServletResponse response) {
        String ossInfoIdStr = getRequest().getParameter("ids");
        Integer[] ossInfoIds = DataConvertUtils.convertToIntegerArray(ossInfoIdStr.split(","));
        ossInfoService.remove(ossInfoIds);
        ResponseUtil.writeJson(response, "");
    }
}

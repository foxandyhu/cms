package com.bfly.manage.system;

import com.bfly.cms.system.entity.SysWaterMark;
import com.bfly.cms.system.service.ISysWaterMarkService;
import com.bfly.common.ResponseUtil;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 水印配置Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/20 9:34
 */
@RestController
@RequestMapping(value = "/manage/watermark")
public class SysWaterMarkController extends BaseManageController {

    @Autowired
    private ISysWaterMarkService waterMarkService;

    /**
     * 获得系统水印配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 9:35
     */
    @GetMapping(value = "/info")
    public void getWaterMark(HttpServletResponse response) {
        SysWaterMark waterMark = waterMarkService.getWaterMark();
        ResponseUtil.writeJson(response, waterMark);
    }

    /**
     * 编辑水印配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 9:44
     */
    @PostMapping(value = "/edit")
    public void editWaterMark(@Valid SysWaterMark waterMark, BindingResult result, HttpServletResponse response) {
        validData(result);
        waterMarkService.edit(waterMark);
        ResponseUtil.writeJson(response, "");
    }
}

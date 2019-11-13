package com.bfly.manage.system;

import com.bfly.cms.system.entity.SysWaterMark;
import com.bfly.cms.system.service.ISysWaterMarkService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @ActionModel(value = "查看水印设置", need = false)
    public void getWaterMark(HttpServletResponse response) {
        SysWaterMark waterMark = waterMarkService.getWaterMark();
        if (StringUtils.hasLength(waterMark == null ? null : waterMark.getImg())) {
            waterMark.setImg(ResourceConfig.getServer() + waterMark.getImg());
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(waterMark));
    }

    /**
     * 修改水印配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 9:44
     */
    @PostMapping(value = "/edit")
    @ActionModel("修改水印设置")
    public void editWaterMark(@RequestBody @Valid SysWaterMark waterMark, BindingResult result, HttpServletResponse response) {
        validData(result);
        waterMarkService.edit(waterMark);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}

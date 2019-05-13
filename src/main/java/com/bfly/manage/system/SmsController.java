package com.bfly.manage.system;

import com.bfly.cms.system.entity.Sms;
import com.bfly.cms.system.service.ISmsService;
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
 * 短信管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 11:55
 */
@RestController
@RequestMapping(value = "/manage/sms")
public class SmsController extends BaseManageController {

    @Autowired
    private ISmsService smsService;

    /**
     * 短信配置列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 11:58
     */
    @GetMapping(value = "/list")
    public void listSms(HttpServletResponse response) {
        ContextUtil.initPager(getRequest());
        Pager pager = smsService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 添加短信配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 12:00
     */
    @PostMapping(value = "/add")
    public void addSms(@Valid Sms sms, BindingResult result, HttpServletResponse response) {
        validData(result);
        smsService.save(sms);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 编辑短信配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 12:04
     */
    @PostMapping(value = "/edit")
    public void editSms(@Valid Sms sms,BindingResult result, HttpServletResponse response) {
        validData(result);
        smsService.edit(sms);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 短信配置详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 12:05
     */
    @GetMapping(value = "/{smsId}")
    public void viewSms(@PathVariable("smsId") int smsId, HttpServletResponse response) {
        Sms sms = smsService.get(smsId);
        ResponseUtil.writeJson(response, sms);
    }

    /**
     * 删除短信配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 12:06
     */
    @PostMapping(value = "/del")
    public void delSms(HttpServletResponse response) {
        String idStr = getRequest().getParameter("ids");
        Integer[] ids = DataConvertUtils.convertToIntegerArray(idStr.split(","));
        smsService.remove(ids);
        ResponseUtil.writeJson(response, "");
    }
}

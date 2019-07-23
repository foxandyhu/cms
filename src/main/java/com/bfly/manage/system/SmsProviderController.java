package com.bfly.manage.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.system.entity.SmsProvider;
import com.bfly.cms.system.service.ISmsProviderService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 短信管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 11:55
 */
@RestController
@RequestMapping(value = "/manage/sms/provider")
public class SmsProviderController extends BaseManageController {

    @Autowired
    private ISmsProviderService smsService;

    /**
     * 短信服务商列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 11:58
     */
    @GetMapping(value = "/list")
    @ActionModel(value = "短信服务商列表", need = false)
    public void listSmsProvider(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Pager pager = smsService.getPage(null);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 添加短信服务商
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 12:00
     */
    @PostMapping(value = "/add")
    @ActionModel(value = "新增短信服务商")
    public void addSmsProvider(@RequestBody @Valid SmsProvider sms, BindingResult result, HttpServletResponse response) {
        validData(result);
        smsService.save(sms);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改短信服务商
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 12:04
     */
    @PostMapping(value = "/edit")
    @ActionModel("修改短信服务商")
    public void editSms(@RequestBody @Valid SmsProvider sms, BindingResult result, HttpServletResponse response) {
        validData(result);
        smsService.edit(sms);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 短信服务商详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 12:05
     */
    @GetMapping(value = "/{smsId}")
    @ActionModel(value = "短信服务商详情", need = false)
    public void viewSms(@PathVariable("smsId") int smsId, HttpServletResponse response) {
        SmsProvider sms = smsService.get(smsId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(sms));
    }

    /**
     * 删除短信服务商
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 12:06
     */
    @PostMapping(value = "/del")
    @ActionModel("删除短信服务商")
    public void delSms(HttpServletResponse response, @RequestBody Integer... ids) {
        smsService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 查询所有的短信服务商---前端下拉框显示
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 11:01
     */
    @GetMapping(value = "/all")
    @ActionModel(value = "查询所有短信服务商", need = false)
    public void getProviderForSelect(HttpServletResponse response) {
        List<SmsProvider> list = smsService.getList();
        JSONArray array = new JSONArray();
        if (list != null) {
            JSONObject json;
            for (SmsProvider provider : list) {
                json = new JSONObject();
                json.put("id", provider.getId());
                json.put("name", provider.getName());
                array.add(json);
            }
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(array));
    }
}

package com.bfly.manage.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.system.entity.EmailProvider;
import com.bfly.cms.system.service.IEmailProviderService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统邮件Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/14 12:01
 */
@RestController
@RequestMapping(value = "/manage/email/provider")
public class EmailProviderController extends BaseManageController {

    @Autowired
    private IEmailProviderService emailService;

    /**
     * 系统邮件服务商列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:02
     */
    @GetMapping(value = "/list")
    @ActionModel(value = "邮件服务商列表", need = false)
    public void listEmailProvider(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Pager pager = emailService.getPage(null);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 获得所有的邮件服务商集合
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 13:28
     */
    @GetMapping("/all")
    @ActionModel(value = "所有的邮件服务商集合", need = false)
    public void getAllEmailProvier(HttpServletResponse response) {
        Map<String, Object> exactMap = new HashMap<>(1);
        exactMap.put("enable", true);
        List<EmailProvider> list = emailService.getList(exactMap);
        JSONArray array = new JSONArray();
        if (list != null) {
            JSONObject json;
            for (EmailProvider provider : list) {
                json = new JSONObject();
                json.put("id", provider.getId());
                json.put("name", provider.getName());
                array.add(json);
            }
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(array));
    }

    /**
     * 添加系统邮件服务商
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:05
     */
    @PostMapping(value = "/add")
    @ActionModel("添加邮件服务商")
    public void addEmailProvider(@RequestBody @Valid EmailProvider email, BindingResult result, HttpServletResponse response) {
        validData(result);
        emailService.save(email);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 编辑系统邮件
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:05
     */
    @PostMapping(value = "/edit")
    @ActionModel("编辑邮件服务商")
    public void editEmailProvider(@RequestBody @Valid EmailProvider email, BindingResult result, HttpServletResponse response) {
        validData(result);
        emailService.edit(email);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 查看系统邮件服务商详细信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:05
     */
    @GetMapping(value = "/{emailId}")
    @ActionModel(value = "查看邮件服务商详情", need = false)
    public void viewEmailProvider(@PathVariable("emailId") int emailId, HttpServletResponse response) {
        EmailProvider email = emailService.get(emailId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(email));
    }

    /**
     * 删除系统邮件服务商
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:07
     */
    @PostMapping(value = "/del")
    @ActionModel(value = "删除邮件服务商")
    public void removeEmailProvider(HttpServletResponse response, @RequestBody Integer... ids) {
        emailService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}

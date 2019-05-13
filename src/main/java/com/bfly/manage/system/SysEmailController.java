package com.bfly.manage.system;

import com.bfly.cms.system.entity.SysEmail;
import com.bfly.cms.system.service.ISysEmailService;
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
 * 系统邮件Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/14 12:01
 */
@RestController
@RequestMapping(value = "/manage/email")
public class SysEmailController extends BaseManageController {

    @Autowired
    private ISysEmailService emailService;

    /**
     * 系统邮件t列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:02
     */
    @GetMapping(value = "/list")
    public void listSysEmail(HttpServletResponse response) {
        ContextUtil.initPager(getRequest());
        Pager pager = emailService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 添加系统邮件
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:05
     */
    @PostMapping(value = "/add")
    public void addSysEmail(@Valid SysEmail email, BindingResult result, HttpServletResponse response) {
        validData(result);
        emailService.save(email);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 编辑系统邮件
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:05
     */
    @PostMapping(value = "/edit")
    public void editSysEmail(@Valid SysEmail email,BindingResult result, HttpServletResponse response) {
        validData(result);
        emailService.edit(email);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 查看系统邮件详细信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:05
     */
    @PostMapping(value = "/{emailId}")
    public void viewSysEmail(@PathVariable("emailId") int emailId, HttpServletResponse response) {
        SysEmail email = emailService.get(emailId);
        ResponseUtil.writeJson(response, email);
    }

    /**
     * 删除系统邮件
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:07
     */
    @PostMapping(value = "/del")
    public void removeSysEmail(HttpServletRequest request, HttpServletResponse response) {
        String idStr = request.getParameter("ids");
        Integer[] ids = DataConvertUtils.convertToIntegerArray(idStr.split(","));
        emailService.remove(ids);
        ResponseUtil.writeJson(response, "");
    }
}

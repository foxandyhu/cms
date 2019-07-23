package com.bfly.manage.system;

import com.bfly.cms.system.entity.SysFirewall;
import com.bfly.cms.system.service.ISysFirewallService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 系统防火墙设置controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/22 10:21
 */
@RestController
@RequestMapping(value = "/manage/firewall")
public class SysFirewallController extends BaseManageController {

    @Autowired
    private ISysFirewallService firewallService;

    /**
     * 查看系统防火墙设置
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 10:21
     */
    @GetMapping(value = "/info")
    @ActionModel(value = "查看防火墙设置",need = false)
    public void viewSysFirewall(HttpServletResponse response) {
        SysFirewall firewall = firewallService.getSysFirewall();
        ResponseUtil.writeJson(response, ResponseData.getSuccess(firewall));
    }

    /**
     * @author andy_hulibo@163.com
     * @date 2019/7/22 10:22
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "修改防火墙设置")
    public void editSysFirewall(@RequestBody @Valid SysFirewall firewall, BindingResult result, HttpServletResponse response) {
        validData(result);
        firewallService.edit(firewall);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}

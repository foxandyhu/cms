package com.bfly.manage.system;

import com.bfly.cms.system.entity.Company;
import com.bfly.cms.system.service.ICompanyService;
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
 * 系统相关信息Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/20 11:23
 */
@RestController
@RequestMapping(value = "/manage/system")
public class SysController extends BaseManageController {

    @Autowired
    private ICompanyService companyService;


    /**
     * 获得企业信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 11:43
     */
    @GetMapping(value = "/company/info")
    public void getCompany(HttpServletResponse response) {
        Company company = companyService.getCompany();
        ResponseUtil.writeJson(response, company);
    }

    /**
     * 编辑企业信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 11:47
     */
    @PostMapping(value = "/company/edit")
    public void editCompany(@Valid Company company, BindingResult result, HttpServletResponse response) {
        validData(result);
        companyService.edit(company);
        ResponseUtil.writeJson(response, "");
    }
}

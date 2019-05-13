package com.bfly.manage.system;

import com.bfly.cms.system.entity.SiteInfo;
import com.bfly.cms.system.service.ISiteInfoService;
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
 * 站点信息Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:09
 */
@RestController
@RequestMapping(value = "/manage/site")
public class SiteInfoController extends BaseManageController {

    @Autowired
    private ISiteInfoService siteInfoService;

    /**
     * 查看站点信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:10
     */
    @GetMapping(value = "/info")
    public void viewSiteInfo(HttpServletResponse response) {
        SiteInfo siteInfo = siteInfoService.getSite();
        ResponseUtil.writeJson(response, siteInfo);
    }

    /**
     * 编辑站点信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:14
     */
    @PostMapping(value = "/edit")
    public void editSiteInfo(@Valid SiteInfo siteInfo, BindingResult result, HttpServletResponse response) {
        validData(result);
        siteInfoService.edit(siteInfo);
        ResponseUtil.writeJson(response, "");
    }
}

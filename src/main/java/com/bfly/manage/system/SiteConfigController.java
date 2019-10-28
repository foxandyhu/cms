package com.bfly.manage.system;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.system.entity.SiteConfig;
import com.bfly.cms.system.service.ISiteConfigService;
import com.bfly.common.FileUtil;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
public class SiteConfigController extends BaseManageController {

    @Autowired
    private ISiteConfigService siteConfigService;

    /**
     * 查看站点信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:10
     */
    @GetMapping(value = "/info")
    @ActionModel(value = "查看站点配置信息", need = false)
    public void viewSiteConfig(HttpServletResponse response) {
        SiteConfig siteConfig = siteConfigService.getSite();

        if (siteConfig == null) {
            siteConfig = new SiteConfig();
        }
        JSONObject json = JsonUtil.toJsonFilter(siteConfig);
        json.put("pc", ResourceConfig.getTemplateNames("index", true));
        json.put("mobile", ResourceConfig.getTemplateNames("index", false));

        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 修改站点信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:14
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "修改站点配置信息")
    public void editSiteConfig(@RequestBody @Valid SiteConfig siteConfig, BindingResult result, HttpServletResponse response) {
        validData(result);
        siteConfigService.edit(siteConfig);
        ContextUtil.setSiteConfig(siteConfig, getRequest().getServletContext());
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}

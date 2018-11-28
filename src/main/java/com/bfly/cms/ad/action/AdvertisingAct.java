package com.bfly.cms.ad.action;

import com.bfly.cms.ad.entity.CmsAdvertising;
import com.bfly.cms.ad.entity.CmsAdvertisingSpace;
import com.bfly.cms.ad.service.CmsAdvertisingMng;
import com.bfly.cms.ad.service.CmsAdvertisingSpaceMng;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.core.web.util.CmsUtils;
import com.bfly.core.web.util.FrontUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.bfly.core.Constants.TPLDIR_CSI;

/**
 * 广告Action
 */
@Controller
public class AdvertisingAct {

    public static final String TPL_AD = "tpl.advertising";
    public static final String TPL_ADSPACE = "tpl.adspace";

    @RequestMapping(value = "/ad.html")
    public String ad(Integer id, HttpServletRequest request,
                     HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        if (id != null) {
            CmsAdvertising ad = cmsAdvertisingMng.findById(id);
            model.addAttribute("ad", ad);
        }
        FrontUtils.frontData(request, model, site);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_CSI, TPL_AD);
    }

    @RequestMapping(value = "/adspace.html")
    public String adspace(Integer id, HttpServletRequest request,
                          HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        if (id != null) {
            CmsAdvertisingSpace adspace = cmsAdvertisingSpaceMng.findById(id);
            List<CmsAdvertising> adList = cmsAdvertisingMng.getList(id, true);
            model.addAttribute("adspace", adspace);
            model.addAttribute("adList", adList);
        }
        FrontUtils.frontData(request, model, site);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_CSI, TPL_ADSPACE);
    }

    @RequestMapping(value = "/ad_display.html")
    public void display(Integer id, HttpServletRequest request,
                        HttpServletResponse response, ModelMap model) {
        if (id != null) {
            cmsAdvertisingMng.display(id);
        }
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    @RequestMapping(value = "/ad_click.html")
    public void click(Integer id, HttpServletRequest request,
                      HttpServletResponse response, ModelMap model) {
        if (id != null) {
            cmsAdvertisingMng.click(id);
        }
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    @Autowired
    private CmsAdvertisingMng cmsAdvertisingMng;
    @Autowired
    private CmsAdvertisingSpaceMng cmsAdvertisingSpaceMng;
}

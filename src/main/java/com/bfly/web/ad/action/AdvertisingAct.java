//package com.bfly.web.ad.action;
//
//import com.bfly.cms.ad.entity.Advertising;
//import com.bfly.cms.ad.entity.AdvertisingSpace;
//import com.bfly.cms.ad.service.CmsAdvertisingMng;
//import com.bfly.cms.ad.service.CmsAdvertisingSpaceMng;
//import com.bfly.core.base.action.RenderController;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//
///**
// * 广告Action
// *
// * @author andy_hulibo@163.com
// * @date 2018/11/29 15:36
// */
//@Controller
//public class AdvertisingAct extends RenderController {
//
//    @RequestMapping(value = "/ad.html")
//    public String ad(Integer id, ModelMap model) {
//        if (id != null) {
//            Advertising ad = cmsAdvertisingMng.findById(id);
//            model.addAttribute("ad", ad);
//        }
//        return renderPage("csi/advertising.html", model);
//    }
//
//    @RequestMapping(value = "/adspace.html")
//    public String adSpace(Integer id, ModelMap model) {
//        if (id != null) {
//            AdvertisingSpace adspace = cmsAdvertisingSpaceMng.findById(id);
//            List<Advertising> adList = cmsAdvertisingMng.getList(id, true);
//            model.addAttribute("adspace", adspace);
//            model.addAttribute("adList", adList);
//        }
//        return renderPage("csi/adspace.html", model);
//    }
//
//    @RequestMapping(value = "/ad_display.html")
//    public void display(Integer id, HttpServletResponse response) {
//        if (id != null) {
//            cmsAdvertisingMng.display(id);
//        }
//        response.setHeader("Pragma", "No-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//    }
//
//    @RequestMapping(value = "/ad_click.html")
//    public void click(Integer id, HttpServletResponse response) {
//        if (id != null) {
//            cmsAdvertisingMng.click(id);
//        }
//        response.setHeader("Pragma", "No-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//    }
//
//    @Autowired
//    private CmsAdvertisingMng cmsAdvertisingMng;
//    @Autowired
//    private CmsAdvertisingSpaceMng cmsAdvertisingSpaceMng;
//}

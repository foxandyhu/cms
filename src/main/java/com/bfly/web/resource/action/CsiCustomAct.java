//package com.bfly.web.resource.action;
//
//import com.bfly.common.web.RequestUtils;
//import com.bfly.core.base.action.RenderController;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// * 自定义客户端包含模板
// *
// * @author andy_hulibo@163.com
// * @date 2018/11/30 17:20
// */
//@Controller
//public class CsiCustomAct extends RenderController {
//
//    /**
//     * 解析至自定义模板页
//     *
//     * @author andy_hulibo@163.com
//     * @date 2018/11/30 17:20
//     */
//    @RequestMapping(value = "/csi_custom*.html")
//    public String custom(String tpl, ModelMap model) {
//        if (StringUtils.isNotBlank(tpl)) {
//            model.putAll(RequestUtils.getQueryParams(getRequest()));
//            return renderPagination("csi_custom/" + tpl + ".html", model);
//        }
//        return renderNotFoundPage(model);
//    }
//}

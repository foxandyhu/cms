//package com.bfly.web.words.action;
//
//import com.bfly.cms.words.entity.ContentTag;
//import com.bfly.cms.words.service.ContentTagMng;
//import com.bfly.core.base.action.RenderController;
//import com.bfly.core.web.util.FrontUtils;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
///**
// * 标签Controller
// *
// * @author andy_hulibo@163.com
// * @date 2018/11/30 9:56
// */
//@Controller
//public class TagAct extends RenderController {
//
//    @GetMapping(value = "/tag*.html")
//    public String index(ModelMap model) {
//        return renderPagination("special/tag_index.html", model);
//    }
//
//    @GetMapping(value = "/tag/{path}.html")
//    public String tags(@PathVariable String path, ModelMap model) {
//        if (StringUtils.isBlank(path)) {
//            return renderNotFoundPage(model);
//        }
//        int index = path.indexOf("_");
//        int pageNo, id;
//        try {
//            if (index != -1) {
//                id = Integer.valueOf(path.substring(0, index));
//                pageNo = Integer.valueOf(path.substring(index + 1, path.length()));
//            } else {
//                id = Integer.valueOf(path);
//                pageNo = 1;
//            }
//        } catch (NumberFormatException e) {
//            return renderNotFoundPage(model);
//        }
//        ContentTag tag = contentTagMng.findById(id);
//        if (tag == null) {
//            return renderNotFoundPage(model);
//        }
//        model.addAttribute("tag", tag);
//        model.addAttribute(FrontUtils.PAGE_NO, pageNo);
//        return renderPagination("special/tag_default.html", model);
//    }
//
//    @Autowired
//    private ContentTagMng contentTagMng;
//}

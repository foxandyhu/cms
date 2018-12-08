//package com.bfly.web.friendlink.action;
//
//import com.bfly.cms.friendlink.service.CmsFriendlinkMng;
//import com.bfly.common.web.ResponseUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 友情链接点击次数Action
// */
//@Controller
//public class FriendlinkAct {
//
//    @RequestMapping(value = "/friendlink_view.html", method = RequestMethod.GET)
//    public void view(Integer id, HttpServletResponse response) {
//        if (id != null) {
//            cmsFriendlinkMng.updateViews(id);
//            ResponseUtils.renderJson(response, "true");
//        } else {
//            ResponseUtils.renderJson(response, "false");
//        }
//    }
//
//    @Autowired
//    private CmsFriendlinkMng cmsFriendlinkMng;
//}

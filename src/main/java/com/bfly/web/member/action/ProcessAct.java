//package com.bfly.web.member.action;
//
//import com.bfly.cms.member.entity.MemberAuthentication;
//import com.bfly.cms.user.service.AuthenticationMng;
//import com.bfly.common.web.RequestUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import static com.bfly.cms.user.service.AuthenticationMng.AUTH_KEY;
//
///**
// * @author andy_hulibo@163.com
// * @date 2018/11/28 16:15
// * 登录处理Action
// * <p>
// * 登录成功后的处理类
// */
//@Controller
//public class ProcessAct {
//
//    private static Logger log = LoggerFactory.getLogger(ProcessAct.class);
//    public static final String RETURN_URL = "returnUrl";
//
//    @RequestMapping(value = "/process.html", method = RequestMethod.GET)
//    public String process(HttpServletRequest request,
//                          HttpServletResponse response) {
//        String returnUrl = RequestUtils.getQueryParam(request, RETURN_URL);
//        String authId = RequestUtils.getQueryParam(request, AUTH_KEY);
//        MemberAuthentication auth = authMng.retrieve(authId);
//        if (auth != null) {
//            authMng.storeAuthIdToSession(request, response, auth.getId());
//        } else {
//            log.warn("MemberAuthentication id not found: {}", authId);
//        }
//        return "redirect:" + returnUrl;
//    }
//
//    @Autowired
//    private AuthenticationMng authMng;
//}

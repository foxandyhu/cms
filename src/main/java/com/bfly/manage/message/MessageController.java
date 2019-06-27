package com.bfly.manage.message;

import com.bfly.cms.message.service.IMessageService;
import com.bfly.core.context.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.DateUtil;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 站内信Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:03
 */
@RestController
@RequestMapping(value = "/manage/message/")
public class MessageController extends BaseManageController {

    @Autowired
    private IMessageService messageService;

    /**
     * 站内信列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:03
     */
    @GetMapping("/list")
    public void listMessage(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> property = new HashMap<String, Object>(3) {
            private static final long serialVersionUID = -9126101626116724049L;

            {
                put("beginSendTime", DateUtil.parseStrDate(request.getParameter("beginSendTime")));
                put("endSendTime", DateUtil.parseStrDate(request.getParameter("endSendTime")));
                put("title", request.getParameter("title"));
                put("box", DataConvertUtils.convertToInteger(request.getParameter("box")));
                put("read", DataConvertUtils.convertToBoolean(request.getParameter("read")));
            }
        };
        Pager pager = messageService.getPage(property);
        ResponseUtil.writeJson(response, pager);
    }
}

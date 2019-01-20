package com.bfly.manage.system;

import com.bfly.cms.system.service.ISmsRecordService;
import com.bfly.common.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.DateUtil;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 短信记录管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 11:55
 */
@RestController
@RequestMapping(value = "/manage/sms/record")
public class SmsRecordController extends BaseManageController {

    @Autowired
    private ISmsRecordService smsRecordService;

    /**
     * 短信记录列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 11:58
     */
    @GetMapping(value = "/list")
    public void listSmsRecord(HttpServletResponse response) {
        ContextUtil.initPager(getRequest());
        Pager pager = smsRecordService.getPage(new HashMap<String, Object>(6) {
            private static final long serialVersionUID = -6334001390722788667L;

            {
                put("phone", getRequest().getParameter("phone"));
                put("member.username", getRequest().getParameter("username"));
                put("beginTime", DateUtil.parseStrDate(getRequest().getParameter("beginTime")));
                put("endTime", DateUtil.parseStrDate(getRequest().getParameter("endTime")));
                put("type", DataConvertUtils.convertToInteger(getRequest().getParameter("type")));
                put("sms", DataConvertUtils.convertToInteger(getRequest().getParameter("sms")));
                put("status", DataConvertUtils.convertToInteger(getRequest().getParameter("status")));
            }
        });
        ResponseUtil.writeJson(response, pager);
    }


    /**
     * 删除短信记录
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 12:06
     */
    @PostMapping(value = "/del")
    public void delSmsRecord(HttpServletResponse response) {
        String idStr = getRequest().getParameter("ids");
        Integer[] ids = DataConvertUtils.convertToIntegerArray(idStr.split(","));
        smsRecordService.remove(ids);
        ResponseUtil.writeJson(response, "");
    }
}

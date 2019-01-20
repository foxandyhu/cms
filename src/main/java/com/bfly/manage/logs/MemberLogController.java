package com.bfly.manage.logs;

import com.bfly.cms.logs.service.IMemberLogService;
import com.bfly.common.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.enums.LogsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 会员日志Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/10 16:38
 */
@RestController
@RequestMapping(value = "/manage/logs/member")
public class MemberLogController extends BaseManageController {

    @Autowired
    private IMemberLogService memberLogService;

    /**
     * 日志列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 16:39
     */
    @PostMapping(value = "/list")
    public void listMemberLog(HttpServletRequest request, HttpServletResponse response) {
        ContextUtil.initPager(request);
        Pager pager = memberLogService.getPage(new HashMap<String, Object>() {
            private static final long serialVersionUID = 7479394923131466430L;

            {
                put("username", request.getParameter("username"));
                put("title", request.getParameter("title"));
                put("ip", request.getParameter("ip"));

                LogsType logsType = LogsType.get(DataConvertUtils.convertToInteger(request.getParameter("category")));
                put("category", logsType == null ? null : logsType.getId());
            }
        });
        ResponseUtil.writeJson(response, pager);
    }
}

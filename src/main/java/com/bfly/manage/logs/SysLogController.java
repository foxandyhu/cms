package com.bfly.manage.logs;

import com.bfly.cms.logs.service.ISysLogService;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.LogsType;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 系统日志Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/10 16:38
 */
@RestController
@RequestMapping(value = "/manage/logs/sys")
public class SysLogController extends BaseManageController {

    @Autowired
    private ISysLogService sysLogService;

    /**
     * 日志列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 16:39
     */
    @GetMapping(value = "/list")
    @ActionModel(value = "系统日志列表", need = false)
    public void listSysLog(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        int category = DataConvertUtils.convertToInteger(request.getParameter("category"));
        LogsType type = LogsType.get(category);
        Pager pager = sysLogService.getPage(new HashMap<String, Object>(3) {
            private static final long serialVersionUID = 7479394923131466430L;

            {
                String userName = request.getParameter("userName");
                if (userName != null) {
                    put("userName", userName);
                }
                String title = request.getParameter("title");
                if (title != null) {
                    put("title", title);
                }
                String ip = request.getParameter("ip");
                if (ip != null) {
                    put("ip", ip);
                }
                if (type != null) {
                    put("category", type.getId());
                }
            }
        });
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 删除系统日志
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/28 13:45
     */
    @PostMapping(value = "/del")
    @ActionModel(value = "删除系统日志")
    public void delSysLog(HttpServletResponse response, @RequestBody Integer... sysId) {
        sysLogService.remove(sysId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}

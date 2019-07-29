package com.bfly.manage.system;

import com.bfly.cms.system.service.ISysTaskService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 系统任务Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/14 12:01
 */
@RestController
@RequestMapping(value = "/manage/task")
public class SysTaskController extends BaseManageController {

    @Autowired
    private ISysTaskService sysTaskService;

    /**
     * 系统任务列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:02
     */
    @GetMapping(value = "/list")
    @ActionModel(value = "系统任务列表", need = false)
    public void listSysTask(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Pager pager = sysTaskService.getPage(null);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 启动系统任务
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 12:29
     */
    @GetMapping(value = "/start/{name}.html")
    @ActionModel("启动系统任务")
    public void startTask(@PathVariable("name") String name, HttpServletResponse response) {
        sysTaskService.startTask(name);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 停止系统任务
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 12:30
     */
    @GetMapping(value = "/stop/{name}.html")
    @ActionModel("停止系统任务")
    public void stopTask(@PathVariable("name") String name, HttpServletResponse response) {
        sysTaskService.stopTask(name);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}

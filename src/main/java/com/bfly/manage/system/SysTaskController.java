package com.bfly.manage.system;

import com.bfly.cms.system.entity.SysTask;
import com.bfly.cms.system.service.ISysTaskService;
import com.bfly.core.context.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
    public void listSysTask(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Pager pager = sysTaskService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 添加系统任务
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:05
     */
    @PostMapping(value = "/add")
    public void addSysTask(@Valid SysTask task, BindingResult result, HttpServletResponse response) {
        validData(result);
        sysTaskService.save(task);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 编辑系统任务
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:05
     */
    @PostMapping(value = "/edit")
    public void editSysTask(@Valid SysTask task,BindingResult result, HttpServletResponse response) {
        validData(result);
        sysTaskService.edit(task);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 查看系统任务详细信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:05
     */
    @PostMapping(value = "/{taskId}")
    public void viewSysTask(@PathVariable("taskId") int taskId, HttpServletResponse response) {
        SysTask task = sysTaskService.get(taskId);
        ResponseUtil.writeJson(response, task);
    }

    /**
     * 删除系统任务
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 12:07
     */
    @PostMapping(value = "/del")
    public void removeSysTask(HttpServletRequest request, HttpServletResponse response) {
        String taskIdStr = request.getParameter("ids");
        Integer[] taskIds = DataConvertUtils.convertToIntegerArray(taskIdStr.split(","));
        sysTaskService.remove(taskIds);
        ResponseUtil.writeJson(response, "");
    }
}

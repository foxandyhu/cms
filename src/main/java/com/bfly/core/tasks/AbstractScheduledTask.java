package com.bfly.core.tasks;

import com.bfly.cms.system.entity.SysTask;
import com.bfly.cms.system.service.ISysTaskService;
import com.bfly.core.enums.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 抽象的计划任务类,提供了公共的函数使用
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/14 11:40
 */
@Component
public abstract class AbstractScheduledTask {

    @Autowired
    private ISysTaskService taskService;
    @Autowired
    private ApplicationContext context;

    /**
     * 计划任务执行完毕后的事件触发
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/14 11:42
     */
    public void executeCompletedEvent(ScheduledTaskExecCompleteEvent event) {
        context.publishEvent(event);
    }

    /**
     * 判断任务是否允许运行
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/28 15:06
     */
    public boolean allowRun(String taskName) {
        SysTask task = taskService.getTask(taskName);
        return task.getStatus() == TaskStatus.START.getId();
    }
}

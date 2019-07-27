package com.bfly.core.tasks;

import org.springframework.context.ApplicationEvent;

import java.util.Date;

/**
 * 计划任务执行完毕后事件
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/27 20:24
 */
public class ScheduledTaskExecCompleteEvent extends ApplicationEvent {

    /**
     * 任务名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 20:26
     */
    private String taskName;

    /**
     * 执行完时间
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 20:26
     */
    private Date completeDate;

    /**
     * 正则表达式
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 20:34
     */
    private String cron;

    public ScheduledTaskExecCompleteEvent(Object source) {
        super(source);
    }

    public ScheduledTaskExecCompleteEvent(String name, String cron, Date completeDate) {
        super(name);
        this.taskName = name;
        this.completeDate = completeDate;
        this.cron = cron;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getCron() {
        return cron;
    }

    public Date getCompleteDate() {
        return completeDate;
    }
}

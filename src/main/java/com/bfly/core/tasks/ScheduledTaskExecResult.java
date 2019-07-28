package com.bfly.core.tasks;

import java.util.Date;

/**
 * 任务执行结果
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/28 9:08
 */
public class ScheduledTaskExecResult {
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

    /**
     * 执行结果
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/28 9:03
     */
    private String execResult;

    public ScheduledTaskExecResult(String name, String cron, Date completeDate, String result) {
        this.taskName = name;
        this.completeDate = completeDate;
        this.cron = cron;
        this.execResult = result;
    }

    public String getExecResult() {
        return execResult;
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
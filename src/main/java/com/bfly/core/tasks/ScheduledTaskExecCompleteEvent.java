package com.bfly.core.tasks;

import org.springframework.context.ApplicationEvent;

/**
 * 计划任务执行完毕后事件
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/27 20:24
 */
public class ScheduledTaskExecCompleteEvent extends ApplicationEvent {

    private ScheduledTaskExecResult execResult;

    public ScheduledTaskExecCompleteEvent(ScheduledTaskExecResult source) {
        super(source);
        this.execResult = source;
    }

    public ScheduledTaskExecResult getExecResult() {
        return execResult;
    }


}

package com.bfly.core.tasks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 计划任务信息注解
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/27 19:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface ScheduledInfo {

    /**
     * 计划任务名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 19:08
     */
    String name();

    /**
     * 计划任务描述
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 19:09
     */
    String remark();
}

package com.bfly.core.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于描述类和方法的作用
 *
 * @author 胡礼波
 * 2012-11-1 上午11:09:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface ActionModel {
    String value() default "";

    /**
     * 是否需要记录日志
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:40
     */
    boolean need() default true;
}

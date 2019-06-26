package com.bfly.core.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否登录注解
 *
 * @author andy_hulibo@163.com
 * @date 2019/6/24 12:13
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
    boolean required() default true;
}

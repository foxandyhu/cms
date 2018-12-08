package com.bfly.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解注释在方法上用于标识请求的方法是否要做Sign校验
 * 主要用于管理员后端请求
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/6 15:10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SignValidate {

    boolean require() default true;

}
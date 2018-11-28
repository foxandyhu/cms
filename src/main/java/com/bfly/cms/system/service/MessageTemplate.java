package com.bfly.cms.system.service;

/**
 * 邮件模板业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 17:07
 */
public interface MessageTemplate {

    /**
     * 获得找回密码主题消息模板
     *
     * @return 消息模板
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:07
     */
    String getForgotPasswordSubject();

    /**
     * 获得找回密码内容消息模板
     *
     * @return 消息模板
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:08
     */
    String getForgotPasswordText();

    /**
     * 获得会员注册主题消息模板
     *
     * @return 消息模板
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:08
     */
    String getRegisterSubject();

    /**
     * 获得会员注册内容消息模板
     *
     * @return 消息模板
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:08
     */
    String getRegisterText();
}

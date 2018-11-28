package com.bfly.cms.system.entity;

/**
 * 邮件发送者
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 16:55
 */
public interface EmailSender {

    /**
     * 获得发送服务器IP
     *
     * @return IP
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:55
     */
    String getHost();

    /**
     * 发送服务器端口
     *
     * @return 端口
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:56
     */
    Integer getPort();

    /**
     * 获得发送编码
     *
     * @return 编码
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:56
     */
    String getEncoding();

    /**
     * 登录名
     *
     * @return 登录名
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:56
     */
    String getUsername();

    /**
     * 密码
     *
     * @return 密码
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:56
     */
    String getPassword();

    /**
     * 发送人
     *
     * @return 发送人
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:57
     */
    String getPersonal();
}

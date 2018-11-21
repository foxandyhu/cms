package com.bfly.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 邮件配置
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 15:49
 */
@Embeddable
public class EmailConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 邮件发送服务器
     */
    @Column(name = "email_host")
    private String host;

    /**
     * 邮件发送编码
     */
    @Column(name = "email_encoding")
    private String encoding;

    /**
     * 邮箱用户名
     */
    @Column(name = "email_username")
    private String username;

    /**
     * 邮箱密码
     */
    @Column(name = "email_password")
    private String password;

    /**
     * 邮箱发件人
     */
    @Column(name = "email_personal")
    private String personal;


    public String getHost() {
        return host;
    }


    public void setHost(String host) {
        this.host = host;
    }


    public String getEncoding() {
        return encoding;
    }


    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }


}
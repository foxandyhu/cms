package com.bfly.cms.system.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 邮件配置
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 15:49
 */
@Entity
@Table(name = "sys_email")
public class SysEmail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 邮件发送服务器
     */
    @Column(name = "host")
    @NotBlank(message = "邮件服务器地址不能为空!")
    private String host;

    /**
     * 邮件发送编码
     */
    @Column(name = "encoding")
    @NotBlank(message = "邮件发送编码不能为空!")
    private String encoding;

    /**
     * 邮箱用户名
     */
    @Column(name = "username")
    @NotBlank(message = "邮件用户名不能为空!")
    private String username;

    /**
     * 邮箱密码
     */
    @Column(name = "password")
    @NotBlank(message = "邮件密码不能为空!")
    private String password;

    /**
     * 邮箱发件人
     */
    @Column(name = "personal")
    private String personal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
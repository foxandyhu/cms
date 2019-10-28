package com.bfly.cms.system.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 邮件服务商
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 15:49
 */
@Entity
@Table(name = "email_provider")
public class EmailProvider implements Serializable {

    private static final long serialVersionUID = 953963861888241541L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 邮件服务商名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/16 17:45
     */
    @Column(name = "name")
    @NotBlank(message = "邮件服务商名称不能为空!")
    private String name;

    /**
     * 邮件发送服务器
     */
    @Column(name = "host")
    @NotBlank(message = "邮件服务器地址不能为空!")
    private String host;

    /**
     * 邮件服务器端口
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 21:05
     */
    @Column(name = "port")
    private int port;

    /**
     * 协议
     * @author andy_hulibo@163.com
     * @date 2019/9/23 21:08
     */
    @Column(name = "protocol")
    private String protocol;

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
    private String userName;

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

    /**
     * 是否启用
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/16 17:46
     */
    @Column(name = "is_enable")
    private boolean enable;

    /**
     * 备注
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/16 17:47
     */
    @Column(name = "remark")
    private String remark;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
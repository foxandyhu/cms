package com.bfly.cms.system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * SMS短信服务配置类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 17:56
 */
@Entity
@Table(name = "sys_sms")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class Sms implements Serializable {

    private static final long serialVersionUID = 900667721702404931L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 消息服务名称
     */
    @Column(name = "name")
    @NotBlank(message = "短信服务名称不能为空!")
    private String name;

    /**
     * 短信接口用户名
     */
    @Column(name = "username")
    private String userName;

    /**
     * 短信接口密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 短信接口地址
     */
    @Column(name = "url")
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

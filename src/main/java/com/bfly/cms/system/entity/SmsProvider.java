package com.bfly.cms.system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * SMS短信服务供应商类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 17:56
 */
@Entity
@Table(name = "sms_provider")
public class SmsProvider implements Serializable {

    private static final long serialVersionUID = 900667721702404931L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 服务商名称
     */
    @Column(name = "name")
    @NotBlank(message = "短信服务商名称不能为空!")
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

    /**
     * 是否启用
     * @author andy_hulibo@163.com
     * @date 2019/7/16 15:22
     */
    @Column(name = "is_enable")
    private boolean enable;

    /**
     * 备注
     * @author andy_hulibo@163.com
     * @date 2019/7/16 15:24
     */
    @Column(name = "remark")
    private String remark;

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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}

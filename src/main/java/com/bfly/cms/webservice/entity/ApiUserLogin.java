package com.bfly.cms.webservice.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * API用户登录信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 10:05
 */
@Entity
@Table(name = "jc_api_user_login")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ApiUserLogin implements Serializable {
    private static final long serialVersionUID = 1L;
    public static Short USER_STATUS_LOGIN = 1;
    public static Short USER_STATUS_LOGOUT = 2;
    public static Short USER_STATUS_LOGOVERTIME = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_key")
    private String sessionKey;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 登陆时间
     */
    @Column(name = "login_time")
    private Date loginTime;

    /**
     * 登陆次数
     */
    @Column(name = "login_count")
    private Integer loginCount;

    /**
     * 最后活跃时间
     */
    @Column(name = "active_time")
    private Date activeTime;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getSessionKey() {
        return sessionKey;
    }


    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }


    public Integer getLoginCount() {
        return loginCount;
    }


    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }


    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }


}
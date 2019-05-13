package com.bfly.cms.member.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 认证信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 15:22
 */
@Entity
@Table(name = "member_authenticate")
public class MemberAuthentication implements Serializable {

    private static final long serialVersionUID = 3913201719378729850L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户ID
     */
    @Column(name = "member_id")
    private int memberId;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 登录时间
     */
    @Column(name = "login_time")
    private Date loginTime;

    /**
     * 登录ip
     */
    @Column(name = "login_ip")
    private String loginIp;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
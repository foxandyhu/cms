package com.bfly.cms.user.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 系统管理员
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 20:51
 */
@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class User implements Serializable {

    private static final long serialVersionUID = -8442645414265352114L;

    /**
     * 账号禁用
     */
    public static final int DISABLE_STATUS = 2;

    /**
     * 账号待审核
     */
    public static final int UNCHECK_STATUS = 0;

    /**
     * 账号可用
     */
    public static final int AVAILABLE_STATUS = 1;

    /**
     * 为了区分Member表和User表的ID Member表的ID从1000开始递增
     * User表的ID从1开始递增
     */
    @Id
    @Column(name = "id")
    private int id;

    /**
     * 用户名
     */
    @Column(name = "username")
    @NotBlank(message = "用户名不能为空!")
    private String userName;

    /**
     * 密码
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/3 15:43
     */
    @NotBlank(message = "密码不能为空!")
    @Column(name = "password")
    private String password;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 注册时间
     */
    @Column(name = "register_time")
    private Date registerTime;

    /**
     * 注册IP
     */
    @Column(name = "register_ip")
    private String registerIp;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    @Column(name = "last_login_ip")
    private String lastLoginIp;


    /**
     * 管理员级别
     */
    @Column(name = "rank")
    private int rank;

    /**
     * 状态 1审核通过  2禁用  0待审核
     */
    @Column(name = "status")
    private int status;

    /**
     * 拥有的角色
     */
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @NotEmpty(message = "角色不能为空!")
    private Set<UserRole> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
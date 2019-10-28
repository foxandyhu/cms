package com.bfly.cms.user.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 系统管理员
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 20:51
 */
@Entity
@Table(name = "user")
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 用户名
     */
    @Column(name = "username")
    @NotBlank(message = "用户名不能为空!")
    @Size(min = 5, max = 15, message = "用户名长度必须在5到15之间!")
    private String userName;

    /**
     * 密码
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/3 15:43
     */
    @NotBlank(message = "密码不能为空!")
    @Column(name = "password")
    @Size(min = 6, max = 50, message = "密码长度必须在6到50之间!")
    private String password;

    /**
     * 邮箱
     */
    @Column(name = "email")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确!")
    @Size(min = 5, max = 30, message = "邮箱长度必须在5到30之间!")
    private String email;

    /**
     * 头像
     */
    @Column(name = "face")
    private String face;

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
     * 状态 1审核通过  2禁用  0待审核
     */
    @Column(name = "status")
    private int status;

    /**
     * 登录次数
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/1 14:51
     */
    @Column(name = "login_count")
    private long loginCount;

    /**
     * 拥有的角色
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role_ship", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @NotEmpty(message = "角色不能为空!")
    private List<UserRole> roles;

    /**
     * 是否是超级管理员
     * @author andy_hulibo@163.com
     * @date 2019/7/14 11:56
     */
    @Column(name = "is_super_admin")
    private boolean superAdmin;


    /**
     * 状态名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/30 20:01
     */
    public String getStatusName() {
        switch (getStatus()) {
            case AVAILABLE_STATUS:
                return "正常";
            case UNCHECK_STATUS:
                return "待审核";
            case DISABLE_STATUS:
                return "已禁用";
            default:
                return "";
        }
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(long loginCount) {
        this.loginCount = loginCount;
    }
}
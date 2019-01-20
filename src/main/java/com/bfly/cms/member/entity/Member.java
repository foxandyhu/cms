package com.bfly.cms.member.entity;


import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 用户类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 23:05
 */
@Entity
@Table(name = "member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1827879748275829762L;

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
     * 会员ID自增标识开始
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 13:44
     */
    public static final int MEMBER_ID_BEGIN = 1000;

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
    private String username;

    /**
     * 电子邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 密码
     */
    @Column(name = "password")
    @NotBlank(message = "密码不能为空!")
    private String password;

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
     * 登录次数
     */
    @Column(name = "login_count")
    private int loginCount;

    /**
     * 重置密码KEY
     */
    @Column(name = "reset_key")
    private String resetKey;

    /**
     * 重置密码VALUE
     */
    @Column(name = "reset_pwd")
    private String resetPwd;

    /**
     * 登录错误时间
     */
    @Column(name = "error_time")
    private Date errorTime;

    /**
     * 登录错误次数
     */
    @Column(name = "error_count")
    private int errorCount;

    /**
     * 登录错误IP
     */
    @Column(name = "error_ip")
    private String errorIp;

    /**
     * 激活状态
     */
    @Column(name = "is_activity")
    private boolean activity;

    /**
     * 激活码
     */
    @Column(name = "activationCode")
    private String activationCode;

    /**
     * 状态 0审核通过  1禁用  2待审核
     */
    @Column(name = "status")
    private int status;

    /**
     * session_id
     */
    @Column(name = "session_id")
    private String sessionId;

    /**
     * 所在组
     */
    @ManyToOne
    @JoinColumn(name = "group_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @NotNull(message = "会员未设置分组!")
    private MemberGroup group;

    /**
     * 用户扩展信息
     */
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "user")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private MemberExt userExt;

    /**
     * 用户绑定的第三方账号
     */
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "user")
    private Set<MemberThirdAccount> thirdAccounts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public String getResetPwd() {
        return resetPwd;
    }

    public void setResetPwd(String resetPwd) {
        this.resetPwd = resetPwd;
    }

    public Date getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public String getErrorIp() {
        return errorIp;
    }

    public void setErrorIp(String errorIp) {
        this.errorIp = errorIp;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public MemberGroup getGroup() {
        return group;
    }

    public void setGroup(MemberGroup group) {
        this.group = group;
    }

    public MemberExt getUserExt() {
        return userExt;
    }

    public void setUserExt(MemberExt userExt) {
        this.userExt = userExt;
    }

    public Set<MemberThirdAccount> getThirdAccounts() {
        return thirdAccounts;
    }

    public void setThirdAccounts(Set<MemberThirdAccount> thirdAccounts) {
        this.thirdAccounts = thirdAccounts;
    }
}
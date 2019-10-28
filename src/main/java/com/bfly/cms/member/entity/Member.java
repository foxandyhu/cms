package com.bfly.cms.member.entity;


import com.bfly.core.enums.MemberStatus;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    @NotBlank(message = "用户名不能为空!")
    private String userName;

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
     * 加密盐KEY
     */
    @Column(name = "salt")
    private String salt;

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
     * 状态
     *
     * @see com.bfly.core.enums.MemberStatus
     */
    @Column(name = "status")
    private int status;

    /**
     * 用户是否激活
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/25 11:54
     */
    @Column(name = "is_activated")
    private boolean activated;

    /**
     * SessionId
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/18 12:36
     */
    @Column(name = "session_id")
    private String sessionId;

    /**
     * 所在组
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @NotNull(message = "会员未设置分组!")
    private MemberGroup group;

    /**
     * 用户扩展信息
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
    @PrimaryKeyJoinColumn
    private MemberExt memberExt;

    /**
     * 用户绑定的第三方账号
     */
    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true, mappedBy = "user", fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(region = "beanCache", usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<MemberThirdAccount> thirdAccounts;

    /**
     * 状态名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/19 16:59
     */
    public String getStatusName() {
        MemberStatus status = MemberStatus.getStatus(getStatus());
        return status == null ? "" : status.getName();
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MemberGroup getGroup() {
        return group;
    }

    public void setGroup(MemberGroup group) {
        this.group = group;
    }

    public MemberExt getMemberExt() {
        return memberExt;
    }

    public void setMemberExt(MemberExt memberExt) {
        this.memberExt = memberExt;
    }

    public List<MemberThirdAccount> getThirdAccounts() {
        return thirdAccounts;
    }

    public void setThirdAccounts(List<MemberThirdAccount> thirdAccounts) {
        this.thirdAccounts = thirdAccounts;
    }
}
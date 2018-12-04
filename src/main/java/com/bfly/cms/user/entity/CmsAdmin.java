package com.bfly.cms.user.entity;

import com.bfly.cms.siteconfig.entity.CmsSite;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * CMS系统管理员
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 20:51
 */
@Entity
@Table(name = "d_admin")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 审核通过
     */
    public static final Integer USER_STATU_CHECKED = 0;

    /**
     * 禁用
     */
    public static final Integer USER_STATU_DISABLED = 1;

    /**
     * 待审核
     */
    public static final Integer USER_STATU_CHECKING = 2;

    @Id
    @Column(name = "user_id")
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/3 15:43
     */
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
    private Integer rank;


    /**
     * 是否只管理自己的数据
     */
    @Column(name = "is_self_admin")
    private Boolean selfAdmin;

    /**
     * 状态 0审核通过  1禁用  2待审核
     */
    @Column(name = "statu")
    private Integer statu;

    /**
     * session_id
     */
    @Column(name = "session_id")
    private String sessionId;


    @ManyToMany(mappedBy = "admins", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private Set<CmsRole> roles;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private Set<CmsUserSite> userSites;


    public Set<CmsSite> getSites() {
        if (getUserSites() != null) {
            Set<CmsUserSite> userSites = getUserSites();
            Set<CmsSite> sites = new HashSet<>(userSites.size());
            for (CmsUserSite us : userSites) {
                sites.add(us.getSite());
            }
            return sites;
        }
        return null;
    }

    public Integer[] getSiteIds() {
        Set<CmsSite> sites = getSites();
        return CmsSite.fetchIds(sites);
    }

    public Byte getCheckStep() {
        CmsUserSite us = getUserSite();
        if (us != null) {
            return getUserSite().getCheckStep();
        } else {
            return null;
        }
    }

    public CmsUserSite getUserSite() {
        Set<CmsUserSite> set = getUserSites();
        for (CmsUserSite us : set) {
            us.getSite();
        }
        return null;
    }

    public void addToRoles(CmsRole role) {
        if (role == null) {
            return;
        }
        Set<CmsRole> set = getRoles();
        if (set == null) {
            set = new HashSet<>();
            setRoles(set);
        }
        set.add(role);
    }


    public Integer[] getRoleIds() {
        Set<CmsRole> roles = getRoles();
        return CmsRole.fetchIds(roles);
    }

    public CmsRole getTopRole() {
        Set<CmsRole> roles = getRoles();
        CmsRole topRole = null;
        for (CmsRole r : roles) {
            topRole = r;
            if (r.getLevel() > topRole.getLevel()) {
                topRole = r;
            }
        }
        return topRole;
    }

    public Integer getTopRoleLevel() {
        CmsRole topRole = getTopRole();
        if (topRole != null) {
            return topRole.getLevel();
        } else {
            return 0;
        }
    }

    public boolean isSuper() {
        Set<CmsRole> roles = getRoles();
        if (roles == null) {
            return false;
        }
        for (CmsRole role : getRoles()) {
            if (role.getAll()) {
                return true;
            }
        }
        return false;
    }

    public Set<String> getPerms() {
        if (USER_STATU_DISABLED.equals(getStatu()) || getRoles() == null) {
            return null;
        }
        boolean isSuper = false;
        Set<String> allPerms = new HashSet<>();
        for (CmsRole role : getRoles()) {
            if (role.getAll()) {
                isSuper = true;
                break;
            }
            allPerms.addAll(role.getPerms());
        }
        if (isSuper) {
            allPerms.clear();
            allPerms.add("*");
        }
        return allPerms;
    }

    public String getPermStr() {
        if (USER_STATU_DISABLED.equals(getStatu())) {
            return "";
        }
        Set<CmsRole> roles = getRoles();
        if (roles == null) {
            return "";
        }
        boolean isSuper = false;
        StringBuffer permBuff = new StringBuffer();
        for (CmsRole role : getRoles()) {
            if (role.getAll()) {
                isSuper = true;
                break;
            }
            for (String s : role.getPerms()) {
                permBuff.append(s + ",");
            }
        }
        if (isSuper) {
            int sb_length = permBuff.length();
            permBuff.delete(0, sb_length);
            permBuff.append("*");
        }
        return permBuff.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Boolean getSelfAdmin() {
        return selfAdmin;
    }

    public void setSelfAdmin(Boolean selfAdmin) {
        this.selfAdmin = selfAdmin;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Set<CmsRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<CmsRole> roles) {
        this.roles = roles;
    }

    public Set<CmsUserSite> getUserSites() {
        return userSites;
    }

    public void setUserSites(Set<CmsUserSite> userSites) {
        this.userSites = userSites;
    }
}
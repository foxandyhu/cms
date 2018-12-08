package com.bfly.cms.member.entity;

import com.bfly.cms.channel.entity.Channel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 会员组类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 16:24
 */
@Entity
@Table(name = "member_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class MemberGroup implements Serializable {

    private static final long serialVersionUID = 6614006354622383908L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 名称
     */
    @Column(name = "group_name")
    private String name;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private int priority;

    /**
     * 每日允许上传KB
     */
    @Column(name = "allow_per_day")
    private int allowPerDay;

    /**
     * 每个文件最大KB
     */
    @Column(name = "allow_max_file")
    private int allowMaxFile;

    /**
     * 允许上传的后缀
     */
    @Column(name = "allow_suffix")
    private String allowSuffix;

    /**
     * 是否需要验证码
     */
    @Column(name = "need_captcha")
    private boolean needCaptcha;
    /**
     * 是否需要审核
     */
    @Column(name = "need_check")

    private boolean needCheck;

    /**
     * 是否默认会员组
     */
    @Column(name = "is_reg_def")
    private boolean regDef;

    /**
     * 浏览权限组
     */
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @JoinTable(name = "member_chnl_group_view", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "channel_id"))
    private Set<Channel> viewChannels;

    /**
     * 投稿权限组
     */
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @JoinTable(name = "member_chnl_group_contri", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "channel_id"))
    private Set<Channel> contriChannels;

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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getAllowPerDay() {
        return allowPerDay;
    }

    public void setAllowPerDay(int allowPerDay) {
        this.allowPerDay = allowPerDay;
    }

    public int getAllowMaxFile() {
        return allowMaxFile;
    }

    public void setAllowMaxFile(int allowMaxFile) {
        this.allowMaxFile = allowMaxFile;
    }

    public String getAllowSuffix() {
        return allowSuffix;
    }

    public void setAllowSuffix(String allowSuffix) {
        this.allowSuffix = allowSuffix;
    }

    public boolean isNeedCaptcha() {
        return needCaptcha;
    }

    public void setNeedCaptcha(boolean needCaptcha) {
        this.needCaptcha = needCaptcha;
    }

    public boolean isNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(boolean needCheck) {
        this.needCheck = needCheck;
    }

    public boolean isRegDef() {
        return regDef;
    }

    public void setRegDef(boolean regDef) {
        this.regDef = regDef;
    }

    public Set<Channel> getViewChannels() {
        return viewChannels;
    }

    public void setViewChannels(Set<Channel> viewChannels) {
        this.viewChannels = viewChannels;
    }

    public Set<Channel> getContriChannels() {
        return contriChannels;
    }

    public void setContriChannels(Set<Channel> contriChannels) {
        this.contriChannels = contriChannels;
    }
}
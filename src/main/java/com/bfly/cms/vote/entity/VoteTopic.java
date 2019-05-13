package com.bfly.cms.vote.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 投票主题
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:42
 */
@Entity
@Table(name = "vote_topic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class VoteTopic implements Serializable {

    private static final long serialVersionUID = 1933383282291172967L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 标题
     */
    @Column(name = "title")
    @NotBlank(message = "问卷标题不能为空!")
    private String title;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 重复投票限制时间，单位小时，为空不允许重复投票
     */
    @Column(name = "repeate_hour")
    private int repeateHour;

    /**
     * 总投票数
     */
    @Column(name = "total_count")
    private int totalCount;

    /**
     * 最多可以选择几项
     */
    @Column(name = "multi_select")
    private int multiSelect;

    /**
     * 是否限制会员
     */
    @Column(name = "is_restrict_member")
    private boolean restrictMember;

    /**
     * 是否限制IP
     */
    @Column(name = "is_restrict_ip")
    private boolean restrictIp;

    /**
     * 是否限制COOKIE
     */
    @Column(name = "is_restrict_cookie")
    private boolean restrictCookie;

    /**
     * 是否禁用
     */
    @Column(name = "is_disabled")
    private boolean disabled;

    /**
     * 是否默认主题
     */
    @Column(name = "is_def")
    private boolean def;

    /**
     * 是否限制微信
     */
    @Column(name = "limit_weixin")
    private boolean limitWeiXin;

    /**
     * 限定微信投票每个用户每日投票次数,为0时则投票期内限定投票一次
     */
    @Column(name = "vote_day")
    private int voteDay;

    /**
     * 投票项
     */
    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @NotEmpty(message = "投票项不能为空!")
    private Set<VoteItem> items;

    /**
     * 子主题
     */
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "voteTopic")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @NotEmpty(message = "子主题项不能为空!")
    private Set<VoteSubTopic> subtopics;

    /**
     * 投票记录
     */
    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private Set<VoteRecord> records;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getRepeateHour() {
        return repeateHour;
    }

    public void setRepeateHour(int repeateHour) {
        this.repeateHour = repeateHour;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(int multiSelect) {
        this.multiSelect = multiSelect;
    }

    public boolean isRestrictMember() {
        return restrictMember;
    }

    public void setRestrictMember(boolean restrictMember) {
        this.restrictMember = restrictMember;
    }

    public boolean isRestrictIp() {
        return restrictIp;
    }

    public void setRestrictIp(boolean restrictIp) {
        this.restrictIp = restrictIp;
    }

    public boolean isRestrictCookie() {
        return restrictCookie;
    }

    public void setRestrictCookie(boolean restrictCookie) {
        this.restrictCookie = restrictCookie;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    public boolean isLimitWeiXin() {
        return limitWeiXin;
    }

    public void setLimitWeiXin(boolean limitWeiXin) {
        this.limitWeiXin = limitWeiXin;
    }

    public int getVoteDay() {
        return voteDay;
    }

    public void setVoteDay(int voteDay) {
        this.voteDay = voteDay;
    }

    public Set<VoteItem> getItems() {
        return items;
    }

    public void setItems(Set<VoteItem> items) {
        this.items = items;
    }

    public Set<VoteSubTopic> getSubtopics() {
        return subtopics;
    }

    public void setSubtopics(Set<VoteSubTopic> subtopics) {
        this.subtopics = subtopics;
    }

    public Set<VoteRecord> getRecords() {
        return records;
    }

    public void setRecords(Set<VoteRecord> records) {
        this.records = records;
    }
}
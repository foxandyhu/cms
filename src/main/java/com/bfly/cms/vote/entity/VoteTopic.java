package com.bfly.cms.vote.entity;

import com.bfly.common.IDEncrypt;
import com.bfly.core.enums.VoteStatus;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 投票主题
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:42
 */
@Entity
@Table(name = "vote_topic")
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
    @Size(min = 1, max = 50, message = "问卷标题长度为1-50之间!")
    private String title;

    /**
     * 描述
     */
    @Column(name = "remark")
    @Size(max = 250, message = "描述内容长度在0-250之间!")
    private String remark;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    @NotNull(message = "开始时间不能为空!")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    @NotNull(message = "结束时间不能为空!")
    private Date endTime;

    /**
     * 重复投票限制时间，单位小时，0为禁止重复投票,-1为无限制重复投票
     */
    @Column(name = "repeat_hour")
    @Min(value = -1, message = "重复投票限制最小为-1")
    private int repeatHour;

    /**
     * 总投票数
     */
    @Column(name = "total_count")
    private int totalCount;


    /**
     * 是否需要登录
     */
    @Column(name = "is_need_login")
    private boolean needLogin;

    /**
     * 是否启用
     */
    @Column(name = "is_enabled")
    private boolean enabled;

    /**
     * 状态
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/29 15:59
     * @see VoteStatus
     */
    private int status;

    /**
     * 子主题
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @NotEmpty(message = "子主题项不能为空!")
    @JoinColumn(name = "vote_topic_id")
    @OrderBy("seq")
    private List<VoteSubTopic> subtopics;

    /**
     * 状态名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/29 16:12
     */
    public String getStatusName() {
        VoteStatus status = VoteStatus.getStatus(getStatus());
        return status == null ? "" : status.getName();
    }

    /**
     * 加密后ID
     * @author andy_hulibo@163.com
     * @date 2019/9/11 17:56
     */
    public String getIdStr(){
        return IDEncrypt.encode(getId());
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public int getRepeatHour() {
        return repeatHour;
    }

    public void setRepeatHour(int repeatHour) {
        this.repeatHour = repeatHour;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(boolean needLogin) {
        this.needLogin = needLogin;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<VoteSubTopic> getSubtopics() {
        return subtopics;
    }

    public void setSubtopics(List<VoteSubTopic> subtopics) {
        this.subtopics = subtopics;
    }
}
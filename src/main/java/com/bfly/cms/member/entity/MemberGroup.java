package com.bfly.cms.member.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 会员组类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 16:24
 */
@Entity
@Table(name = "member_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class MemberGroup implements Serializable, Comparable<MemberGroup> {

    private static final long serialVersionUID = 6614006354622383908L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 名称
     */
    @Column(name = "name")
    @NotBlank(message = "组名称不能为空!")
    private String name;

    /**
     * 排列顺序
     */
    @Column(name = "seq")
    @Min(value = 0, message = "排列顺序最小为0!")
    private int seq;

    /**
     * 每日允许上传总量KB
     */
    @Column(name = "allow_upload_per_day")
    @Min(value = 0, message = "每日允许上传量必须大于0KB!")
    private int allowUploadPerDay;

    /**
     * 每个文件最大总量KB
     */
    @Column(name = "allow_upload_max_file")
    @Min(value = 0, message = "每个文件最大值为0KB!")
    private int allowUploadMaxFile;

    /**
     * 允许上传的文件后缀
     */
    @Column(name = "allow_upload_suffix")
    private String allowUploadSuffix;

    /**
     * 评论是否需要验证码
     */
    @Column(name = "comment_need_captcha")
    private boolean commentNeedCaptcha;
    /**
     * 评论是否需要审核
     */
    @Column(name = "comment_need_check")

    private boolean commentNeedCheck;

    /**
     * 是否默认会员组
     */
    @Column(name = "is_default")
    private boolean defaults;

    @Override
    public int compareTo(MemberGroup o) {
        return this.getSeq() - o.getSeq();
    }

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

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getAllowUploadPerDay() {
        return allowUploadPerDay;
    }

    public void setAllowUploadPerDay(int allowUploadPerDay) {
        this.allowUploadPerDay = allowUploadPerDay;
    }

    public int getAllowUploadMaxFile() {
        return allowUploadMaxFile;
    }

    public void setAllowUploadMaxFile(int allowUploadMaxFile) {
        this.allowUploadMaxFile = allowUploadMaxFile;
    }

    public String getAllowUploadSuffix() {
        return allowUploadSuffix;
    }

    public void setAllowUploadSuffix(String allowUploadSuffix) {
        this.allowUploadSuffix = allowUploadSuffix;
    }

    public boolean isCommentNeedCaptcha() {
        return commentNeedCaptcha;
    }

    public void setCommentNeedCaptcha(boolean commentNeedCaptcha) {
        this.commentNeedCaptcha = commentNeedCaptcha;
    }

    public boolean isCommentNeedCheck() {
        return commentNeedCheck;
    }

    public void setCommentNeedCheck(boolean commentNeedCheck) {
        this.commentNeedCheck = commentNeedCheck;
    }

    public boolean isDefaults() {
        return defaults;
    }

    public void setDefaults(boolean defaults) {
        this.defaults = defaults;
    }
}
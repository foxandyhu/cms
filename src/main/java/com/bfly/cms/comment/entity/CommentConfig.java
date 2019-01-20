package com.bfly.cms.comment.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * 评论配置
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/20 9:55
 */
@Entity
@Table(name = "comment_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CommentConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 是否开启评论
     */
    @Column(name = "is_open_comment")
    private boolean openComment;

    /**
     * 是否登录才能评论
     */
    @Column(name = "is_allow_login_comment")
    private boolean allowLoginComment;

    /**
     * 日评论数限制 0则不限制
     */
    @Column(name = "day_comment_limit")
    @Min(value = 0, message = "日评论数不正确!")
    private int dayCommentLimit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAllowLoginComment() {
        return allowLoginComment;
    }

    public void setAllowLoginComment(boolean allowLoginComment) {
        this.allowLoginComment = allowLoginComment;
    }

    public int getDayCommentLimit() {
        return dayCommentLimit;
    }

    public void setDayCommentLimit(int dayCommentLimit) {
        this.dayCommentLimit = dayCommentLimit;
    }

    public boolean isOpenComment() {
        return openComment;
    }

    public void setOpenComment(boolean openComment) {
        this.openComment = openComment;
    }
}

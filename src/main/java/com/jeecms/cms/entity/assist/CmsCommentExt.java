package com.jeecms.cms.entity.assist;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS评论扩展
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:59
 */
@Entity
@Table(name = "jc_comment_ext")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsCommentExt implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "comment_id")
    private Integer id;

    /**
     * IP地址
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 评论内容
     */
    @Column(name = "text")
    private String text;

    /**
     * 回复内容
     */
    @Column(name = "reply")
    private String reply;

    @Id
    @OneToOne
    @JoinColumn(name = "comment_id")
    private CmsComment comment;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReply() {
        return reply;
    }


    public void setReply(String reply) {
        this.reply = reply;
    }


    public CmsComment getComment() {
        return comment;
    }


    public void setComment(CmsComment comment) {
        this.comment = comment;
    }


}
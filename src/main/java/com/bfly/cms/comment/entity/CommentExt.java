package com.bfly.cms.comment.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 文章评论扩展信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:59
 */
@Entity
@Table(name = "comment_ext")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CommentExt implements Serializable {

    private static final long serialVersionUID = 3035819854676694933L;

    @Id
    @Column(name = "comment_id", unique = true, nullable = false)
    private int id;

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

    /**
     * 所属评论
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "comment_id")
    private Comment comment;


    public int getId() {
        return id;
    }

    public void setId(int id) {
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


    public Comment getComment() {
        return comment;
    }


    public void setComment(Comment comment) {
        this.comment = comment;
    }


}
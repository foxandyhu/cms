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
     * 所属评论
     */
    @OneToOne(mappedBy = "commentExt")
    @MapsId
    private Comment comment;

    /**
     * IP地址
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 地址区域
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/2 12:10
     */
    @Column(name = "area")
    private String area;

    /**
     * 评论内容
     */
    @Column(name = "text")
    private String text;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }


}
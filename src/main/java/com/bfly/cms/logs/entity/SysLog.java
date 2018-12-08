package com.bfly.cms.logs.entity;

import com.bfly.cms.member.entity.Member;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * CMS日志操作
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 16:40
 */
@Entity
@Table(name = "sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 2573493653697796058L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 日志类型
     */
    @Column(name = "category")
    private int category;

    /**
     * 日志时间
     */
    @Column(name = "log_time")
    private Date time;

    /**
     * IP地址
     */
    @Column(name = "ip")
    private String ip;

    /**
     * URL地址
     */
    @Column(name = "url")
    private String url;

    /**
     * 日志标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 日志内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 操作者
     */
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }
}
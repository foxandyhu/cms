package com.bfly.cms.logs.entity;

import com.bfly.cms.member.entity.Member;
import com.bfly.cms.user.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志操作
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
    @Column(name = "username")
    private String userName;

    @Column(name = "is_success")
    private boolean success;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
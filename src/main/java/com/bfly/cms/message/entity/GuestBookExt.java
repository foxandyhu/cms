package com.bfly.cms.message.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS留言内容
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:26
 */
@Entity
@Table(name = "guestbook_ext")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class GuestBookExt implements Serializable {

    private static final long serialVersionUID = 147714682278906191L;

    @Id
    @Column(name = "guestbook_id", unique = true, nullable = false)
    private int id;

    /**
     * 所属留言
     */
    @OneToOne(mappedBy = "ext")
    @MapsId
    private GuestBook guestBook;

    /**
     * 留言标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 留言内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 回复内容
     * @author andy_hulibo@163.com
     * @date 2019/8/2 15:54
     */
    @Column(name = "reply_content")
    private String replyContent;

    /**
     * 电子邮件
     */
    @Column(name = "email")
    private String email;

    /**
     * 电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * QQ
     */
    @Column(name = "qq")
    private String qq;

    /**
     * 留言Ip
     * @author andy_hulibo@163.com
     * @date 2019/8/2 15:55
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 留言地点区域
     * @author andy_hulibo@163.com
     * @date 2019/8/2 15:56
     */
    @Column(name = "area")
    private String area;

    /**
     * 回复ip
     * @author andy_hulibo@163.com
     * @date 2019/8/2 15:55
     */
    @Column(name = "reply_ip")
    private String replyIp;

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

    public String getReplyIp() {
        return replyIp;
    }

    public void setReplyIp(String replyIp) {
        this.replyIp = replyIp;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public GuestBook getGuestBook() {
        return guestBook;
    }

    public void setGuestBook(GuestBook guestBook) {
        this.guestBook = guestBook;
    }
}
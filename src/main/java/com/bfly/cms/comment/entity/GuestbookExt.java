package com.bfly.cms.comment.entity;

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
public class GuestbookExt implements Serializable {

    private static final long serialVersionUID = 147714682278906191L;

    @Id
    @Column(name = "guestbook_id", unique = true, nullable = false)
    private int id;

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
     */
    @Column(name = "reply")
    private String reply;

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
     * 所属留言
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "guestbook_id")
    private Guestbook guestbook;

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

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
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

    public Guestbook getGuestbook() {
        return guestbook;
    }

    public void setGuestbook(Guestbook guestbook) {
        this.guestbook = guestbook;
    }
}
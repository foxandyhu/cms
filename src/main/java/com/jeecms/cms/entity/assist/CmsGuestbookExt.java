package com.jeecms.cms.entity.assist;

import org.apache.commons.lang.StringUtils;
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
@Table(name = "jc_guestbook_ext")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsGuestbookExt implements Serializable {
    private static final long serialVersionUID = 1L;

    public void init() {
        blankToNull();
    }

    public void blankToNull() {
        if (StringUtils.isBlank(getContent())) {
            setContent(null);
        }
        if (StringUtils.isBlank(getReply())) {
            setReply(null);
        }
        if (StringUtils.isBlank(getTitle())) {
            setTitle(null);
        }
        if (StringUtils.isBlank(getEmail())) {
            setEmail(null);
        }
        if (StringUtils.isBlank(getPhone())) {
            setPhone(null);
        }
        if (StringUtils.isBlank(getQq())) {
            setQq(null);
        }
    }

    @Column(name = "guestbook_id")
    private Integer id;

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

    @Id
    @OneToOne
    @JoinColumn(name = "guestbook_id")
    private CmsGuestbook guestbook;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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


    public CmsGuestbook getGuestbook() {
        return guestbook;
    }


    public void setGuestbook(CmsGuestbook guestbook) {
        this.guestbook = guestbook;
    }


}
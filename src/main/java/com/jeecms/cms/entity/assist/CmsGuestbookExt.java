package com.jeecms.cms.entity.assist;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

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

    // primary key
    private Integer id;

    // fields
    private String title;
    private String content;
    private String reply;
    private String email;
    private String phone;
    private String qq;

    // one to one
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
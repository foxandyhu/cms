package com.jeecms.cms.entity.assist;

import java.io.Serializable;


public class CmsCommentExt implements Serializable {
    private static final long serialVersionUID = 1L;

    // primary key
    private Integer id;

    // fields
    private String ip;
    private String text;
    private String reply;

    // one to one
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
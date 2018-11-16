package com.jeecms.cms.entity.assist;

import java.io.Serializable;


public class CmsVoteReply implements Serializable {
    private static final long serialVersionUID = 1L;

    // primary key
    private Integer id;

    // fields
    private String reply;

    // many to one
    private CmsVoteSubTopic subTopic;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getReply() {
        return reply;
    }


    public void setReply(String reply) {
        this.reply = reply;
    }

    public CmsVoteSubTopic getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(CmsVoteSubTopic subTopic) {
        this.subTopic = subTopic;
    }


}
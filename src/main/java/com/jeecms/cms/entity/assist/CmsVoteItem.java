package com.jeecms.cms.entity.assist;

import com.jeecms.common.hibernate4.PriorityInterface;

import java.io.Serializable;
import java.util.Set;

public class CmsVoteItem implements PriorityInterface, Serializable {
    private static final long serialVersionUID = 1L;


    //调查多题目情况下
    public int getPercent() {
        Integer totalCount = 0;
        Set<CmsVoteItem> subTopicVoteItems = getSubTopic().getVoteItems();
        for (CmsVoteItem vote : subTopicVoteItems) {
            totalCount += vote.getVoteCount();
        }
        if (totalCount != null && totalCount != 0) {
            return (getVoteCount() * 100) / totalCount;
        } else {
            return 0;
        }
    }

    public void init() {
        if (getPriority() == null) {
            setPriority(10);
        }
        if (getVoteCount() == null) {
            setVoteCount(0);
        }
    }

    // primary key
    private Integer id;

    // fields
    private String title;
    private Integer voteCount;
    private Integer priority;
    private String picture;

    // many to one
    private CmsVoteTopic topic;
    private CmsVoteSubTopic subTopic;


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

    public Integer getVoteCount() {
        return voteCount;
    }


    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getPriority() {
        return priority;
    }


    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public CmsVoteTopic getTopic() {
        return topic;
    }


    public void setTopic(CmsVoteTopic topic) {
        this.topic = topic;
    }


    public CmsVoteSubTopic getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(CmsVoteSubTopic subTopic) {
        this.subTopic = subTopic;
    }


}
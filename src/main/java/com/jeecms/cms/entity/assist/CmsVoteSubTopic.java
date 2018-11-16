package com.jeecms.cms.entity.assist;


import com.jeecms.common.hibernate4.PriorityInterface;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Set;


public class CmsVoteSubTopic implements PriorityInterface, Comparable<Object>, Serializable {
    private static final long serialVersionUID = 1L;

    //虚拟字段，用于存储保存题目顺序
    @Transient
    private Integer voteIndex;

    // primary key
    private Integer id;

    // fields
    private String title;
    private Integer type;
    private Integer priority;

    // many to one
    private CmsVoteTopic voteTopic;

    // collections
    private Set<CmsVoteItem> voteItems;
    private Set<CmsVoteReply> voteReplys;

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


    public Integer getType() {
        return type;
    }


    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public CmsVoteTopic getVoteTopic() {
        return voteTopic;
    }


    public void setVoteTopic(CmsVoteTopic voteTopic) {
        this.voteTopic = voteTopic;
    }

    public Set<CmsVoteItem> getVoteItems() {
        return voteItems;
    }


    public void setVoteItems(Set<CmsVoteItem> voteItems) {
        this.voteItems = voteItems;
    }


    public Set<CmsVoteReply> getVoteReplys() {
        return voteReplys;
    }

    public void setVoteReplys(Set<CmsVoteReply> voteReplys) {
        this.voteReplys = voteReplys;
    }

    public Integer getVoteIndex() {
        return voteIndex;
    }

    public void setVoteIndex(Integer voteIndex) {
        this.voteIndex = voteIndex;
    }

    public boolean getIsRadio() {
        if (getType() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getIsMulti() {
        if (getType() == 2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getIsText() {
        if (getType() == 3) {
            return true;
        } else {
            return false;
        }
    }

    public int compareTo(Object o) {
        return getPriority();
    }


}
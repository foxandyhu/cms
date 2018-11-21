package com.bfly.cms.entity.assist;


import com.bfly.common.hibernate4.PriorityComparator;
import com.bfly.common.hibernate4.PriorityInterface;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 投票子题目
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:38
 */
@Entity
@Table(name = "jc_vote_subtopic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsVoteSubTopic implements PriorityInterface, Comparable<Object>, Serializable {
    private static final long serialVersionUID = 1L;

    //虚拟字段，用于存储保存题目顺序
    @Transient
    private Integer voteIndex;

    @Id
    @Column(name = "subtopic_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 类型（1单选，2多选，3文本）
     */
    @Column(name = "subtopic_type")
    private Integer type;

    @Column(name = "priority")
    private Integer priority;

    /**
     * 投票（调查）
     */
    @ManyToOne
    @JoinColumn(name = "votetopic_id")
    private CmsVoteTopic voteTopic;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @OneToMany(mappedBy = "subTopic", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @SortComparator(value = PriorityComparator.class)
    private Set<CmsVoteItem> voteItems;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @OneToMany(mappedBy = "subTopic", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<CmsVoteReply> voteReplys;

    @Override
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

    @Override
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

    @Override
    public int compareTo(Object o) {
        return getPriority();
    }


}
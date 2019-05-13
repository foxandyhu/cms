package com.bfly.cms.vote.entity;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@Table(name = "vote_subtopic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class VoteSubTopic implements Serializable {

    private static final long serialVersionUID = 3345663970044453306L;

    @Transient
    private Integer voteIndex;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 类型（1单选，2多选，3文本）
     */
    @Column(name = "subtopic_type")
    private int type;

    @Column(name = "priority")
    private int priority;

    /**
     * 投票（调查）
     */
    @ManyToOne
    @JoinColumn(name = "votetopic_id")
    private VoteTopic voteTopic;

    /**
     * 投票项
     */
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @OneToMany(mappedBy = "subTopic", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<VoteItem> voteItems;

    /**
     * 投票回复
     */
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @OneToMany(mappedBy = "subTopic", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<VoteReply> voteReplys;

    public Integer getVoteIndex() {
        return voteIndex;
    }

    public void setVoteIndex(Integer voteIndex) {
        this.voteIndex = voteIndex;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public VoteTopic getVoteTopic() {
        return voteTopic;
    }

    public void setVoteTopic(VoteTopic voteTopic) {
        this.voteTopic = voteTopic;
    }

    public Set<VoteItem> getVoteItems() {
        return voteItems;
    }

    public void setVoteItems(Set<VoteItem> voteItems) {
        this.voteItems = voteItems;
    }

    public Set<VoteReply> getVoteReplys() {
        return voteReplys;
    }

    public void setVoteReplys(Set<VoteReply> voteReplys) {
        this.voteReplys = voteReplys;
    }
}
package com.bfly.cms.vote.entity;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 投票子题目
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:38
 */
@Entity
@Table(name = "vote_subtopic")
public class VoteSubTopic implements Serializable, Comparable<VoteSubTopic> {

    private static final long serialVersionUID = 3345663970044453306L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 标题
     */
    @Column(name = "title")
    @NotEmpty(message = "子主题项标题不能为空!")
    @Size(min = 1, max = 100, message = "子项标题长度为1-100之间!")
    private String title;

    /**
     * 类型（1单选，2多选，3文本）
     *
     * @see com.bfly.core.enums.VoteType
     */
    @Column(name = "type")
    private int type;

    @Column(name = "seq")
    @Min(value = 0, message = "排序序号最小为0!")
    private int seq;

    /**
     * 所属投票主题ID
     */
    @Column(name = "vote_topic_id")
    private int voteTopicId;

    /**
     * 投票项
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sub_topic_id")
    @OrderBy("seq")
    private List<VoteItem> voteItems;

    /**
     * 投票回复
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sub_topic_id")
    private List<VoteReply> voteReplys;

    @Override
    public int compareTo(VoteSubTopic o) {
        return getSeq() - o.getSeq();
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

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getVoteTopicId() {
        return voteTopicId;
    }

    public void setVoteTopicId(int voteTopicId) {
        this.voteTopicId = voteTopicId;
    }

    public List<VoteItem> getVoteItems() {
        return voteItems;
    }

    public void setVoteItems(List<VoteItem> voteItems) {
        this.voteItems = voteItems;
    }

    public List<VoteReply> getVoteReplys() {
        return voteReplys;
    }

    public void setVoteReplys(List<VoteReply> voteReplys) {
        this.voteReplys = voteReplys;
    }
}
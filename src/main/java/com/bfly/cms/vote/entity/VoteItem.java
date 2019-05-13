package com.bfly.cms.vote.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 投票项
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:25
 */
@Entity
@Table(name = "vote_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class VoteItem implements Serializable {

    private static final long serialVersionUID = -1360824589287347094L;

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
     * 投票数量
     */
    @Column(name = "vote_count")
    private int voteCount;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private int priority;

    /**
     * 图片
     */
    @Column(name = "picture")
    private String picture;

    /**
     * 所属投票主题
     */
    @ManyToOne
    @JoinColumn(name = "votetopic_id")
    private VoteTopic topic;

    /**
     * 所属投票子主题
     */
    @ManyToOne
    @JoinColumn(name = "subtopic_id")
    private VoteSubTopic subTopic;


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

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public VoteTopic getTopic() {
        return topic;
    }

    public void setTopic(VoteTopic topic) {
        this.topic = topic;
    }

    public VoteSubTopic getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(VoteSubTopic subTopic) {
        this.subTopic = subTopic;
    }
}
package com.bfly.cms.vote.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
public class VoteItem implements Serializable, Comparable<VoteItem> {

    private static final long serialVersionUID = -1360824589287347094L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 标题
     */
    @Column(name = "title")
    @Size(min = 1, max = 100, message = "答案选项长度为1-100之间!")
    private String title;

    /**
     * 投票数量
     */
    @Column(name = "vote_count")
    private int voteCount;

    /**
     * 排列顺序
     */
    @Column(name = "seq")
    @Min(value = 0, message = "排序序号最小为0!")
    private int seq;

    /**
     * 图片
     */
    @Column(name = "picture")
    private String picture;

    /**
     * 所属投票子主题Id
     */
    @Column(name = "sub_topic_id")
    private int subTopicId;

    @Override
    public int compareTo(VoteItem o) {
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

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getSubTopicId() {
        return subTopicId;
    }

    public void setSubTopicId(int subTopicId) {
        this.subTopicId = subTopicId;
    }
}
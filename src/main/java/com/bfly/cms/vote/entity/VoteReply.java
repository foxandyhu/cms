package com.bfly.cms.vote.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 投票文本题目回复
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:36
 */
@Entity
@Table(name = "vote_reply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class VoteReply implements Serializable {

    private static final long serialVersionUID = -9219522160983208978L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 回复内容
     */
    @Column(name = "reply")
    private String reply;

    /**
     * 所属投票自主题
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

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public VoteSubTopic getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(VoteSubTopic subTopic) {
        this.subTopic = subTopic;
    }
}
package com.jeecms.cms.entity.assist;

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
@Table(name = "jc_vote_reply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsVoteReply implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "votereply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 回复内容
     */
    @Column(name = "reply")
    private String reply;

    @ManyToOne
    @JoinColumn(name = "subtopic_id")
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
package com.bfly.cms.entity.assist;

import com.bfly.common.hibernate4.PriorityInterface;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * CMS投票项
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:25
 */
@Entity
@Table(name = "jc_vote_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
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

    @Id
    @Column(name = "voteitem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 投票数量
     */
    @Column(name = "vote_count")
    private Integer voteCount;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private Integer priority;

    /**
     * 图片
     */
    @Column(name = "picture")
    private String picture;

    @ManyToOne
    @JoinColumn(name = "votetopic_id")
    private CmsVoteTopic topic;

    @ManyToOne
    @JoinColumn(name = "subtopic_id")
    private CmsVoteSubTopic subTopic;


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

    public Integer getVoteCount() {
        return voteCount;
    }


    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Override
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
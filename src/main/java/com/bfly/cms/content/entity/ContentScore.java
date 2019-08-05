package com.bfly.cms.content.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 评分纪录
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 9:49
 */
@Entity
@Table(name = "content_score")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ContentScore implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 评分次数
     */
    @Column(name = "score_count")
    private int count;

    /**
     * 所属文章内容
     */
    @Column(name = "content_id")
    private int contentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }
}
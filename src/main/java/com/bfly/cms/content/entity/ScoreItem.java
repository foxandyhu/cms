package com.bfly.cms.content.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * 评分项
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 9:45
 */
@Entity
@Table(name = "score_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ScoreItem implements Serializable, Comparable<ScoreItem> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 评分名
     */
    @Column(name = "name")
    @NotBlank(message = "评分名称不能为空!")
    private String name;

    /**
     * 分值
     */
    @Column(name = "score")
    @Min(value = 0, message = "评分值必须大于0!")
    private int score;

    /**
     * 评分图标路径
     */
    @Column(name = "url")
    private String url;

    /**
     * 排序
     */
    @Column(name = "seq")
    @Min(value = 0, message = "评分顺序必须大于0!")
    private int seq;

    /**
     * 评分组
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id")
    private ScoreGroup group;

    @Override
    public int compareTo(ScoreItem o) {
        return this.getSeq() - o.getSeq();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public ScoreGroup getGroup() {
        return group;
    }

    public void setGroup(ScoreGroup group) {
        this.group = group;
    }
}
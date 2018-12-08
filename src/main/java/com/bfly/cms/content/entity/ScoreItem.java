package com.bfly.cms.content.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
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
    private String name;

    /**
     * 分值
     */
    @Column(name = "score")
    private int score;

    /**
     * 评分图标路径
     */
    @Column(name = "image_path")
    private String imagePath;

    /**
     * 排序
     */
    @Column(name = "priority")
    private int priority;

    /**
     * 评分组
     */
    @ManyToOne
    @JoinColumn(name = "group_id")
    private ScoreGroup group;

    /**
     * 评分记录
     */
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private Set<ScoreRecord> records;


    @Override
    public int compareTo(ScoreItem o) {
        return this.getPriority() - o.getPriority();
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ScoreGroup getGroup() {
        return group;
    }

    public void setGroup(ScoreGroup group) {
        this.group = group;
    }

    public Set<ScoreRecord> getRecords() {
        return records;
    }

    public void setRecords(Set<ScoreRecord> records) {
        this.records = records;
    }
}
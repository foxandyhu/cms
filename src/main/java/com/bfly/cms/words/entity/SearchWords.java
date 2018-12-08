package com.bfly.cms.words.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 搜索词汇
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:28
 */
@Entity
@Table(name = "d_search_words")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class SearchWords implements Serializable {

    /**
     * 搜索次数降序
     */
    public static final int HIT_DESC = 1;

    /**
     * 优先级降序
     */
    public static final int PRIORITY_DESC = 3;

    /**
     *
     */
    public static final int HIT_ASC = 2;

    /**
     * 优先级升序
     */
    public static final int PRIORITY_ASC = 4;

    public static final int DEFAULT_PRIORITY = 10;
    private static final long serialVersionUID = 4755704910893942895L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 搜索词汇
     */
    @Column(name = "name")
    private String name;

    /**
     * 搜索次数
     */
    @Column(name = "hit_count")
    private int hitCount;

    /**
     * 优先级
     */
    @Column(name = "priority")
    private int priority;

    /**
     * 拼音首字母
     */
    @Column(name = "name_initial")
    private String nameInitial;

    /**
     * 推荐
     */
    @Column(name = "is_recommend")
    private boolean recommend;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHitCount() {
        return hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getNameInitial() {
        return nameInitial;
    }

    public void setNameInitial(String nameInitial) {
        this.nameInitial = nameInitial;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }
}
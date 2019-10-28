package com.bfly.cms.words.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 搜索词汇
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:28
 */
@Entity
@Table(name = "d_search_words")
@Cache(region = "beanCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class SearchWords implements Serializable {

    private static final long serialVersionUID = 4755704910893942895L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 搜索词汇
     */
    @Column(name = "name")
    @NotBlank(message = "搜索词不能为空!")
    private String name;

    /**
     * 搜索次数
     */
    @Column(name = "hit_count")
    private int hitCount;

    /**
     * 推荐
     */
    @Column(name = "is_recommend")
    private boolean recommend;

    public int getId() {
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

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }
}
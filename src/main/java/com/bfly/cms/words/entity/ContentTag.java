package com.bfly.cms.words.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 文章内容TAG
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 13:35
 */
@Entity
@Table(name = "content_tag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ContentTag implements Serializable {

    private static final long serialVersionUID = 6307840825256503849L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * tag名称
     */
    @Column(name = "tag_name")
    private String name;

    /**
     * 被引用的次数
     */
    @Column(name = "ref_counter")
    private int count;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
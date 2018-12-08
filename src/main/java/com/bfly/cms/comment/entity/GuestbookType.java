package com.bfly.cms.comment.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 留言类别
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:23
 */
@Entity
@Table(name = "guestbook_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class GuestbookType implements Serializable {

    private static final long serialVersionUID = 518868718222923558L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 名称
     */
    @Column(name = "ctg_name")
    private String name;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private int priority;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
package com.bfly.cms.friendlink.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS友情链接类别
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:15
 */
@Entity
@Table(name = "friendlink_type")
public class FriendLinkType implements Serializable {

    private static final long serialVersionUID = 7919307427987468685L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private int priority;

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
}
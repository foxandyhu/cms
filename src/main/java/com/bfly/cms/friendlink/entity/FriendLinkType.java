package com.bfly.cms.friendlink.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * CMS友情链接类别
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:15
 */
@Entity
@Table(name = "friendlink_type")
public class FriendLinkType implements Serializable, Comparable<FriendLinkType> {

    private static final long serialVersionUID = 7919307427987468685L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 名称
     */
    @Column(name = "name")
    @NotBlank(message = "类型名称不能为空!")
    private String name;

    /**
     * 排列顺序
     */
    @Column(name = "seq")
    private int seq;

    @Override
    public int compareTo(FriendLinkType type) {
        return this.getSeq() - type.getSeq();
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

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
package com.bfly.cms.friendlink.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 友情链接
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:12
 */
@Entity
@Table(name = "friendlink")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class FriendLink implements Serializable, Comparable<FriendLink> {

    private static final long serialVersionUID = -2546503159131831426L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 网站名称
     */
    @Column(name = "name")
    @NotBlank(message = "网站名称不能为空!")
    private String name;

    /**
     * 网站地址
     */
    @Column(name = "url")
    @NotBlank(message = "网站地址不能为空!")
    private String url;

    /**
     * 图标
     */
    @Column(name = "logo")
    private String logo;

    /**
     * 描述
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 排列顺序
     */
    @Column(name = "seq")
    @Min(value = 0, message = "排序顺序必须大于0!")
    private int seq;

    /**
     * 是否显示
     */
    @Column(name = "is_enabled")
    private boolean enabled;

    /**
     * 所属类型
     */
    @ManyToOne
    @JoinColumn(name = "type_id")
    @NotNull(message = "类型不能为空!")
    private FriendLinkType type;

    @Override
    public int compareTo(FriendLink link) {
        return this.getSeq() - link.getSeq();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public FriendLinkType getType() {
        return type;
    }

    public void setType(FriendLinkType type) {
        this.type = type;
    }
}
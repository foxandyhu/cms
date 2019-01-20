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
public class FriendLink implements Serializable {

    private static final long serialVersionUID = -2546503159131831426L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 网站名称
     */
    @Column(name = "site_name")
    @NotBlank(message = "网站名称不能为空!")
    private String name;

    /**
     * 网站地址
     */
    @Column(name = "domain")
    @NotBlank(message = "网站地址不能为空!")
    private String domain;

    /**
     * 图标
     */
    @Column(name = "logo")
    private String logo;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 点击次数
     */
    @Column(name = "views")
    @Min(value = 0, message = "点击次数必须大于0!")
    private int views;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    @Min(value = 0, message = "排序顺序必须大于0!")
    private int priority;

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
    private FriendLinkType category;

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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public FriendLinkType getCategory() {
        return category;
    }

    public void setCategory(FriendLinkType category) {
        this.category = category;
    }
}
package com.bfly.cms.content.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * 评分组
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 9:45
 */
@Entity
@Table(name = "score_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ScoreGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 分组名
     */
    @Column(name = "name")
    @NotBlank(message = "分组名称不能为空!")
    private String name;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 是否启用
     */
    @Column(name = "enable")
    private boolean enable;

    /**
     * 是否默认
     */
    @Column(name = "def")
    private boolean def;

    /**
     * 评分项
     */
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<ScoreItem> items;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    public Set<ScoreItem> getItems() {
        return items;
    }

    public void setItems(Set<ScoreItem> items) {
        this.items = items;
    }
}
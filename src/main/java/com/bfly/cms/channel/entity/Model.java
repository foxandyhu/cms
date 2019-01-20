package com.bfly.cms.channel.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * CMS模型类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 11:29
 */
@Entity
@Table(name = "model")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class Model implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 名称
     */
    @Column(name = "model_name")
    @NotBlank(message = "模型名称不能为空!")
    private String name;

    /**
     * 栏目标题图宽度
     */
    @Column(name = "title_img_width")
    private int titleImgWidth;

    /**
     * 栏目标题图高度
     */
    @Column(name = "title_img_height")
    private int titleImgHeight;

    /**
     * 栏目内容图宽度
     */
    @Column(name = "content_img_width")
    private int contentImgWidth;

    /**
     * 栏目内容图高度
     */
    @Column(name = "content_img_height")
    private int contentImgHeight;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private int priority;

    /**
     * 是否有内容
     * false 标识没有内容模型 true表示有内容模型
     */
    @Column(name = "has_content")
    private boolean hasContent;

    /**
     * 是否禁用
     */
    @Column(name = "is_enabled")
    private boolean enabled;

    /**
     * 模型项
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "model")
    private Set<ModelItem> items;

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

    public int getTitleImgWidth() {
        return titleImgWidth;
    }

    public void setTitleImgWidth(int titleImgWidth) {
        this.titleImgWidth = titleImgWidth;
    }

    public int getTitleImgHeight() {
        return titleImgHeight;
    }

    public void setTitleImgHeight(int titleImgHeight) {
        this.titleImgHeight = titleImgHeight;
    }

    public int getContentImgWidth() {
        return contentImgWidth;
    }

    public void setContentImgWidth(int contentImgWidth) {
        this.contentImgWidth = contentImgWidth;
    }

    public int getContentImgHeight() {
        return contentImgHeight;
    }

    public void setContentImgHeight(int contentImgHeight) {
        this.contentImgHeight = contentImgHeight;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isHasContent() {
        return hasContent;
    }

    public void setHasContent(boolean hasContent) {
        this.hasContent = hasContent;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<ModelItem> getItems() {
        return items;
    }

    public void setItems(Set<ModelItem> items) {
        this.items = items;
    }
}
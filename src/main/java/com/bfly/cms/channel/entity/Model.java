package com.bfly.cms.channel.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
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
    private int id;

    /**
     * 名称
     */
    @Column(name = "model_name")
    private String name;

    /**
     * 路径
     */
    @Column(name = "model_path")
    private String path;

    /**
     * 栏目模板前缀
     */
    @Column(name = "tpl_channel_prefix")
    private String tplChannelPrefix;

    /**
     * 内容模板前缀
     */
    @Column(name = "tpl_content_prefix")
    private String tplContentPrefix;

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
     */
    @Column(name = "has_content")
    private boolean hasContent;

    /**
     * 是否禁用
     */
    @Column(name = "is_disabled")
    private boolean disabled;

    /**
     * 是否默认模型
     */
    @Column(name = "is_def")
    private boolean def;

    /**
     * 是否全站模型
     */
    @Column(name = "is_global")
    private boolean global;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTplChannelPrefix() {
        return tplChannelPrefix;
    }

    public void setTplChannelPrefix(String tplChannelPrefix) {
        this.tplChannelPrefix = tplChannelPrefix;
    }

    public String getTplContentPrefix() {
        return tplContentPrefix;
    }

    public void setTplContentPrefix(String tplContentPrefix) {
        this.tplContentPrefix = tplContentPrefix;
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public Set<ModelItem> getItems() {
        return items;
    }

    public void setItems(Set<ModelItem> items) {
        this.items = items;
    }
}
package com.bfly.cms.content.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 栏目实体类
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/4 12:21
 */
@Entity
@Table(name = "channel")
public class Channel implements Serializable, Comparable<Channel> {

    private static final long serialVersionUID = -8682983792421664015L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 访问路径
     */
    @Column(name = "channel_path")
    @NotBlank(message = "访问路径不能为空!")
    private String path;

    /**
     * 排列顺序
     */
    @Column(name = "seq")
    private int seq;

    /**
     * 是否显示
     */
    @Column(name = "is_display")
    private boolean display;

    /**
     * 名称
     */
    @Column(name = "channel_name")
    @NotBlank(message = "名称不能为空!")
    private String name;

    /**
     * 外部链接
     */
    @Column(name = "link")
    private String link;

    /**
     * 栏目页模板
     */
    @Column(name = "tpl_pc_channel")
    private String tplPcChannel;

    /**
     * 内容页模板
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 20:27
     */
    @Column(name = "tpl_pc_content")
    private String tplPcContent;

    /**
     * 手机栏目页模板
     */
    @Column(name = "tpl_mobile_channel")
    private String tplMobileChannel;

    /**
     * 手机内容页模板
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 20:28
     */
    @Column(name = "tpl_mobile_content")
    private String tplMobileContent;

    /**
     * 窗口打开方式
     */
    @Column(name = "target")
    private String target;

    /**
     * meta 关键字
     */
    @Column(name = "keywords")
    private String keywords;

    /**
     * meta描述
     */
    @Column(name = "summary")
    private String summary;

    /**
     * 所属模型
     */
    @Column(name = "model_id")
    private int modelId;


    /**
     * 父类栏目
     */
    @Column(name = "parent_id")
    private int parentId;

    @Override
    public int compareTo(Channel o) {
        return this.getSeq()-o.getSeq();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTplPcChannel() {
        return tplPcChannel;
    }

    public void setTplPcChannel(String tplPcChannel) {
        this.tplPcChannel = tplPcChannel;
    }

    public String getTplPcContent() {
        return tplPcContent;
    }

    public void setTplPcContent(String tplPcContent) {
        this.tplPcContent = tplPcContent;
    }

    public String getTplMobileChannel() {
        return tplMobileChannel;
    }

    public void setTplMobileChannel(String tplMobileChannel) {
        this.tplMobileChannel = tplMobileChannel;
    }

    public String getTplMobileContent() {
        return tplMobileContent;
    }

    public void setTplMobileContent(String tplMobileContent) {
        this.tplMobileContent = tplMobileContent;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
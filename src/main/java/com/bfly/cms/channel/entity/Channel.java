package com.bfly.cms.channel.entity;

import com.bfly.cms.member.entity.MemberGroup;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 栏目实体类
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/4 12:21
 */
@Entity
@Table(name = "channel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class Channel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 访问路径
     */
    @Column(name = "channel_path")
    private String path;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private int priority;

    /**
     * 是否显示
     */
    @Column(name = "is_display")
    private boolean display;

    /**
     * 栏目扩展信息
     */
    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "channel")
    private ChannelExt channelExt;

    /**
     * 所属模型
     */
    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;


    /**
     * 父类栏目
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Channel parent;

    /**
     * 子栏目
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private Set<Channel> child;

    /**
     * 浏览权限组
     */
    @ManyToMany(mappedBy = "viewChannels")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private Set<MemberGroup> viewGroups;

    /**
     * 投稿权限组
     */
    @ManyToMany(mappedBy = "contriChannels")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private Set<MemberGroup> contriGroups;

    /**
     * 栏目扩展内容
     */
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "channel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private ChannelTxt channelTxt;

    /**
     * 栏目数据统计
     */
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "channel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private ChannelCount channelCount;

    @ElementCollection
    @OrderColumn(name = "priority")
    @CollectionTable(name = "channel_template", joinColumns = @JoinColumn(name = "channel_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private List<ChannelTemplate> channelTemplate;

    /**
     * 栏目其他的属性
     */
    @ElementCollection
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @CollectionTable(name = "channel_attr", joinColumns = @JoinColumn(name = "channel_id"))
    @MapKeyColumn(name = "attr_name")
    @Column(name = "attr_value")
    private Map<String, String> attr;

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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public ChannelExt getChannelExt() {
        return channelExt;
    }

    public void setChannelExt(ChannelExt channelExt) {
        this.channelExt = channelExt;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Channel getParent() {
        return parent;
    }

    public void setParent(Channel parent) {
        this.parent = parent;
    }

    public Set<Channel> getChild() {
        return child;
    }

    public void setChild(Set<Channel> child) {
        this.child = child;
    }

    public Set<MemberGroup> getViewGroups() {
        return viewGroups;
    }

    public void setViewGroups(Set<MemberGroup> viewGroups) {
        this.viewGroups = viewGroups;
    }

    public Set<MemberGroup> getContriGroups() {
        return contriGroups;
    }

    public void setContriGroups(Set<MemberGroup> contriGroups) {
        this.contriGroups = contriGroups;
    }

    public ChannelTxt getChannelTxt() {
        return channelTxt;
    }

    public void setChannelTxt(ChannelTxt channelTxt) {
        this.channelTxt = channelTxt;
    }

    public ChannelCount getChannelCount() {
        return channelCount;
    }

    public void setChannelCount(ChannelCount channelCount) {
        this.channelCount = channelCount;
    }

    public List<ChannelTemplate> getChannelTemplate() {
        return channelTemplate;
    }

    public void setChannelTemplate(List<ChannelTemplate> channelTemplate) {
        this.channelTemplate = channelTemplate;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
}
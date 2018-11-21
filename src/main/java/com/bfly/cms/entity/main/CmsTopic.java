package com.bfly.cms.entity.main;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * CMS专题类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 11:50
 */
@Entity
@Table(name = "jc_topic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsTopic implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "topic_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "topic_name")
    private String name;

    /**
     * 简称
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * 关键字
     */
    @Column(name = "keywords")
    private String keywords;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 标题图
     */
    @Column(name = "title_img")
    private String titleImg;

    /**
     * 内容图
     */
    @Column(name = "content_img")
    private String contentImg;

    /**
     * 专题模板
     */
    @Column(name = "tpl_content")
    private String tplContent;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private Integer priority;

    /**
     * 是否推荐
     */
    @Column(name = "is_recommend")
    private Boolean recommend;

    /**
     * 首字母拼音简写
     */
    @Column(name = "initials")
    private String initials;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @JoinTable(name = "jc_topic_channel", joinColumns = @JoinColumn(name = "topic_id", referencedColumnName = "topic_id"), inverseJoinColumns = @JoinColumn(name = "channel_id", referencedColumnName = "channel_id"))
    private Set<Channel> channels;


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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }


    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getTplContent() {
        return tplContent;
    }

    public void setTplContent(String tplContent) {
        this.tplContent = tplContent;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }


    public Channel getChannel() {
        return channel;
    }


    public void setChannel(Channel channel) {
        this.channel = channel;
    }


    public Set<Channel> getChannels() {
        return channels;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getShortName())) {
            json.put("shortName", getShortName());
        } else {
            json.put("shortName", "");
        }
        if (StringUtils.isNotBlank(getKeywords())) {
            json.put("keywords", getKeywords());
        } else {
            json.put("keywords", "");
        }
        if (StringUtils.isNotBlank(getDescription())) {
            json.put("description", getDescription());
        } else {
            json.put("description", "");
        }
        if (StringUtils.isNotBlank(getTitleImg())) {
            json.put("titleImg", getTitleImg());
        } else {
            json.put("titleImg", "");
        }
        if (StringUtils.isNotBlank(getContentImg())) {
            json.put("contentImg", getContentImg());
        } else {
            json.put("contentImg", "");
        }
        if (StringUtils.isNotBlank(getTplContent())) {
            json.put("tplContent", getTplContent());
        } else {
            json.put("tplContent", "");
        }
        if (getPriority() != null) {
            json.put("priority", getPriority());
        } else {
            json.put("priority", "");
        }
        if (getRecommend() != null) {
            json.put("recommend", getRecommend());
        } else {
            json.put("recommend", "");
        }
        if (StringUtils.isNotBlank(getInitials())) {
            json.put("initials", getInitials());
        } else {
            json.put("initials", "");
        }
        JSONArray channelArray = new JSONArray();
        if (getChannels() != null) {
            Set<Channel> set = getChannels();
            for (Channel channel : set) {
                channelArray.put(channel.getId());
            }
        }
        json.put("channelIds", channelArray);
        return json;
    }

    public String getTplContentShort(String tplBasePath) {
        String tplContent = getTplContent();
        // 当前模板，去除基本路径
        int tplPathLength = tplBasePath.length();
        if (!StringUtils.isBlank(tplContent)) {
            tplContent = tplContent.substring(tplPathLength);
        }
        return tplContent;
    }

    /**
     * 获得简短名称，如果不存在则返回名称
     *
     * @return
     */
    public String getSname() {
        if (!StringUtils.isBlank(getShortName())) {
            return getShortName();
        } else {
            return getName();
        }
    }

    public void init() {
        blankToNull();
        if (getPriority() == null) {
            setPriority(10);
        }
        if (getRecommend() == null) {
            setRecommend(false);
        }
    }

    public void blankToNull() {
        if (StringUtils.isBlank(getTitleImg())) {
            setTitleImg(null);
        }
        if (StringUtils.isBlank(getContentImg())) {
            setContentImg(null);
        }
        if (StringUtils.isBlank(getShortName())) {
            setShortName(null);
        }
    }

    /**
     * 从集合中获取ID数组
     *
     * @param topics
     * @return
     */
    public static Integer[] fetchIds(Collection<CmsTopic> topics) {
        Integer[] ids = new Integer[topics.size()];
        int i = 0;
        for (CmsTopic g : topics) {
            ids[i++] = g.getId();
        }
        return ids;
    }

    public void addToChannels(Channel channel) {
        Set<Channel> channels = getChannels();
        if (channels == null) {
            channels = new HashSet<>();
            setChannels(channels);
        }
        channels.add(channel);
    }
}
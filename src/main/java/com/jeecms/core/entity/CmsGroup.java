package com.jeecms.core.entity;

import com.jeecms.cms.entity.main.Channel;
import com.jeecms.common.hibernate4.PriorityComparator;
import com.jeecms.common.hibernate4.PriorityInterface;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * CMS会员组类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 16:24
 */
@Entity
@Table(name = "jc_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsGroup implements PriorityInterface, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "group_name")
    private String name;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private Integer priority;

    /**
     * 每日允许上传KB
     */
    @Column(name = "allow_per_day")
    private Integer allowPerDay;

    /**
     * 每个文件最大KB
     */
    @Column(name = "allow_max_file")
    private Integer allowMaxFile;

    /**
     * 允许上传的后缀
     */
    @Column(name = "allow_suffix")
    private String allowSuffix;

    /**
     * 是否需要验证码
     */
    @Column(name = "need_captcha")
    private Boolean needCaptcha;
    /**
     * 是否需要审核
     */
    @Column(name = "need_check")

    private Boolean needCheck;

    /**
     * 是否默认会员组
     */
    @Column(name = "is_reg_def")
    private Boolean regDef;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @JoinTable(name = "jc_chnl_group_view",joinColumns = @JoinColumn(name = "group_id",referencedColumnName = "group_id"),inverseJoinColumns = @JoinColumn(name = "channel_id",referencedColumnName = "channel_id"))
    private Set<Channel> viewChannels;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @JoinTable(name = "jc_chnl_group_contri",joinColumns = @JoinColumn(name = "group_id",referencedColumnName = "group_id"),inverseJoinColumns = @JoinColumn(name = "channel_id",referencedColumnName = "channel_id"))
    private Set<Channel> contriChannels;


    @Override
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

    public Integer getPriority() {
        return priority;
    }


    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getAllowPerDay() {
        return allowPerDay;
    }


    public void setAllowPerDay(Integer allowPerDay) {
        this.allowPerDay = allowPerDay;
    }


    public Integer getAllowMaxFile() {
        return allowMaxFile;
    }


    public void setAllowMaxFile(Integer allowMaxFile) {
        this.allowMaxFile = allowMaxFile;
    }


    public String getAllowSuffix() {
        return allowSuffix;
    }

    public void setAllowSuffix(String allowSuffix) {
        this.allowSuffix = allowSuffix;
    }


    public Boolean getNeedCaptcha() {
        return needCaptcha;
    }


    public void setNeedCaptcha(Boolean needCaptcha) {
        this.needCaptcha = needCaptcha;
    }

    public Boolean getNeedCheck() {
        return needCheck;
    }


    public void setNeedCheck(Boolean needCheck) {
        this.needCheck = needCheck;
    }



    public Boolean getRegDef() {
        return regDef;
    }


    public void setRegDef(Boolean regDef) {
        this.regDef = regDef;
    }


    public Set<Channel> getViewChannels() {
        return viewChannels;
    }


    public void setViewChannels(Set<Channel> viewChannels) {
        this.viewChannels = viewChannels;
    }


    public Set<Channel> getContriChannels() {
        return contriChannels;
    }


    public void setContriChannels(Set<Channel> contriChannels) {
        this.contriChannels = contriChannels;
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
        if (getPriority() != null) {
            json.put("priority", getPriority());
        } else {
            json.put("priority", "");
        }
        if (getAllowPerDay() != null) {
            json.put("allowPerDay", getAllowPerDay());
        } else {
            json.put("allowPerDay", "");
        }
        if (getAllowMaxFile() != null) {
            json.put("allowMaxFile", getAllowMaxFile());
        } else {
            json.put("allowMaxFile", "");
        }
        if (StringUtils.isNotBlank(getAllowSuffix())) {
            json.put("allowSuffix", getAllowSuffix());
        } else {
            json.put("allowSuffix", "");
        }
        json.put("allowFileSize", "");
        json.put("allowFileTotal", "");
        if (getNeedCaptcha() != null) {
            json.put("needCaptcha", getNeedCaptcha());
        } else {
            json.put("needCaptcha", "");
        }
        if (getNeedCheck() != null) {
            json.put("needCheck", getNeedCheck());
        } else {
            json.put("needCheck", "");
        }
        if (getRegDef() != null) {
            json.put("regDef", getRegDef());
        } else {
            json.put("regDef", "");
        }
        if (getViewChannels() != null && getViewChannels().size() > 0) {
            JSONArray jsonArray = new JSONArray();
            Set<Channel> set = getViewChannels();
            int index = 0;
            for (Channel channel : set) {
                jsonArray.put(index, channel.getId());
                index++;
            }
            json.put("viewChannelIds", jsonArray);
        } else {
            json.put("viewChannelIds", new JSONArray());
        }
        if (getContriChannels() != null && getContriChannels().size() > 0) {
            JSONArray jsonArray = new JSONArray();
            Set<Channel> set = getContriChannels();
            int index = 0;
            for (Channel channel : set) {
                jsonArray.put(index, channel.getId());
                index++;
            }
            json.put("contriChannelIds", jsonArray);
        } else {
            json.put("contriChannelIds", new JSONArray());
        }
        return json;
    }

    /**
     * 从集合中提取ID数组
     *
     * @param groups
     * @return
     */
    public static Integer[] fetchIds(Collection<CmsGroup> groups) {
        Integer[] ids = new Integer[groups.size()];
        int i = 0;
        for (CmsGroup g : groups) {
            ids[i++] = g.getId();
        }
        return ids;
    }

    /**
     * 根据列表排序
     *
     * @param source 元素集合
     * @param target 有顺序列表
     * @return 一个新的、按目标排序的列表
     */
    public static List<CmsGroup> sortByList(Set<CmsGroup> source,
                                            List<CmsGroup> target) {
        List<CmsGroup> list = new ArrayList<CmsGroup>(source.size());
        for (CmsGroup g : target) {
            if (source.contains(g)) {
                list.add(g);
            }
        }
        return list;
    }

    /**
     * 是否允许上传后缀
     *
     * @param ext
     * @return
     */
    public boolean isAllowSuffix(String ext) {
        String suffix = getAllowSuffix();
        if (StringUtils.isBlank(suffix)) {
            return true;
        }
        String[] attr = StringUtils.split(suffix, ",");
        for (int i = 0, len = attr.length; i < len; i++) {
            if (attr[i].equals(ext)) {
                return true;
            }
        }
        return false;
    }

    public void init() {
        if (getRegDef() == null) {
            setRegDef(false);
        }
        if (getAllowPerDay() == null) {
            setAllowPerDay(0);
        }
        if (getAllowMaxFile() == null) {
            setAllowMaxFile(0);
        }
        if (getNeedCaptcha() == null) {
            setNeedCaptcha(true);
        }
        if (getNeedCheck() == null) {
            setNeedCheck(true);
        }
    }

    public Set<Integer> getViewChannelIds(Integer siteId) {
        Set<Channel> channels = getViewChannels();
        Set<Integer> ids = new HashSet<Integer>();
        for (Channel c : channels) {
            if (c.getSite().getId().equals(siteId)) {
                ids.add(c.getId());
            }
        }
        return ids;
    }

    public Set<Integer> getContriChannelIds(Integer siteId) {
        Set<Channel> channels = getContriChannels();
        Set<Integer> ids = new HashSet<>();
        for (Channel c : channels) {
            if (c.getSite().getId().equals(siteId)) {
                ids.add(c.getId());
            }
        }
        return ids;
    }

    public void addToViewChannels(Channel channel) {
        Set<Channel> channels = getViewChannels();
        if (channels == null) {
            channels = new TreeSet<Channel>(new PriorityComparator());
            setViewChannels(channels);
        }
        channels.add(channel);
        channel.getViewGroups().add(this);
    }

    public void addToContriChannels(Channel channel) {
        Set<Channel> channels = getContriChannels();
        if (channels == null) {
            channels = new TreeSet<Channel>(new PriorityComparator());
            setContriChannels(channels);
        }
        channels.add(channel);
        channel.getContriGroups().add(this);
    }

    public boolean allowUploadFileSuffix(String ext) {
        String allowSuffix = getAllowSuffix();
        if (StringUtils.isBlank(allowSuffix)) {
            return true;
        }
        String[] suffixs = allowSuffix.split(",");
        if (Arrays.asList(suffixs).contains(ext)) {
            return true;
        }
        return false;
    }
}
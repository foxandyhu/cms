package com.jeecms.cms.entity.assist;

import com.jeecms.common.hibernate4.PriorityComparator;
import com.jeecms.common.util.DateUtils;
import com.jeecms.core.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SortComparator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * CMS投票主题
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:42
 */
@Entity
@Table(name = "jc_vote_topic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsVoteTopic implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String PROP_REPEATE_HOUR = "repeateHour";
    public static String PROP_END_TIME = "endTime";
    public static String PROP_START_TIME = "startTime";

    public void init() {
        if (getTotalCount() == null) {
            setTotalCount(0);
        }
        if (getMultiSelect() == null) {
            setMultiSelect(1);
        }
        if (getDef() == null) {
            setDef(false);
        }
        if (getDisabled() == null) {
            setDisabled(false);
        }
        if (getRestrictMember() == null) {
            setRestrictMember(false);
        }
        if (getRestrictIp() == null) {
            setRestrictIp(false);
        }
        if (getRestrictCookie() == null) {
            setRestrictCookie(true);
        }
        if (getVoteDay() == null) {
            setVoteDay(0);
        }
        if (getLimitWeiXin() == null) {
            setLimitWeiXin(false);
        }
    }

    public JSONObject convertToJson(Boolean showSub)
            throws JSONException {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getTitle())) {
            json.put("title", getTitle());
        } else {
            json.put("title", "");
        }
        if (StringUtils.isNotBlank(getDescription())) {
            json.put("description", getDescription());
        } else {
            json.put("description", "");
        }
        if (getStartTime() != null) {
            json.put("startTime", DateUtils.parseDateToTimeStr(getStartTime()));
        } else {
            json.put("startTime", "");
        }
        if (getEndTime() != null) {
            json.put("endTime", DateUtils.parseDateToTimeStr(getEndTime()));
        } else {
            json.put("endTime", "");
        }
        if (getRepeateHour() != null) {
            json.put("repeateHour", getRepeateHour());
        } else {
            json.put("repeateHour", "");
        }
        if (getTotalCount() != null) {
            json.put("totalCount", getTotalCount());
        } else {
            json.put("totalCount", "");
        }
        if (getRestrictMember() != null) {
            json.put("restrictMember", getRestrictMember());
        } else {
            json.put("restrictMember", "");
        }
        if (getRestrictIp() != null) {
            json.put("restrictIp", getRestrictIp());
        } else {
            json.put("restrictIp", "");
        }
        if (getRestrictCookie() != null) {
            json.put("restrictCookie", getRestrictCookie());
        } else {
            json.put("restrictCookie", "");
        }
        if (getDisabled() != null) {
            json.put("disabled", getDisabled());
        } else {
            json.put("disabled", "");
        }
        if (getDef() != null) {
            json.put("def", getDef());
        } else {
            json.put("def", "");
        }
        if (getLimitWeiXin() != null) {
            json.put("limitWeiXin", getLimitWeiXin());
        } else {
            json.put("limitWeiXin", "");
        }
        if (getVoteDay() != null) {
            json.put("voteDay", getVoteDay());
        } else {
            json.put("voteDay", "");
        }
        if (showSub) {
            JSONArray subtopics = new JSONArray();
            Set<CmsVoteSubTopic> subtoics = getSubtopics();
            if (subtoics != null && subtoics.size() > 0) {
                Iterator<CmsVoteSubTopic> subTopicIt = subtoics.iterator();
                int i = 0;
                while (subTopicIt.hasNext()) {
                    JSONObject subTopicJson = new JSONObject();
                    CmsVoteSubTopic sub = subTopicIt.next();
                    Set<CmsVoteItem> items = sub.getVoteItems();
                    if (items != null && items.size() > 0) {
                        Iterator<CmsVoteItem> itemIt = items.iterator();
                        JSONArray itemsArray = new JSONArray();
                        int j = 0;
                        while (itemIt.hasNext()) {
                            CmsVoteItem item = itemIt.next();
                            JSONObject itemJson = new JSONObject();
                            if (item.getId() != null) {
                                itemJson.put("id", item.getId());
                            } else {
                                itemJson.put("id", "");
                            }
                            itemJson.put("percent", item.getPercent());
                            if (StringUtils.isNotBlank(item.getTitle())) {
                                itemJson.put("title", item.getTitle());
                            } else {
                                itemJson.put("title", "");
                            }
                            if (item.getVoteCount() != null) {
                                itemJson.put("voteCount", item.getVoteCount());
                            } else {
                                itemJson.put("voteCount", "");
                            }
                            if (item.getPriority() != null) {
                                itemJson.put("priority", item.getPriority());
                            } else {
                                itemJson.put("priority", "");
                            }
                            if (StringUtils.isNotBlank(item.getPicture())) {
                                itemJson.put("picture", item.getPicture());
                            } else {
                                itemJson.put("picture", "");
                            }
                            itemsArray.put(j++, itemJson);
                        }
                        subTopicJson.put("voteItems", itemsArray);
                    } else {
                        subTopicJson.put("voteItems", "");
                    }
                    Set<CmsVoteReply> replys = sub.getVoteReplys();
                    if (replys != null && replys.size() > 0) {
                        Iterator<CmsVoteReply> replyIt = replys.iterator();
                        JSONArray replysArray = new JSONArray();
                        int j = 0;
                        while (replyIt.hasNext()) {
                            CmsVoteReply reply = replyIt.next();
                            JSONObject replyJson = new JSONObject();
                            if (reply.getId() != null) {
                                replyJson.put("id", reply.getId());
                            } else {
                                replyJson.put("id", "");
                            }
                            if (StringUtils.isNotBlank(reply.getReply())) {
                                replyJson.put("reply", reply.getReply());
                            } else {
                                replyJson.put("reply", "");
                            }
                            replysArray.put(j++, replyJson);
                        }
                        subTopicJson.put("voteReplys", replysArray);
                    } else {
                        subTopicJson.put("voteReplys", "");
                    }
                    if (StringUtils.isNotBlank(sub.getTitle())) {
                        subTopicJson.put("title", sub.getTitle());
                    } else {
                        subTopicJson.put("title", "");
                    }
                    if (sub.getType() != null) {
                        subTopicJson.put("type", sub.getType());
                    } else {
                        subTopicJson.put("type", "");
                    }
                    if (sub.getPriority() != null) {
                        subTopicJson.put("priority", sub.getPriority());
                    } else {
                        subTopicJson.put("priority", "");
                    }
                    if (sub.getId() != null) {
                        subTopicJson.put("id", sub.getId());
                    } else {
                        subTopicJson.put("id", "");
                    }
                    subtopics.put(i++, subTopicJson);
                }
            }
            json.put("subtopics", subtopics);
        }
        json.put("voteStatus", getStatus());

        return json;
    }

    /**
     * 获取投票状态 1未开始 2进行中 3已结束
     */
    public Byte getStatus() {
        Date currentTime = new Date();
        if (getStartTime() != null) {
            if (currentTime.before(getStartTime())) {//未开始
                return 1;
            } else {
                if (getEndTime() == null) {//进行中(无结束时间)
                    return 2;
                } else {
                    if (currentTime.getTime() < getEndTime().getTime()) {
                        return 2;//进行中
                    } else {
                        return 3;//已结束
                    }
                }
            }
        } else {
            if (getEndTime() == null) {//进行中(无结束时间)
                return 2;
            } else {
                if (currentTime.getTime() < getEndTime().getTime()) {
                    return 2;//进行中
                } else {
                    return 3;//已结束
                }
            }
        }
    }

    @Id
    @Column(name = "votetopic_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 重复投票限制时间，单位小时，为空不允许重复投票
     */
    @Column(name = "repeate_hour")
    private Integer repeateHour;

    /**
     * 总投票数
     */
    @Column(name = "total_count")
    private Integer totalCount;

    /**
     * 最多可以选择几项
     */
    @Column(name = "multi_select")
    private Integer multiSelect;

    /**
     * 是否限制会员
     */
    @Column(name = "is_restrict_member")
    private Boolean restrictMember;

    /**
     * 是否限制IP
     */
    @Column(name = "is_restrict_ip")
    private Boolean restrictIp;

    /**
     * 是否限制COOKIE
     */
    @Column(name = "is_restrict_cookie")
    private Boolean restrictCookie;

    /**
     * 是否禁用
     */
    @Column(name = "is_disabled")
    private Boolean disabled;

    /**
     * 是否默认主题
     */
    @Column(name = "is_def")
    private Boolean def;

    /**
     * 是否限制微信
     */
    @Column(name = "limit_weixin")
    private Boolean limitWeiXin;

    /**
     * 限定微信投票每个用户每日投票次数,为0时则投票期内限定投票一次
     */
    @Column(name = "vote_day")
    private Integer voteDay;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @SortComparator(value = PriorityComparator.class)
    private Set<CmsVoteItem> items;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true,mappedBy = "voteTopic")
    @SortComparator(value = PriorityComparator.class)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private Set<CmsVoteSubTopic> subtopics;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private Set<CmsVoteRecord> records;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }


    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getRepeateHour() {
        return repeateHour;
    }

    public void setRepeateHour(Integer repeateHour) {
        this.repeateHour = repeateHour;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(Integer multiSelect) {
        this.multiSelect = multiSelect;
    }

    public Boolean getRestrictMember() {
        return restrictMember;
    }

    public void setRestrictMember(Boolean restrictMember) {
        this.restrictMember = restrictMember;
    }

    public Boolean getRestrictIp() {
        return restrictIp;
    }


    public void setRestrictIp(Boolean restrictIp) {
        this.restrictIp = restrictIp;
    }


    public Boolean getRestrictCookie() {
        return restrictCookie;
    }

    public void setRestrictCookie(Boolean restrictCookie) {
        this.restrictCookie = restrictCookie;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getDef() {
        return def;
    }

    public void setDef(Boolean def) {
        this.def = def;
    }

    public Boolean getLimitWeiXin() {
        return limitWeiXin;
    }

    public void setLimitWeiXin(Boolean limitWeiXin) {
        this.limitWeiXin = limitWeiXin;
    }

    public Integer getVoteDay() {
        return voteDay;
    }

    public void setVoteDay(Integer voteDay) {
        this.voteDay = voteDay;
    }

    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }


    public Set<CmsVoteItem> getItems() {
        return items;
    }


    public void setItems(Set<CmsVoteItem> items) {
        this.items = items;
    }

    public Set<CmsVoteSubTopic> getSubtopics() {
        return subtopics;
    }

    public void setSubtopics(
            Set<CmsVoteSubTopic> subtopics) {
        this.subtopics = subtopics;
    }

    public Set<CmsVoteRecord> getRecords() {
        return records;
    }

    public void setRecords(
            Set<CmsVoteRecord> records) {
        this.records = records;
    }
}
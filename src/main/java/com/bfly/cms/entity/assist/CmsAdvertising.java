package com.bfly.cms.entity.assist;

import com.bfly.common.util.DateUtils;
import com.bfly.core.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * CMS广告
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:42
 */
@Entity
@Table(name = "jc_advertising")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region = "beanCache")
public class CmsAdvertising implements Serializable {
    private static final long serialVersionUID = 1L;

    public static String PROP_CODE = "code";
    public static String PROP_END_TIME = "endTime";
    public static String PROP_START_TIME = "startTime";

    @Id
    @Column(name = "advertising_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *广告名称
     */
    @Column(name = "ad_name")
    private String name;

    /**
     *广告类型
     */
    @Column(name = "category")
    private String category;

    /**
     *广告代码
     */
    @Column(name = "ad_code")
    private String code;

    /**
     *广告权重
     */
    @Column(name = "ad_weight")
    private Integer weight;

    /**
     *展现次数
     */
    @Column(name = "display_count")
    private Long displayCount;

    /**
     *点击次数
     */
    @Column(name = "click_count")
    private Long clickCount;

    /**
     *开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     *结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     *是否启用
     */
    @Column(name = "is_enabled")
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "adspace_id")
    private CmsAdvertisingSpace adspace;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;

    @ElementCollection
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region = "beanCache")
    @CollectionTable(name = "jc_advertising_attr",joinColumns = @JoinColumn(name = "advertising_id"))
    @MapKeyColumn(name = "attr_name")
    @Column(name="attr_value")
    private Map<String, String> attr;


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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(Long displayCount) {
        this.displayCount = displayCount;
    }


    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
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


    public Boolean getEnabled() {
        return enabled;
    }


    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public CmsAdvertisingSpace getAdspace() {
        return adspace;
    }


    public void setAdspace(CmsAdvertisingSpace adspace) {
        this.adspace = adspace;
    }


    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }


    public Map<String, String> getAttr() {
        return attr;
    }


    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public int getPercent() {
        if (getDisplayCount() <= 0) {
            return 0;
        } else {
            return (int) (getClickCount() * 100 / getDisplayCount());
        }
    }

    public Long getStartTimeMillis() {
        if (getStartTime() != null) {
            return getStartTime().getTime();
        } else {
            return null;
        }
    }

    public Long getEndTimeMillis() {
        if (getEndTime() != null) {
            return getEndTime().getTime();
        } else {
            return null;
        }
    }

    public void init() {
        if (getClickCount() == null) {
            setClickCount(0L);
        }
        if (getDisplayCount() == null) {
            setDisplayCount(0L);
        }
        if (getEnabled() == null) {
            setEnabled(true);
        }
        if (getWeight() == null) {
            setWeight(1);
        }
        blankToNull();
    }

    public void blankToNull() {
        if (StringUtils.isBlank(getCode())) {
            setCode(null);
        }
    }

    public JSONObject convertToJson()
            throws JSONException {
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
        if (StringUtils.isNotBlank(getCategory())) {
            json.put("category", getCategory());
        } else {
            json.put("category", "");
        }
        if (StringUtils.isNotBlank(getCode())) {
            json.put("code", getCode());
        } else {
            json.put("code", "");
        }
        if (getWeight() != null) {
            json.put("weight", getWeight());
        } else {
            json.put("weight", "");
        }
        if (getDisplayCount() != null) {
            json.put("displayCount", getDisplayCount());
        } else {
            json.put("displayCount", "");
        }
        if (getClickCount() != null) {
            json.put("clickCount", getClickCount());
        } else {
            json.put("clickCount", "");
        }
        if (getStartTime() != null) {
            json.put("startTime", DateUtils.parseDateToDateStr(getStartTime()));
        } else {
            json.put("startTime", "");
        }
        if (getEndTime() != null) {
            json.put("endTime", DateUtils.parseDateToDateStr(getEndTime()));
        } else {
            json.put("endTime", "");
        }
        if (getAdspace() != null && StringUtils.isNotBlank(getAdspace().getName())) {
            json.put("adspaceName", getAdspace().getName());
        } else {
            json.put("adspaceName", "");
        }
        if (getAdspace() != null && getAdspace().getId() != null) {
            json.put("adspaceId", getAdspace().getId());
        } else {
            json.put("adspaceId", "");
        }
        if (getEnabled() != null) {
            json.put("enabled", getEnabled());
        } else {
            json.put("enabled", "");
        }
        json.put("percent", getPercent());
        if (getAttr() != null && StringUtils.isNotBlank(getAttr().get("image_link"))) {
            json.put("attr_image_link", getAttr().get("image_link"));
        } else {
            json.put("attr_image_link", "");
        }
        if (getAttr() != null) {
            String imageUrl = getAttr().get("image_url");
            if (StringUtils.isNotBlank(imageUrl)) {
                json.put("attr_image_url", imageUrl);
            } else {
                json.put("attr_image_url", "");
            }
        } else {
            json.put("attr_image_url", "");
        }
        if (getAttr() != null && StringUtils.isNotBlank(getAttr().get("image_target"))) {
            json.put("attr_image_target", getAttr().get("image_target"));
        } else {
            json.put("attr_image_target", "");
        }
        if (getAttr() != null && StringUtils.isNotBlank(getAttr().get("image_title"))) {
            json.put("attr_image_title", getAttr().get("image_title"));
        } else {
            json.put("attr_image_title", "");
        }
        if (getAttr() != null && StringUtils.isNotBlank(getAttr().get("image_width"))) {
            json.put("attr_image_width", getAttr().get("image_width"));
        } else {
            json.put("attr_image_width", "");
        }
        if (getAttr() != null && StringUtils.isNotBlank(getAttr().get("image_height"))) {
            json.put("attr_image_height", getAttr().get("image_height"));
        } else {
            json.put("attr_image_height", "");
        }
        if (getAttr() != null) {
            String flashUrl = getAttr().get("flash_url");
            if (StringUtils.isNotBlank(flashUrl)) {
                json.put("attr_flash_url", flashUrl);
            } else {
                json.put("attr_flash_url", "");
            }
        } else {
            json.put("attr_flash_url", "");
        }
        if (getAttr() != null && StringUtils.isNotBlank(getAttr().get("flash_width"))) {
            json.put("attr_flash_width", getAttr().get("flash_width"));
        } else {
            json.put("attr_flash_width", "");
        }
        if (getAttr() != null && StringUtils.isNotBlank(getAttr().get("flash_height"))) {
            json.put("attr_flash_height", getAttr().get("flash_height"));
        } else {
            json.put("attr_flash_height", "");
        }
        if (getAttr() != null && StringUtils.isNotBlank(getAttr().get("text_link"))) {
            json.put("attr_text_link", getAttr().get("text_link"));
        } else {
            json.put("attr_text_link", "");
        }
        if (getAttr() != null && StringUtils.isNotBlank(getAttr().get("text_target"))) {
            json.put("attr_text_target", getAttr().get("text_target"));
        } else {
            json.put("attr_text_target", "");
        }
        if (getAttr() != null && StringUtils.isNotBlank(getAttr().get("text_color"))) {
            json.put("attr_text_color", getAttr().get("text_color"));
        } else {
            json.put("attr_text_color", "");
        }
        if (getAttr() != null && StringUtils.isNotBlank(getAttr().get("text_font"))) {
            json.put("attr_text_font", getAttr().get("text_font"));
        } else {
            json.put("attr_text_font", "");
        }
        if (getAttr() != null && StringUtils.isNotBlank(getAttr().get("text_title"))) {
            json.put("attr_text_title", getAttr().get("text_title"));
        } else {
            json.put("attr_text_title", "");
        }
        return json;
    }
}
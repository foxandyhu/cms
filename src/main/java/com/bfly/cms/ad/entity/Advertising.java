package com.bfly.cms.ad.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * CMS广告
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:42
 */
@Entity
@Table(name = "ad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class Advertising implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 广告名称
     */
    @Column(name = "ad_name")
    private String name;

    /**
     * 广告类型
     */
    @Column(name = "category")
    private String category;

    /**
     * 广告代码
     */
    @Column(name = "ad_code")
    private String code;

    /**
     * 广告权重
     */
    @Column(name = "ad_weight")
    private int weight;

    /**
     * 展现次数
     */
    @Column(name = "display_count")
    private int displayCount;

    /**
     * 点击次数
     */
    @Column(name = "click_count")
    private int clickCount;

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
     * 是否启用
     */
    @Column(name = "is_enabled")
    private boolean enabled;

    /**
     * 所属广告位
     */
    @ManyToOne
    @JoinColumn(name = "adspace_id")
    private AdvertisingSpace adspace;

    /**
     * 广告其他属性
     */
    @ElementCollection
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @CollectionTable(name = "ad_attr", joinColumns = @JoinColumn(name = "ad_id"))
    @MapKeyColumn(name = "attr_name")
    @Column(name = "attr_value")
    private Map<String, String> attr;


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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(int displayCount) {
        this.displayCount = displayCount;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public AdvertisingSpace getAdspace() {
        return adspace;
    }

    public void setAdspace(AdvertisingSpace adspace) {
        this.adspace = adspace;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
}
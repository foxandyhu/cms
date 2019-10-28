package com.bfly.cms.ad.entity;

import com.bfly.core.enums.AdvertisingType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
public class Advertising implements Serializable {

    private static final long serialVersionUID = 71159170989050961L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 广告名称
     */
    @Column(name = "name")
    @NotBlank(message = "广告名称不能为空!")
    private String name;

    /**
     * 广告类型
     *
     * @see com.bfly.core.enums.AdvertisingType
     */
    @Column(name = "type")
    private int type;

    /**
     * 展现次数
     */
    @Column(name = "display_count")
    @Min(value = 0, message = "展现次数必须是正整数0!")
    private int displayCount;

    /**
     * 点击次数
     */
    @Column(name = "click_count")
    @Min(value = 0, message = "点击次数必须是正整数0!")
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
    @JoinColumn(name = "space_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private AdvertisingSpace space;

    /**
     * 广告其他属性
     */
    @ElementCollection
    @CollectionTable(name = "ad_attr", joinColumns = @JoinColumn(name = "ad_id"))
    @MapKeyColumn(name = "attr_name")
    @Column(name = "attr_value")
    private Map<String, String> attr;


    /**
     * 获得类型名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/18 10:40
     */
    public String getTypeName() {
        AdvertisingType type = AdvertisingType.getType(getType());
        return type == null ? "" : type.getName();
    }

    /**
     * 点击率
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/18 11:25
     */
    public String getClickRate() {
        return (getDisplayCount() == 0 ? 0 : (getClickCount() / getDisplayCount())) + "%";
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public AdvertisingSpace getSpace() {
        return space;
    }

    public void setSpace(AdvertisingSpace space) {
        this.space = space;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
}
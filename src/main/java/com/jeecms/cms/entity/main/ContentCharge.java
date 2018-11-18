package com.jeecms.cms.entity.main;

import org.hibernate.annotations.Cache;
import com.jeecms.common.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 收费内容配置
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 13:03
 */
@Entity
@Table(name = "jc_content_charge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ContentCharge implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Short MODEL_FREE = 0;
    public static final Short MODEL_CHARGE = 1;
    public static final Short MODEL_REWARD = 2;

    @Column(name = "content_id")
    private Integer id;

    /**
     * 收费金额
     */
    @Column(name = "charge_amount")
    private Double chargeAmount;

    /**
     * 已累计收费
     */
    @Column(name = "total_amount")
    private Double totalAmount;

    /**
     * 年金额
     */
    @Column(name = "year_amount")
    private Double yearAmount;

    /**
     * 月金额
     */
    @Column(name = "month_amount")
    private Double monthAmount;

    /**
     * 日金额
     */
    @Column(name = "day_amount")
    private Double dayAmount;

    /**
     * 最后购买时间
     */
    @Column(name = "last_buy_time")
    private Date lastBuyTime;

    /**
     * 模式，1收费 2打赏
     */
    @Column(name = "charge_reward")
    private Short chargeReward;

    /**
     * 随机 最小值
     */
    @Column(name = "reward_random_min")
    private Double rewardRandomMin;

    /**
     * 随机 最大值
     */
    @Column(name = "reward_random_max")
    private Double rewardRandomMax;

    /**
     * 打赏模式(0随机 1固定)
     */
    @Column(name = "reward_pattern")
    private Boolean rewardPattern;

    @Id
    @OneToOne
    @JoinColumn(name = "content_id")
    private Content content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getYearAmount() {
        return yearAmount;
    }


    public void setYearAmount(Double yearAmount) {
        this.yearAmount = yearAmount;
    }

    public Double getMonthAmount() {
        return monthAmount;
    }


    public void setMonthAmount(Double monthAmount) {
        this.monthAmount = monthAmount;
    }


    public Double getDayAmount() {
        return dayAmount;
    }

    public void setDayAmount(Double dayAmount) {
        this.dayAmount = dayAmount;
    }


    public Date getLastBuyTime() {
        return lastBuyTime;
    }

    public void setLastBuyTime(Date lastBuyTime) {
        this.lastBuyTime = lastBuyTime;
    }

    public Short getChargeReward() {
        return chargeReward;
    }

    public void setChargeReward(Short chargeReward) {
        this.chargeReward = chargeReward;
    }

    public Double getRewardRandomMin() {
        return rewardRandomMin;
    }

    public void setRewardRandomMin(Double rewardRandomMin) {
        this.rewardRandomMin = rewardRandomMin;
    }

    public Double getRewardRandomMax() {
        return rewardRandomMax;
    }

    public void setRewardRandomMax(Double rewardRandomMax) {
        this.rewardRandomMax = rewardRandomMax;
    }

    public Boolean getRewardPattern() {
        return rewardPattern;
    }

    public void setRewardPattern(Boolean rewardPattern) {
        this.rewardPattern = rewardPattern;
    }

    public Content getContent() {
        return content;
    }


    public void setContent(Content content) {
        this.content = content;
    }


    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        json = baseJson(json);
        if (getContent() != null && getContent().getId() != null) {
            json.put("contentId", getContent().getId());
        } else {
            json.put("contentId", "");
        }
        if (getContent() != null && StringUtils.isNotBlank(getContent().getTitle())) {
            json.put("contentTitle", getContent().getTitle());
        } else {
            json.put("contentTitle", "");
        }
        return json;
    }

    public JSONObject baseJson(JSONObject json) {
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (getChargeAmount() != null) {
            json.put("chargeAmount", getChargeAmount());
        } else {
            json.put("chargeAmount", "");
        }
        if (getTotalAmount() != null) {
            json.put("totalAmount", getTotalAmount());
        } else {
            json.put("totalAmount", "");
        }
        if (getYearAmount() != null) {
            json.put("yearAmount", getYearAmount());
        } else {
            json.put("yearAmount", "");
        }
        if (getMonthAmount() != null) {
            json.put("monthAmount", getMonthAmount());
        } else {
            json.put("monthAmount", "");
        }
        if (getDayAmount() != null) {
            json.put("dayAmount", getDayAmount());
        } else {
            json.put("dayAmount", "");
        }
        if (getChargeReward() != null) {
            json.put("chargeReward", getChargeReward());
        } else {
            json.put("chargeReward", "");
        }
        if (getLastBuyTime() != null) {
            json.put("lastBuyTime", DateUtils.parseDateToTimeStr(getLastBuyTime()));
        } else {
            json.put("lastBuyTime", "");
        }
        return json;
    }

    public void init() {
        if (getChargeAmount() == null) {
            setChargeAmount(0d);
        }
        if (getDayAmount() == null) {
            setDayAmount(0d);
        }
        if (getMonthAmount() == null) {
            setMonthAmount(0d);
        }
        if (getYearAmount() == null) {
            setYearAmount(0d);
        }
        if (getTotalAmount() == null) {
            setTotalAmount(0d);
        }
        if (getRewardRandomMax() == null) {
            setRewardRandomMax(0d);
        }
        if (getRewardRandomMin() == null) {
            setRewardRandomMin(0d);
        }
    }
}
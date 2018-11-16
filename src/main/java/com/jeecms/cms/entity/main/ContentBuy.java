package com.jeecms.cms.entity.main;

import com.jeecms.common.util.DateUtils;
import com.jeecms.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 收费文章购买记录
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 12:55
 */
@Entity
@Table(name = "jc_content_buy")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ContentBuy implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Integer PAY_METHOD_WECHAT = 1;
    public static final Integer PAY_METHOD_ALIPAY = 2;

    //订单号错误
    public static final Integer PRE_PAY_STATUS_ORDER_NUM_ERROR = 2;
    //订单成功
    public static final Integer PRE_PAY_STATUS_SUCCESS = 1;
    //订单金额不足以购买内容
    public static final Integer PRE_PAY_STATUS_ORDER_AMOUNT_NOT_ENOUGH = 3;

    @Id
    @Column(name = "content_buy_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 成交金额
     */
    @Column(name = "charge_amount")
    private Double chargeAmount;

    /**
     * 笔者所得
     */
    @Column(name = "author_amount")
    private Double authorAmount;

    /**
     * 平台所得
     */
    @Column(name = "plat_amount")
    private Double platAmount;

    /**
     * 成交时间
     */
    @Column(name = "buy_time")
    private Date buyTime;

    /**
     * 是否已经结算给作者(0未结算 1已经结算)
     */
    @Column(name = "has_paid_author")
    private boolean hasPaidAuthor;

    /**
     * 订单号
     */
    @Column(name = "order_number")
    private String orderNumber;

    /**
     * 微信支付订单号
     */
    @Column(name = "order_num_weixin")
    private String orderNumWeiXin;

    /**
     * 支付宝订单号
     */
    @Column(name = "order_num_alipay")
    private String orderNumAliPay;

    /**
     * 模式 1收费 2打赏
     */
    @Column(name = "charge_reward")
    private Short chargeReward;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @ManyToOne
    @JoinColumn(name = "buy_user_id")
    private CmsUser buyUser;

    @ManyToOne
    @JoinColumn(name = "author_user_id")
    private CmsUser authorUser;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Double getAuthorAmount() {
        return authorAmount;
    }

    public void setAuthorAmount(Double authorAmount) {
        this.authorAmount = authorAmount;
    }

    public Double getPlatAmount() {
        return platAmount;
    }

    public void setPlatAmount(Double platAmount) {
        this.platAmount = platAmount;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public boolean getHasPaidAuthor() {
        return hasPaidAuthor;
    }

    public void setHasPaidAuthor(boolean hasPaidAuthor) {
        this.hasPaidAuthor = hasPaidAuthor;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumWeiXin() {
        return orderNumWeiXin;
    }

    public void setOrderNumWeiXin(String orderNumWeiXin) {
        this.orderNumWeiXin = orderNumWeiXin;
    }

    public String getOrderNumAliPay() {
        return orderNumAliPay;
    }

    public void setOrderNumAliPay(String orderNumAliPay) {
        this.orderNumAliPay = orderNumAliPay;
    }

    public Short getChargeReward() {
        return chargeReward;
    }

    public void setChargeReward(Short chargeReward) {
        this.chargeReward = chargeReward;
    }

    /**
     * Return the value associated with the column: contentId
     */
    public Content getContent() {
        return content;
    }

    /**
     * Set the value related to the column: contentId
     *
     * @param content the contentId value
     */
    public void setContent(Content content) {
        this.content = content;
    }


    /**
     * Return the value associated with the column: buy_user_id
     */
    public CmsUser getBuyUser() {
        return buyUser;
    }

    /**
     * Set the value related to the column: buy_user_id
     *
     * @param buyUser the buy_user_id value
     */
    public void setBuyUser(CmsUser buyUser) {
        this.buyUser = buyUser;
    }


    public CmsUser getAuthorUser() {
        return authorUser;
    }


    public void setAuthorUser(CmsUser authorUser) {
        this.authorUser = authorUser;
    }

    public boolean getUserHasPaid() {
        if (StringUtils.isNotBlank(getOrderNumWeiXin())
                || StringUtils.isNotBlank(getOrderNumAliPay())) {
            return true;
        } else {
            return false;
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
        if (getChargeAmount() != null) {
            json.put("chargeAmount", getChargeAmount());
        } else {
            json.put("chargeAmount", "");
        }
        if (getAuthorAmount() != null) {
            json.put("authorAmount", getAuthorAmount());
        } else {
            json.put("authorAmount", "");
        }
        if (getPlatAmount() != null) {
            json.put("platAmount", getPlatAmount());
        } else {
            json.put("platAmount", "");
        }
        if (getBuyTime() != null) {
            json.put("buyTime", DateUtils.parseDateToTimeStr(getBuyTime()));
        } else {
            json.put("buyTime", "");
        }
        if (StringUtils.isNotBlank(getOrderNumber())) {
            json.put("orderNumber", getOrderNumber());
        } else {
            json.put("orderNumber", "");
        }
        if (StringUtils.isNotBlank(getOrderNumWeiXin())) {
            json.put("orderNumWeiXin", getOrderNumWeiXin());
        } else {
            json.put("orderNumWeiXin", "");
        }
        if (StringUtils.isNotBlank(getOrderNumAliPay())) {
            json.put("orderNumAliPay", getOrderNumAliPay());
        } else {
            json.put("orderNumAliPay", "");
        }
        if (getChargeReward() != null) {
            json.put("chargeReward", getChargeReward());
        } else {
            json.put("chargeReward", "");
        }
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
        if (getContent() != null && StringUtils.isNotBlank(getContent().getUrl())) {
            json.put("contentUrl", getContent().getUrl());
        } else {
            json.put("contentUrl", "");
        }
        if (getBuyUser() != null && StringUtils.isNotBlank(getBuyUser().getUsername())) {
            json.put("buyUserName", getBuyUser().getUsername());
        } else {
            json.put("buyUserName", "");
        }
        if (getBuyUser() != null && StringUtils.isNotBlank(getBuyUser().getRealname())) {
            json.put("buyRealname", getBuyUser().getRealname());
        } else {
            json.put("buyRealname", "");
        }
        if (getBuyUser() != null && getBuyUser().getId() != null) {
            json.put("buyUserId", getBuyUser().getId());
        } else {
            json.put("buyUserId", "");
        }
        if (getAuthorUser() != null && StringUtils.isNotBlank(getAuthorUser().getUsername())) {
            json.put("authorUserName", getAuthorUser().getUsername());
        } else {
            json.put("authorUserName", "");
        }
        if (getAuthorUser() != null && StringUtils.isNotBlank(getAuthorUser().getRealname())) {
            json.put("authorRealname", getAuthorUser().getRealname());
        } else {
            json.put("authorRealname", "");
        }
        if (getAuthorUser() != null && getAuthorUser().getId() != null) {
            json.put("authorUserId", getAuthorUser().getId());
        } else {
            json.put("authorUserId", "");
        }
        return json;
    }

    public int getPrePayStatus() {
        return prePayStatus;
    }

    public void setPrePayStatus(int prePayStatus) {
        this.prePayStatus = prePayStatus;
    }

    @Transient
    private int prePayStatus;
}
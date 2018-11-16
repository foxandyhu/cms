package com.jeecms.core.entity;

import com.jeecms.common.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户稿费收费配置类
 * @author andy_hulibo@163.com
 * @date 2018/11/15 22:21
 */
@Entity
@Table(name = "jc_user_account")
public class CmsUserAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final byte DRAW_WEIXIN = 0;

    @Column(name = "user_id")
    private Integer id;

    /**
     * 微信账号名
     */
    @Column(name = "account_weixin")
    private String accountWeixin;

    /**
     * 微信账号openid
     */
    @Column(name = "account_weixin_openId")
    private String accountWeixinOpenId;

    /**
     * 支付宝账号
     */
    @Column(name = "account_alipy")
    private String accountAlipy;

    /**
     * 稿费总金额
     */
    @Column(name = "content_total_amount")
    private Double contentTotalAmount;

    /**
     * 待提现稿费金额
     */
    @Column(name = "content_no_pay_amount")
    private Double contentNoPayAmount;

    /**
     * 稿费年金额
     */
    @Column(name = "content_year_amount")
    private Double contentYearAmount;

    /**
     * 稿费本月金额
     */
    @Column(name = "content_month_amount")
    private Double contentMonthAmount;

    /**
     * 稿费本日金额
     */
    @Column(name = "content_day_amount")
    private Double contentDayAmount;

    /**
     * 累计提现次数
     */
    @Column(name = "draw_count")
    private Integer drawCount;

    /**
     * 被用户购买次数
     */
    @Column(name = "content_buy_count")
    private Integer contentBuyCount;

    /**
     * 上次提现时间
     */
    @Column(name = "last_draw_time")
    private Date lastDrawTime;

    /**
     * 上次用户购买时间
     */
    @Column(name = "last_buy_time")
    private Date lastBuyTime;

    /**
     * 提现账户(0微信 1支付宝)
     */
    @Column(name = "draw_account")
    private Short drawAccount;

    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private CmsUser user;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountWeixin() {
        return accountWeixin;
    }

    public void setAccountWeixin(String accountWeixin) {
        this.accountWeixin = accountWeixin;
    }

    public String getAccountAlipy() {
        return accountAlipy;
    }

    public void setAccountAlipy(String accountAlipy) {
        this.accountAlipy = accountAlipy;
    }

    public String getAccountWeixinOpenId() {
        return accountWeixinOpenId;
    }


    public void setAccountWeixinOpenId(String accountWeixinOpenId) {
        this.accountWeixinOpenId = accountWeixinOpenId;
    }


    public Double getContentTotalAmount() {
        return contentTotalAmount;
    }

    public void setContentTotalAmount(Double contentTotalAmount) {
        this.contentTotalAmount = contentTotalAmount;
    }


    public Double getContentNoPayAmount() {
        return contentNoPayAmount;
    }

    public void setContentNoPayAmount(Double contentNoPayAmount) {
        this.contentNoPayAmount = contentNoPayAmount;
    }

    public Double getContentYearAmount() {
        return contentYearAmount;
    }


    public void setContentYearAmount(Double contentYearAmount) {
        this.contentYearAmount = contentYearAmount;
    }


    public Double getContentMonthAmount() {
        return contentMonthAmount;
    }


    public void setContentMonthAmount(Double contentMonthAmount) {
        this.contentMonthAmount = contentMonthAmount;
    }


    public Double getContentDayAmount() {
        return contentDayAmount;
    }


    public void setContentDayAmount(Double contentDayAmount) {
        this.contentDayAmount = contentDayAmount;
    }


    public Integer getDrawCount() {
        return drawCount;
    }

    public void setDrawCount(Integer drawCount) {
        this.drawCount = drawCount;
    }

    public Integer getContentBuyCount() {
        return contentBuyCount;
    }

    public void setContentBuyCount(Integer contentBuyCount) {
        this.contentBuyCount = contentBuyCount;
    }

    public Date getLastDrawTime() {
        return lastDrawTime;
    }

    public void setLastDrawTime(Date lastDrawTime) {
        this.lastDrawTime = lastDrawTime;
    }

    public Date getLastBuyTime() {
        return lastBuyTime;
    }

    public void setLastBuyTime(Date lastBuyTime) {
        this.lastBuyTime = lastBuyTime;
    }

    public Short getDrawAccount() {
        return drawAccount;
    }

    public void setDrawAccount(Short drawAccount) {
        this.drawAccount = drawAccount;
    }

    public CmsUser getUser() {
        return user;
    }


    public void setUser(CmsUser user) {
        this.user = user;
    }


    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (getContentTotalAmount() != null) {
            json.put("contentTotalAmount", getContentTotalAmount());
        } else {
            json.put("contentTotalAmount", "");
        }
        if (getContentNoPayAmount() != null) {
            json.put("contentNoPayAmount", getContentNoPayAmount());
        } else {
            json.put("contentNoPayAmount", "");
        }
        if (getContentYearAmount() != null) {
            json.put("contentYearAmount", getContentYearAmount());
        } else {
            json.put("contentYearAmount", "");
        }
        if (getContentMonthAmount() != null) {
            json.put("contentMonthAmount", getContentMonthAmount());
        } else {
            json.put("contentMonthAmount", "");
        }
        if (getContentDayAmount() != null) {
            json.put("contentDayAmount", getContentDayAmount());
        } else {
            json.put("contentDayAmount", "");
        }
        if (getDrawCount() != null) {
            json.put("drawCount", getDrawCount());
        } else {
            json.put("drawCount", "");
        }
        if (getContentBuyCount() != null) {
            json.put("contentBuyCount", getContentBuyCount());
        } else {
            json.put("contentBuyCount", "");
        }
        if (getDrawAccount() != null) {
            json.put("drawAccount", getDrawAccount());
        } else {
            json.put("drawAccount", "");
        }
        if (getUser() != null && StringUtils.isNotBlank(getUser().getUsername())) {
            json.put("userName", getUser().getUsername());
        } else {
            json.put("userName", "");
        }
        if (getUser() != null && StringUtils.isNotBlank(getUser().getRealname())) {
            json.put("realname", getUser().getRealname());
        } else {
            json.put("realname", "");
        }
        if (getLastDrawTime() != null) {
            json.put("lastDrawTime", DateUtils.parseDateToTimeStr(getLastDrawTime()));
        } else {
            json.put("lastDrawTime", "");
        }
        if (getLastBuyTime() != null) {
            json.put("lastBuyTime", DateUtils.parseDateToTimeStr(getLastBuyTime()));
        } else {
            json.put("lastBuyTime", "");
        }
        return json;
    }
}
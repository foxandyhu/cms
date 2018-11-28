package com.bfly.cms.system.entity;

import com.bfly.common.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 内容收费配置
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:01
 */
@Entity
@Table(name = "jc_config_content_charge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsConfigContentCharge implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getWeixinAppId())) {
            json.put("weixinAppId", getWeixinAppId());
        } else {
            json.put("weixinAppId", "");
        }
        if (StringUtils.isNotBlank(getWeixinSecret())) {
            json.put("weixinSecret", getWeixinSecret());
        } else {
            json.put("weixinSecret", "");
        }
        if (StringUtils.isNotBlank(getWeixinAccount())) {
            json.put("weixinAccount", getWeixinAccount());
        } else {
            json.put("weixinAccount", "");
        }
        if (StringUtils.isNotBlank(getWeixinPassword())) {
            json.put("weixinPassword", getWeixinPassword());
        } else {
            json.put("weixinPassword", "");
        }
        if (StringUtils.isNotBlank(getAlipayPartnerId())) {
            json.put("alipayPartnerId", getAlipayPartnerId());
        } else {
            json.put("alipayPartnerId", "");
        }
        if (StringUtils.isNotBlank(getAlipayAccount())) {
            json.put("alipayAccount", getAlipayAccount());
        } else {
            json.put("alipayAccount", "");
        }
        if (StringUtils.isNotBlank(getAlipayKey())) {
            json.put("alipayKey", getAlipayKey());
        } else {
            json.put("alipayKey", "");
        }
        if (StringUtils.isNotBlank(getAlipayAppId())) {
            json.put("alipayAppId", getAlipayAppId());
        } else {
            json.put("alipayAppId", "");
        }
        if (StringUtils.isNotBlank(getAlipayPublicKey())) {
            json.put("alipayPublicKey", getAlipayPublicKey());
        } else {
            json.put("alipayPublicKey", "");
        }
        if (StringUtils.isNotBlank(getAlipayPrivateKey())) {
            json.put("alipayPrivateKey", getAlipayPrivateKey());
        } else {
            json.put("alipayPrivateKey", "");
        }
        if (getChargeRatio() != null) {
            json.put("chargeRatio", getChargeRatio());
        } else {
            json.put("chargeRatio", "");
        }
        if (getMinDrawAmount() != null) {
            json.put("minDrawAmount", getMinDrawAmount());
        } else {
            json.put("minDrawAmount", "");
        }
        if (getCommissionTotal() != null) {
            json.put("commissionTotal", getCommissionTotal());
        } else {
            json.put("commissionTotal", "");
        }
        if (getCommissionYear() != null) {
            json.put("commissionYear", getCommissionYear());
        } else {
            json.put("commissionYear", "");
        }
        if (getCommissionMonth() != null) {
            json.put("commissionMonth", getCommissionMonth());
        } else {
            json.put("commissionMonth", "");
        }
        if (getCommissionDay() != null) {
            json.put("commissionDay", getCommissionDay());
        } else {
            json.put("commissionDay", "");
        }
        if (getLastBuyTime() != null) {
            json.put("lastBuyTime", DateUtils.parseDateToDateStr(getLastBuyTime()));
        } else {
            json.put("lastBuyTime", "");
        }
        if (StringUtils.isNotBlank(getPayTransferPassword())) {
            json.put("payTransferPassword", getPayTransferPassword());
        } else {
            json.put("payTransferPassword", "");
        }
        if (StringUtils.isNotBlank(getTransferApiPassword())) {
            json.put("transferApiPassword", getTransferApiPassword());
        } else {
            json.put("transferApiPassword", "");
        }
        if (getRewardMin() != null) {
            json.put("rewardMin", getRewardMin());
        } else {
            json.put("rewardMin", "");
        }
        if (getRewardMax() != null) {
            json.put("rewardMax", getRewardMax());
        } else {
            json.put("rewardMax", "");
        }
        if (getRewardPattern() != null) {
            json.put("rewardPattern", getRewardPattern());
        } else {
            json.put("rewardPattern", "");
        }
        return json;
    }

    @Id
    @Column(name = "config_content_id")
    private Integer id;

    /**
     * 微信服务号APPID
     */
    @Column(name = "weixin_appid")
    private String weixinAppId;

    /**
     * 微信公众号secret
     */
    @Column(name = "weixin_secret")
    private String weixinSecret;

    /**
     * 微信支付商户号
     */
    @Column(name = "weixin_account")
    private String weixinAccount;

    /**
     * 微信支付商户密钥
     */
    @Column(name = "weixin_password")
    private String weixinPassword;

    /**
     * 支付宝合作者ID
     */
    @Column(name = "alipay_partner_id")
    private String alipayPartnerId;

    /**
     * 支付宝签约账户
     */
    @Column(name = "alipay_account")
    private String alipayAccount;

    /**
     * 支付宝公钥
     */
    @Column(name = "alipay_key")
    private String alipayKey;

    /**
     * 支付宝应用ID
     */
    @Column(name = "alipay_appid")
    private String alipayAppId;

    /**
     * 支付宝RSA公钥
     */
    @Column(name = "alipay_public_key")
    private String alipayPublicKey;

    /**
     * 支付宝RSA私钥
     */
    @Column(name = "alipay_private_key")
    private String alipayPrivateKey;

    /**
     * 抽成比例
     */
    @Column(name = "charge_ratio")
    private Double chargeRatio;

    /**
     * 最小提现额
     */
    @Column(name = "min_draw_amount")
    private Double minDrawAmount;

    /**
     * 佣金抽成总金额
     */
    @Column(name = "commission_total")
    private Double commissionTotal;

    /**
     * 佣金抽成年金额
     */
    @Column(name = "commission_year")
    private Double commissionYear;

    /**
     * 佣金抽成月金额
     */
    @Column(name = "commission_month")
    private Double commissionMonth;

    /**
     * 佣金抽成日金额
     */
    @Column(name = "commission_day")
    private Double commissionDay;

    /**
     * 最后购买时间
     */
    @Column(name = "last_buy_time")
    private Date lastBuyTime;

    /**
     * 转账支付密码（管理后台验证）
     */
    @Column(name = "pay_transfer_password")
    private String payTransferPassword;

    /**
     * 企业转账接口API密钥
     */
    @Column(name = "transfer_api_password")
    private String transferApiPassword;

    /**
     * 打赏随机数最小值
     */
    @Column(name = "reward_min")
    private Double rewardMin;

    /**
     * 打赏随机数最小值
     */
    @Column(name = "reward_max")
    private Double rewardMax;

    /**
     * 打赏模式(0随机 1固定)
     */
    @Column(name = "reward_pattern")
    private Boolean rewardPattern;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeixinAppId() {
        return weixinAppId;
    }

    public void setWeixinAppId(String weixinAppId) {
        this.weixinAppId = weixinAppId;
    }

    public String getWeixinSecret() {
        return weixinSecret;
    }

    public void setWeixinSecret(String weixinSecret) {
        this.weixinSecret = weixinSecret;
    }

    public String getWeixinAccount() {
        return weixinAccount;
    }

    public void setWeixinAccount(String weixinAccount) {
        this.weixinAccount = weixinAccount;
    }

    public String getWeixinPassword() {
        return weixinPassword;
    }

    public void setWeixinPassword(String weixinPassword) {
        this.weixinPassword = weixinPassword;
    }

    public Double getChargeRatio() {
        return chargeRatio;
    }

    public void setChargeRatio(Double chargeRatio) {
        this.chargeRatio = chargeRatio;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getAlipayKey() {
        return alipayKey;
    }

    public void setAlipayKey(String alipayKey) {
        this.alipayKey = alipayKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getAlipayPartnerId() {
        return alipayPartnerId;
    }

    public void setAlipayPartnerId(String alipayPartnerId) {
        this.alipayPartnerId = alipayPartnerId;
    }

    public String getAlipayAppId() {
        return alipayAppId;
    }

    public void setAlipayAppId(String alipayAppId) {
        this.alipayAppId = alipayAppId;
    }

    public String getAlipayPrivateKey() {
        return alipayPrivateKey;
    }

    public void setAlipayPrivateKey(String alipayPrivateKey) {
        this.alipayPrivateKey = alipayPrivateKey;
    }

    public Double getMinDrawAmount() {
        return minDrawAmount;
    }

    public void setMinDrawAmount(Double minDrawAmount) {
        this.minDrawAmount = minDrawAmount;
    }

    public Double getCommissionTotal() {
        return commissionTotal;
    }

    public void setCommissionTotal(Double commissionTotal) {
        this.commissionTotal = commissionTotal;
    }

    public Double getCommissionYear() {
        return commissionYear;
    }

    public void setCommissionYear(Double commissionYear) {
        this.commissionYear = commissionYear;
    }

    public Double getCommissionMonth() {
        return commissionMonth;
    }

    public void setCommissionMonth(Double commissionMonth) {
        this.commissionMonth = commissionMonth;
    }

    public Double getCommissionDay() {
        return commissionDay;
    }

    public void setCommissionDay(Double commissionDay) {
        this.commissionDay = commissionDay;
    }

    public Date getLastBuyTime() {
        return lastBuyTime;
    }

    public void setLastBuyTime(Date lastBuyTime) {
        this.lastBuyTime = lastBuyTime;
    }

    public String getPayTransferPassword() {
        return payTransferPassword;
    }

    public void setPayTransferPassword(String payTransferPassword) {
        this.payTransferPassword = payTransferPassword;
    }

    public String getTransferApiPassword() {
        return transferApiPassword;
    }

    public void setTransferApiPassword(String transferApiPassword) {
        this.transferApiPassword = transferApiPassword;
    }

    public Double getRewardMin() {
        return rewardMin;
    }

    public void setRewardMin(Double rewardMin) {
        this.rewardMin = rewardMin;
    }

    public Double getRewardMax() {
        return rewardMax;
    }

    public void setRewardMax(Double rewardMax) {
        this.rewardMax = rewardMax;
    }

    public Boolean getRewardPattern() {
        return rewardPattern;
    }

    public void setRewardPattern(Boolean rewardPattern) {
        this.rewardPattern = rewardPattern;
    }

}
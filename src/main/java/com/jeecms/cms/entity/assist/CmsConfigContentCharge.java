package com.jeecms.cms.entity.assist;

import com.jeecms.common.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.io.Serializable;

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

    // primary key
    private Integer id;

    // fields
    private String weixinAppId;
    private String weixinSecret;
    private String weixinAccount;
    private String weixinPassword;
    private String alipayPartnerId;
    private String alipayAccount;
    private String alipayKey;

    private String alipayAppId;
    private String alipayPublicKey;
    private String alipayPrivateKey;

    private Double chargeRatio;
    private Double minDrawAmount;
    private Double commissionTotal;
    private Double commissionYear;
    private Double commissionMonth;
    private Double commissionDay;
    private java.util.Date lastBuyTime;
    private String payTransferPassword;
    private String transferApiPassword;
    private Double rewardMin;
    private Double rewardMax;
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

    public java.util.Date getLastBuyTime() {
        return lastBuyTime;
    }

    public void setLastBuyTime(java.util.Date lastBuyTime) {
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
package com.jeecms.cms.entity.assist;

import com.jeecms.common.util.DateUtils;
import com.jeecms.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户账户提现支付
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 13:53
 */
@Entity
@Table(name = "jc_account_pay")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsAccountPay implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getDrawNum())) {
            json.put("drawNum", getDrawNum());
        } else {
            json.put("drawNum", "");
        }
        if (StringUtils.isNotBlank(getPayAccount())) {
            json.put("payAccount", getPayAccount());
        } else {
            json.put("payAccount", "");
        }
        if (StringUtils.isNotBlank(getDrawAccount())) {
            json.put("drawAccount", getDrawAccount());
        } else {
            json.put("drawAccount", "");
        }
        if (getPayTime() != null) {
            json.put("payTime", DateUtils.parseDateToDateStr(getPayTime()));
        } else {
            json.put("payTime", "");
        }
        if (StringUtils.isNotBlank(getWeixinNum())) {
            json.put("weixinNum", getWeixinNum());
        } else {
            json.put("weixinNum", "");
        }
        if (StringUtils.isNotBlank(getAlipayNum())) {
            json.put("alipayNum", getAlipayNum());
        } else {
            json.put("alipayNum", "");
        }
        if (getPayUser() != null && getPayUser().getId() != null) {
            json.put("payUserId", getPayUser().getId());
        } else {
            json.put("payUserId", "");
        }
        if (getPayUser() != null && StringUtils.isNotBlank(getPayUser().getUsername())) {
            json.put("payUserName", getPayUser().getUsername());
        } else {
            json.put("payUserName", "");
        }
        if (getDrawUser() != null && getDrawUser().getId() != null) {
            json.put("drawUserId", getDrawUser().getId());
        } else {
            json.put("drawUserId", "");
        }
        if (getDrawUser() != null && StringUtils.isNotBlank(getDrawUser().getUsername())) {
            json.put("drawUserName", getDrawUser().getUsername());
        } else {
            json.put("drawUserName", "");
        }
        return json;
    }

    @Id
    @Column(name = "account_pay_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 内部流水号
     */
    @Column(name = "draw_num")
    private String drawNum;

    /**
     * 支出账户
     */
    @Column(name = "pay_account")
    private String payAccount;

    /**
     * 收入账户(微信账户名)
     */
    @Column(name = "draw_account")
    private String drawAccount;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 微信流水号
     */
    @Column(name = "weixin_num")
    private String weixinNum;

    /**
     * 支付宝流水号
     */
    @Column(name = "alipay_num")
    private String alipayNum;

    @ManyToOne
    @JoinColumn(name = "pay_user_id")
    private CmsUser payUser;

    @ManyToOne
    @JoinColumn(name = "draw_user_id")
    private CmsUser drawUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrawNum() {
        return drawNum;
    }


    public void setDrawNum(String drawNum) {
        this.drawNum = drawNum;
    }

    public String getPayAccount() {
        return payAccount;
    }


    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }


    public String getDrawAccount() {
        return drawAccount;
    }


    public void setDrawAccount(String drawAccount) {
        this.drawAccount = drawAccount;
    }


    public Date getPayTime() {
        return payTime;
    }


    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }


    public String getWeixinNum() {
        return weixinNum;
    }

    public void setWeixinNum(String weixinNum) {
        this.weixinNum = weixinNum;
    }

    public String getAlipayNum() {
        return alipayNum;
    }

    public void setAlipayNum(String alipayNum) {
        this.alipayNum = alipayNum;
    }


    public CmsUser getPayUser() {
        return payUser;
    }


    public void setPayUser(CmsUser payUser) {
        this.payUser = payUser;
    }


    public CmsUser getDrawUser() {
        return drawUser;
    }


    public void setDrawUser(CmsUser drawUser) {
        this.drawUser = drawUser;
    }


}
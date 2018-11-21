package com.bfly.cms.entity.assist;

import com.bfly.common.util.DateUtils;
import com.bfly.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户账户提现申请
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 13:49
 */
@Entity
@Table(name = "jc_account_draw")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsAccountDraw implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Short CHECKING = 0;
    public static final Short CHECKED_SUCC = 1;
    public static final Short CHECKED_FAIL = 2;
    public static final Short DRAW_SUCC = 3;

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (getApplyAmount() != null) {
            json.put("applyAmount", getApplyAmount());
        } else {
            json.put("applyAmount", "");
        }
        if (getApplyStatus() != null) {
            json.put("applyStatus", getApplyStatus());
        } else {
            json.put("applyStatus", "");
        }
        if (getApplyTime() != null) {
            json.put("applyTime", DateUtils.parseDateToTimeStr(getApplyTime()));
        } else {
            json.put("applyTime", "");
        }
        if (getDrawUser() != null && StringUtils.isNotBlank(getDrawUser().getUsername())) {
            json.put("drawUserName", getDrawUser().getUsername());
        } else {
            json.put("drawUserName", "");
        }
        if (getDrawUser() != null && getDrawUser().getId() != null) {
            json.put("drawUserId", getDrawUser().getId());
        } else {
            json.put("drawUserId", "");
        }
        if (StringUtils.isNotBlank(getApplyAccount())) {
            json.put("applyAccount", getApplyAccount());
        } else {
            json.put("applyAccount", "");
        }
        return json;
    }

    @Id
    @Column(name = "account_draw_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 申请账户（微信号或支付宝账户）
     */
    @Column(name = "apply_account")
    private String applyAccount;

    /**
     * 提现申请金额
     */
    @Column(name = "apply_amount")
    private Double applyAmount;

    /**
     * 处理状态(0申请中 1申请成功待支付 2申请失败 3提现成功)
     */
    @Column(name = "apply_status")
    private Short applyStatus;

    /**
     * 申请时间
     */
    @Column(name = "apply_time")
    private Date applyTime;

    @ManyToOne
    @JoinColumn(name = "draw_user_id")
    private CmsUser drawUser;

    @ManyToOne
    @JoinColumn(name = "account_pay_id")
    private CmsAccountPay accountPay;

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyAccount() {
        return applyAccount;
    }

    public void setApplyAccount(String applyAccount) {
        this.applyAccount = applyAccount;
    }

    public Double getApplyAmount() {
        return applyAmount;
    }


    public void setApplyAmount(Double applyAmount) {
        this.applyAmount = applyAmount;
    }


    public Short getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Short applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Date getApplyTime() {
        return applyTime;
    }


    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public CmsUser getDrawUser() {
        return drawUser;
    }


    public void setDrawUser(CmsUser drawUser) {
        this.drawUser = drawUser;
    }


    public CmsAccountPay getAccountPay() {
        return accountPay;
    }


    public void setAccountPay(CmsAccountPay accountPay) {
        this.accountPay = accountPay;
    }

}
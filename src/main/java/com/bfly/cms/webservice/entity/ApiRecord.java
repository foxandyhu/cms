package com.bfly.cms.webservice.entity;

import com.bfly.common.util.DateUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * API调用记录
 * @author andy_hulibo@163.com
 * @date 2018/11/16 10:01
 */
@Entity
@Table(name = "jc_api_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ApiRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 调用的IP
     */
    @Column(name = "call_ip")
    private String callIp;

    /**
     * 调用时间
     */
    @Column(name = "api_call_time")
    private Date callTime;

    /**
     * 调用的时间戳
     */
    @Column(name = "call_time_stamp")
    private Long callTimeStamp;

    /**
     * 签名数据(不允许重复调用)
     */
    @Column(name = "sign")
    private String sign;

    /**
     * 调用的账户
     */
    @ManyToOne
    @JoinColumn(name = "api_account")
    private ApiAccount apiAccount;

    /**
     * 接口
     */
    @ManyToOne
    @JoinColumn(name = "api_info_id")
    private ApiInfo apiInfo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCallIp() {
        return callIp;
    }

    public void setCallIp(String callIp) {
        this.callIp = callIp;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public Long getCallTimeStamp() {
        return callTimeStamp;
    }


    public void setCallTimeStamp(Long callTimeStamp) {
        this.callTimeStamp = callTimeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public ApiAccount getApiAccount() {
        return apiAccount;
    }

    public void setApiAccount(ApiAccount apiAccount) {
        this.apiAccount = apiAccount;
    }


    public ApiInfo getApiInfo() {
        return apiInfo;
    }


    public void setApiInfo(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (getCallTime() != null) {
            json.put("callTime", DateUtils.parseDateToDateStr(getCallTime()));
        } else {
            json.put("callTime", "");
        }
        if (StringUtils.isNotBlank(getCallIp())) {
            json.put("callIp", getCallIp());
        } else {
            json.put("callIp", "");
        }
        if (getApiAccount() != null && StringUtils.isNotBlank(getApiAccount().getAppId())) {
            json.put("apiAppId", getApiAccount().getAppId());
        } else {
            json.put("apiAppId", "");
        }
        if (getApiInfo() != null && StringUtils.isNotBlank(getApiInfo().getName())) {
            json.put("apiInfoName", getApiInfo().getName());
        } else {
            json.put("apiInfoName", "");
        }
        return json;
    }
}
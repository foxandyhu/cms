package com.bfly.cms.webservice.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * API调用记录
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 10:01
 */
@Entity
@Table(name = "ws_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class WsRecord implements Serializable {

    private static final long serialVersionUID = 210109523684537983L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
    private long callTimeStamp;

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
    private WsAccount apiAccount;

    /**
     * 接口
     */
    @ManyToOne
    @JoinColumn(name = "api_info_id")
    private WsInfo apiInfo;


    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getCallTimeStamp() {
        return callTimeStamp;
    }

    public void setCallTimeStamp(long callTimeStamp) {
        this.callTimeStamp = callTimeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public WsAccount getApiAccount() {
        return apiAccount;
    }

    public void setApiAccount(WsAccount apiAccount) {
        this.apiAccount = apiAccount;
    }

    public WsInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(WsInfo apiInfo) {
        this.apiInfo = apiInfo;
    }
}
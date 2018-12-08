package com.bfly.cms.webservice.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * API应用账户
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 9:43
 */
@Entity
@Table(name = "ws_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class WsAccount implements Serializable {

    private static final long serialVersionUID = 4617746891309698300L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "app_id")
    private String appId;

    @Column(name = "app_key")
    private String appKey;

    /**
     * AES加解密密钥
     */
    @Column(name = "aes_key")
    private String aesKey;

    @Column(name = "iv_key")
    private String ivKey;

    /**
     * 是否禁用
     */
    @Column(name = "disabled")
    private boolean disabled;

    /**
     * 是否默认管理后台API账户
     */
    @Column(name = "is_admin")
    private boolean admin;

    /**
     * 是否限制单设备同时登陆
     */
    @Column(name = "limit_single_device")
    private boolean limitSingleDevice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apiAccount")
    private Set<WsRecord> callRecords;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getIvKey() {
        return ivKey;
    }

    public void setIvKey(String ivKey) {
        this.ivKey = ivKey;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isLimitSingleDevice() {
        return limitSingleDevice;
    }

    public void setLimitSingleDevice(boolean limitSingleDevice) {
        this.limitSingleDevice = limitSingleDevice;
    }

    public Set<WsRecord> getCallRecords() {
        return callRecords;
    }

    public void setCallRecords(Set<WsRecord> callRecords) {
        this.callRecords = callRecords;
    }
}
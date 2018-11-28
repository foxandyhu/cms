package com.bfly.cms.webservice.entity;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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
@Table(name = "jc_api_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ApiAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    private Boolean disabled;

    /**
     * 是否默认管理后台API账户
     */
    @Column(name = "is_admin")
    private Boolean admin;

    /**
     * 是否限制单设备同时登陆
     */
    @Column(name = "limit_single_device")
    private Boolean limitSingleDevice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apiAccount")
    private Set<ApiRecord> callRecords;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
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


    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getLimitSingleDevice() {
        return limitSingleDevice;
    }

    public void setLimitSingleDevice(Boolean limitSingleDevice) {
        this.limitSingleDevice = limitSingleDevice;
    }


    public Set<ApiRecord> getCallRecords() {
        return callRecords;
    }


    public void setCallRecords(Set<ApiRecord> callRecords) {
        this.callRecords = callRecords;
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getAppId())) {
            json.put("setAppId", getAppId());
        } else {
            json.put("setAppId", "");
        }
        if (StringUtils.isNotBlank(getAppKey())) {
            json.put("appKey", getAppKey());
        } else {
            json.put("appKey", "");
        }
        if (StringUtils.isNotBlank(getAesKey())) {
            json.put("aesKey", getAesKey());
        } else {
            json.put("aesKey", "");
        }
        if (StringUtils.isNotBlank(getIvKey())) {
            json.put("ivKey", getIvKey());
        } else {
            json.put("ivKey", "");
        }
        if (getDisabled() != null) {
            json.put("disabled", getDisabled());
        } else {
            json.put("disabled", "");
        }
        if (getAdmin() != null) {
            json.put("admin", getAdmin());
        } else {
            json.put("admin", "");
        }
        if (getLimitSingleDevice() != null) {
            json.put("limitSingleDevice", getLimitSingleDevice());
        } else {
            json.put("limitSingleDevice", "");
        }
        return json;
    }

    public void init() {
        if (getDisabled() == null) {
            setDisabled(false);
        }
        if (getAdmin() == null) {
            setAdmin(false);
        }
        if (getLimitSingleDevice() == null) {
            setLimitSingleDevice(false);
        }
    }
}
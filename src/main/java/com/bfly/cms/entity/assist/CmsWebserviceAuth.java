package com.bfly.cms.entity.assist;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * webservices认证
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:52
 */
@Entity
@Table(name = "jc_webservice_auth")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsWebserviceAuth implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getUsername())) {
            json.put("username", getUsername());
        } else {
            json.put("username", "");
        }
        if (StringUtils.isNotBlank(getPassword())) {
            json.put("password", getPassword());
        } else {
            json.put("password", "");
        }
        if (StringUtils.isNotBlank(getSystem())) {
            json.put("system", getSystem());
        } else {
            json.put("system", "");
        }
        json.put("enable", getEnable());
        return json;
    }

    public boolean getEnable() {
        return isEnable();
    }

    @Id
    @Column(name = "auth_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 系统
     */
    @Column(name = "system")
    private String system;

    /**
     * 是否启用
     */
    @Column(name = "is_enable")
    private boolean enable;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "auth")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private Set<CmsWebserviceCallRecord> webserviceCallRecords;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getSystem() {
        return system;
    }


    public void setSystem(String system) {
        this.system = system;
    }


    public boolean isEnable() {
        return enable;
    }


    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    public Set<CmsWebserviceCallRecord> getWebserviceCallRecords() {
        return webserviceCallRecords;
    }


    public void setWebserviceCallRecords(Set<CmsWebserviceCallRecord> webserviceCallRecords) {
        this.webserviceCallRecords = webserviceCallRecords;
    }


}
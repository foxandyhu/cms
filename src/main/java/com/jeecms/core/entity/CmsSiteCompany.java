package com.jeecms.core.entity;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 公司信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 17:45
 */
@Entity
@Table(name = "jc_site_company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsSiteCompany implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "site_id",unique = true,nullable = false)
    private Integer id;

    /**
     * 公司名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 公司规模
     */
    @Column(name = "scale")
    private String scale;

    /**
     * 公司性质
     */
    @Column(name = "nature")
    private String nature;

    /**
     * 公司行业
     */
    @Column(name = "industry")
    private String industry;

    /**
     * 联系方式
     */
    @Column(name = "contact")
    private String contact;

    /**
     * 公司简介
     */
    @Column(name = "description")
    private String description;

    /**
     * 公司地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 经度
     */
    @Column(name = "longitude")
    private Float longitude;

    /**
     * 纬度
     */
    @Column(name = "latitude")
    private Float latitude;

    @OneToOne
    @MapsId
    @JoinColumn(name = "site_id")
    private CmsSite site;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getIndustry() {
        return industry;
    }


    public void setIndustry(String industry) {
        this.industry = industry;
    }


    public String getContact() {
        return contact;
    }


    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public Float getLongitude() {
        return longitude;
    }


    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }


    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getScale())) {
            json.put("scale", getScale());
        } else {
            json.put("scale", "");
        }
        if (StringUtils.isNotBlank(getNature())) {
            json.put("nature", getNature());
        } else {
            json.put("nature", "");
        }
        if (StringUtils.isNotBlank(getIndustry())) {
            json.put("industry", getIndustry());
        } else {
            json.put("industry", "");
        }
        if (StringUtils.isNotBlank(getContact())) {
            json.put("contact", getContact());
        } else {
            json.put("contact", "");
        }
        if (StringUtils.isNotBlank(getDescription())) {
            json.put("description", getDescription());
        } else {
            json.put("description", "");
        }
        if (StringUtils.isNotBlank(getAddress())) {
            json.put("address", getAddress());
        } else {
            json.put("address", "");
        }
        if (getLongitude() != null) {
            json.put("longitude", getLongitude());
        } else {
            json.put("longitude", "");
        }
        if (getLatitude() != null) {
            json.put("latitude", getLatitude());
        } else {
            json.put("latitude", "");
        }
        return json;
    }
}
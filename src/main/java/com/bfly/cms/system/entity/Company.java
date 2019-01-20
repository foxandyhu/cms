package com.bfly.cms.system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 公司信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 17:45
 */
@Entity
@Table(name = "d_company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class Company implements Serializable {

    private static final long serialVersionUID = 8689011643274322584L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 公司名称
     */
    @Column(name = "name")
    @NotBlank(message = "公司名称不能为空!")
    private String name;

    /**
     * 公司规模
     */
    @Column(name = "scale")
    @NotBlank(message = "公司规模不能为空!")
    private String scale;

    /**
     * 公司性质
     */
    @Column(name = "nature")
    @NotBlank(message = "公司性质不能为空!")
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
    private float longitude;

    /**
     * 纬度
     */
    @Column(name = "latitude")
    private float latitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
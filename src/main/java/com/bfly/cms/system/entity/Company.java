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
public class Company implements Serializable {

    private static final long serialVersionUID = 8689011643274322584L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
     * 联系电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 企业邮箱
     * @author andy_hulibo@163.com
     * @date 2019/7/22 10:57
     */
    @Column(name = "email")
    private String email;

    /**
     * 公司简介
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 公司地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 微信
     * @author andy_hulibo@163.com
     * @date 2019/7/22 11:05
     */
    @Column(name = "weixin")
    private String weixin;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }
}
package com.bfly.cms.ad.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * CMS广告版位
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:47
 */
@Entity
@Table(name = "ad_space")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class AdvertisingSpace implements Serializable {

    private static final long serialVersionUID = 2304185058497496977L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 名称
     */
    @Column(name = "name")
    @NotBlank(message = "广告位名称不能为空!")
    private String name;

    /**
     * 描述
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 是否启用
     */
    @Column(name = "is_enabled")
    private boolean enabled;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
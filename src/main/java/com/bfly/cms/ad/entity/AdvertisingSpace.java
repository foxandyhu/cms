package com.bfly.cms.ad.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS广告版位
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:47
 */
@Entity
@Table(name = "ad_space")
public class AdvertisingSpace implements Serializable {

    private static final long serialVersionUID = 2304185058497496977L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 名称
     */
    @Column(name = "ad_name")
    private String name;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
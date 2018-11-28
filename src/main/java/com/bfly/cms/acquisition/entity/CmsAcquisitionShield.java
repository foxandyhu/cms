package com.bfly.cms.acquisition.entity;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 采集内容屏蔽
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:38
 */
@Entity
@Table(name = "jc_acquisition_shield")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsAcquisitionShield implements Serializable {

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getShieldStart())) {
            json.put("shieldStart", getShieldStart());
        } else {
            json.put("shieldStart", "");
        }
        if (StringUtils.isNotBlank(getShieldEnd())) {
            json.put("shieldEnd", getShieldEnd());
        } else {
            json.put("shieldEnd", "");
        }
        return json;
    }

    @Id
    @Column(name = "shield_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 屏蔽开始
     */
    @Column(name = "shield_start")
    private String shieldStart;

    /**
     * 屏蔽结束
     */
    @Column(name = "shield_end")
    private String shieldEnd;

    @ManyToOne
    @JoinColumn(name = "acquisition_id")
    private CmsAcquisition acquisition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShieldStart() {
        return shieldStart;
    }

    public void setShieldStart(String shieldStart) {
        this.shieldStart = shieldStart;
    }

    public String getShieldEnd() {
        return shieldEnd;
    }

    public void setShieldEnd(String shieldEnd) {
        this.shieldEnd = shieldEnd;
    }

    public CmsAcquisition getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(CmsAcquisition acquisition) {
        this.acquisition = acquisition;
    }

}

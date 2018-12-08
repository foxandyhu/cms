package com.bfly.cms.acquisition.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 采集内容屏蔽
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:38
 */
@Entity
@Table(name = "acquisition_shield")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class AcquisitionShield implements Serializable {

    private static final long serialVersionUID = 6207380527371143405L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
    private Acquisition acquisition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Acquisition getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(Acquisition acquisition) {
        this.acquisition = acquisition;
    }
}

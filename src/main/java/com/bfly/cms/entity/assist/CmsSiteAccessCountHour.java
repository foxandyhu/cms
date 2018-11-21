package com.bfly.cms.entity.assist;

import com.bfly.core.entity.CmsSite;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "jc_site_access_count_hour")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsSiteAccessCountHour implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "access_count_hour_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 小时PV
     */
    @Column(name = "hour_pv")
    private Long hourPv;

    /**
     * 小时IP
     */
    @Column(name = "hour_ip")
    private Long hourIp;

    /**
     * 小时访客数
     */
    @Column(name = "hour_uv")
    private Long hourUv;

    @Column(name = "access_date")
    private Date accessDate;

    @Column(name = "access_hour")
    private Integer accessHour;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Long getHourPv() {
        return hourPv;
    }

    public void setHourPv(Long hourPv) {
        this.hourPv = hourPv;
    }

    public Long getHourIp() {
        return hourIp;
    }

    public void setHourIp(Long hourIp) {
        this.hourIp = hourIp;
    }


    public Long getHourUv() {
        return hourUv;
    }

    public void setHourUv(Long hourUv) {
        this.hourUv = hourUv;
    }

    public Date getAccessDate() {
        return accessDate;
    }


    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public Integer getAccessHour() {
        return accessHour;
    }

    public void setAccessHour(Integer accessHour) {
        this.accessHour = accessHour;
    }

    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }
}
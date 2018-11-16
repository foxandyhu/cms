package com.jeecms.cms.entity.main;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS栏目访问量计数类
 * @author andy_hulibo@163.com
 * @date 2018/11/16 11:01
 */
@Entity
@Table(name = "jc_channel_count")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ChannelCount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "channel_id")
    private Integer id;

    /**
     * 总访问数
     */
    @Column(name = "views")
    private Integer views;

    /**
     * 月访问数
     */
    @Column(name = "views_month")
    private Integer viewsMonth;

    /**
     * 周访问数
     */
    @Column(name = "views_week")
    private Integer viewsWeek;

    /**
     * 日访问数
     */
    @Column(name = "views_day")
    private Integer viewsDay;

    /**
     * 内容今日发布数
     */
    @Column(name = "content_count_day")
    private Integer contentDay;

    /**
     * 内容本月发布数
     */
    @Column(name = "content_count_month")
    private Integer contentMonth;

    /**
     * 内容本周发布数
     */
    @Column(name = "content_count_week")
    private Integer contentWeek;

    /**
     * 内容今年发布数
     */
    @Column(name = "content_count_year")
    private Integer contentYear;

    /**
     * 内容发布数
     */
    @Column(name = "content_count_total")
    private Integer contentTotal;

    @Id
    @OneToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getViewsMonth() {
        return viewsMonth;
    }

    public void setViewsMonth(Integer viewsMonth) {
        this.viewsMonth = viewsMonth;
    }

    public Integer getViewsWeek() {
        return viewsWeek;
    }


    public void setViewsWeek(Integer viewsWeek) {
        this.viewsWeek = viewsWeek;
    }

    public Integer getViewsDay() {
        return viewsDay;
    }

    public void setViewsDay(Integer viewsDay) {
        this.viewsDay = viewsDay;
    }

    public Integer getContentDay() {
        return contentDay;
    }

    public void setContentDay(Integer contentDay) {
        this.contentDay = contentDay;
    }

    public Integer getContentMonth() {
        return contentMonth;
    }

    public void setContentMonth(Integer contentMonth) {
        this.contentMonth = contentMonth;
    }

    public Integer getContentWeek() {
        return contentWeek;
    }

    public void setContentWeek(Integer contentWeek) {
        this.contentWeek = contentWeek;
    }

    public Integer getContentYear() {
        return contentYear;
    }

    public void setContentYear(Integer contentYear) {
        this.contentYear = contentYear;
    }

    public Integer getContentTotal() {
        return contentTotal;
    }

    public void setContentTotal(Integer contentTotal) {
        this.contentTotal = contentTotal;
    }

    public Channel getChannel() {
        return channel;
    }


    public void setChannel(Channel channel) {
        this.channel = channel;
    }


    public void init() {
        if (getViews() == null) {
            setViews(0);
        }
        if (getViewsMonth() == null) {
            setViewsMonth(0);
        }
        if (getViewsWeek() == null) {
            setViewsWeek(0);
        }
        if (getViewsDay() == null) {
            setViewsDay(0);
        }
        if (getContentTotal() == null) {
            setContentTotal(0);
        }
        if (getContentDay() == null) {
            setContentDay(0);
        }
        if (getContentMonth() == null) {
            setContentMonth(0);
        }
        if (getContentWeek() == null) {
            setContentWeek(0);
        }
        if (getContentYear() == null) {
            setContentYear(0);
        }
    }
}
package com.bfly.cms.channel.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS栏目访问量计数类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 11:01
 */
@Entity
@Table(name = "channel_count")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ChannelCount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "channel_id", unique = true, nullable = false)
    private int id;

    /**
     * 总访问数
     */
    @Column(name = "views")
    private int views;

    /**
     * 月访问数
     */
    @Column(name = "views_month")
    private int viewsMonth;

    /**
     * 周访问数
     */
    @Column(name = "views_week")
    private int viewsWeek;

    /**
     * 日访问数
     */
    @Column(name = "views_day")
    private int viewsDay;

    /**
     * 内容今日发布数
     */
    @Column(name = "content_count_day")
    private int contentDay;

    /**
     * 内容本月发布数
     */
    @Column(name = "content_count_month")
    private int contentMonth;

    /**
     * 内容本周发布数
     */
    @Column(name = "content_count_week")
    private int contentWeek;

    /**
     * 内容今年发布数
     */
    @Column(name = "content_count_year")
    private int contentYear;

    /**
     * 内容发布数
     */
    @Column(name = "content_count_total")
    private int contentTotal;

    /**
     * 所属栏目
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "channel_id")
    private Channel channel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getViewsMonth() {
        return viewsMonth;
    }

    public void setViewsMonth(int viewsMonth) {
        this.viewsMonth = viewsMonth;
    }

    public int getViewsWeek() {
        return viewsWeek;
    }

    public void setViewsWeek(int viewsWeek) {
        this.viewsWeek = viewsWeek;
    }

    public int getViewsDay() {
        return viewsDay;
    }

    public void setViewsDay(int viewsDay) {
        this.viewsDay = viewsDay;
    }

    public int getContentDay() {
        return contentDay;
    }

    public void setContentDay(int contentDay) {
        this.contentDay = contentDay;
    }

    public int getContentMonth() {
        return contentMonth;
    }

    public void setContentMonth(int contentMonth) {
        this.contentMonth = contentMonth;
    }

    public int getContentWeek() {
        return contentWeek;
    }

    public void setContentWeek(int contentWeek) {
        this.contentWeek = contentWeek;
    }

    public int getContentYear() {
        return contentYear;
    }

    public void setContentYear(int contentYear) {
        this.contentYear = contentYear;
    }

    public int getContentTotal() {
        return contentTotal;
    }

    public void setContentTotal(int contentTotal) {
        this.contentTotal = contentTotal;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
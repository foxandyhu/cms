package com.bfly.cms.content.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS内容计数
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 13:13
 */
@Entity
@Table(name = "content_count")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ContentCount implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum ContentViewCount {
        viewTotal, viewMonth, viewWeek, viewDay
    }

    @Id
    @Column(name = "content_id", unique = true, nullable = false)
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
     * 总评论数
     */
    @Column(name = "comments")
    private int comments;

    /**
     * 月评论数
     */
    @Column(name = "comments_month")
    private int commentsMonth;

    /**
     * 周评论数
     */
    @Column(name = "comments_week")
    private int commentsWeek;

    /**
     * 日评论数
     */
    @Column(name = "comments_day")
    private int commentsDay;

    /**
     * 总下载数
     */
    @Column(name = "downloads")
    private int downloads;

    /**
     * 月下载数
     */
    @Column(name = "downloads_month")
    private int downloadsMonth;

    /**
     * 周下载数
     */
    @Column(name = "downloads_week")
    private int downloadsWeek;

    /**
     * 日下载数
     */
    @Column(name = "downloads_day")
    private int downloadsDay;

    /**
     * 总顶数
     */
    @Column(name = "ups")
    private int ups;

    /**
     * 月顶数
     */
    @Column(name = "ups_month")
    private int upsMonth;

    /**
     * 周顶数
     */
    @Column(name = "ups_week")
    private int upsWeek;

    /**
     * 日顶数
     */
    @Column(name = "ups_day")
    private int upsDay;

    /**
     * 总踩数
     */
    @Column(name = "downs")
    private int downs;

    /**
     * 所属文章
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "content_id")
    private Content content;

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

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getCommentsMonth() {
        return commentsMonth;
    }

    public void setCommentsMonth(int commentsMonth) {
        this.commentsMonth = commentsMonth;
    }

    public int getCommentsWeek() {
        return commentsWeek;
    }

    public void setCommentsWeek(int commentsWeek) {
        this.commentsWeek = commentsWeek;
    }

    public int getCommentsDay() {
        return commentsDay;
    }

    public void setCommentsDay(int commentsDay) {
        this.commentsDay = commentsDay;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public int getDownloadsMonth() {
        return downloadsMonth;
    }

    public void setDownloadsMonth(int downloadsMonth) {
        this.downloadsMonth = downloadsMonth;
    }

    public int getDownloadsWeek() {
        return downloadsWeek;
    }

    public void setDownloadsWeek(int downloadsWeek) {
        this.downloadsWeek = downloadsWeek;
    }

    public int getDownloadsDay() {
        return downloadsDay;
    }

    public void setDownloadsDay(int downloadsDay) {
        this.downloadsDay = downloadsDay;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getUpsMonth() {
        return upsMonth;
    }

    public void setUpsMonth(int upsMonth) {
        this.upsMonth = upsMonth;
    }

    public int getUpsWeek() {
        return upsWeek;
    }

    public void setUpsWeek(int upsWeek) {
        this.upsWeek = upsWeek;
    }

    public int getUpsDay() {
        return upsDay;
    }

    public void setUpsDay(int upsDay) {
        this.upsDay = upsDay;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
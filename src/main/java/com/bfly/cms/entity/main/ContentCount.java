package com.bfly.cms.entity.main;

import javax.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * CMS内容计数
 * @author andy_hulibo@163.com
 * @date 2018/11/16 13:13
 */
@Entity
@Table(name = "jc_content_count")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region = "beanCache")
public class ContentCount implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum ContentViewCount {
        viewTotal, viewMonth, viewWeek, viewDay
    }

    public void init() {
        short zero = 0;
        if (getDowns() == null) {
            setDowns(0);
        }
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
        if (getComments() == null) {
            setComments(0);
        }
        if (getCommentsMonth() == null) {
            setCommentsMonth(0);
        }
        if (getCommentsWeek() == null) {
            setCommentsWeek(zero);
        }
        if (getCommentsDay() == null) {
            setCommentsDay(zero);
        }
        if (getDownloads() == null) {
            setDownloads(0);
        }
        if (getDownloadsMonth() == null) {
            setDownloadsMonth(0);
        }
        if (getDownloadsWeek() == null) {
            setDownloadsWeek(zero);
        }
        if (getDownloadsDay() == null) {
            setDownloadsDay(zero);
        }
        if (getUps() == null) {
            setUps(0);
        }
        if (getUpsMonth() == null) {
            setUpsMonth(0);
        }
        if (getUpsWeek() == null) {
            setUpsWeek(zero);
        }
        if (getUpsDay() == null) {
            setUpsDay(zero);
        }
    }

    @Id
    @Column(name = "content_id",unique = true,nullable = false)
    private Integer id;

    /**
     *总访问数
     */
    @Column(name = "views")
    private Integer views;

    /**
     *月访问数
     */
    @Column(name = "views_month")
    private Integer viewsMonth;

    /**
     *周访问数
     */
    @Column(name = "views_week")
    private Integer viewsWeek;

    /**
     *日访问数
     */
    @Column(name = "views_day")
    private Integer viewsDay;

    /**
     *总评论数
     */
    @Column(name = "comments")
    private Integer comments;

    /**
     *月评论数
     */
    @Column(name = "comments_month")
    private Integer commentsMonth;

    /**
     *周评论数
     */
    @Column(name = "comments_week")
    private Short commentsWeek;

    /**
     *日评论数
     */
    @Column(name = "comments_day")
    private Short commentsDay;

    /**
     *总下载数
     */
    @Column(name = "downloads")
    private Integer downloads;

    /**
     *月下载数
     */
    @Column(name = "downloads_month")
    private Integer downloadsMonth;

    /**
     *周下载数
     */
    @Column(name = "downloads_week")
    private Short downloadsWeek;

    /**
     *日下载数
     */
    @Column(name = "downloads_day")
    private Short downloadsDay;

    /**
     *总顶数
     */
    @Column(name = "ups")
    private Integer ups;

    /**
     *月顶数
     */
    @Column(name = "ups_month")
    private Integer upsMonth;

    /**
     *周顶数
     */
    @Column(name = "ups_week")
    private Short upsWeek;

    /**
     *日顶数
     */
    @Column(name = "ups_day")
    private Short upsDay;

    /**
     *总踩数
     */
    @Column(name = "downs")
    private Integer downs;

    @OneToOne
    @MapsId
    @JoinColumn(name = "content_id")
    private Content content;


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


    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getCommentsMonth() {
        return commentsMonth;
    }


    public void setCommentsMonth(Integer commentsMonth) {
        this.commentsMonth = commentsMonth;
    }


    public Short getCommentsWeek() {
        return commentsWeek;
    }

    public void setCommentsWeek(Short commentsWeek) {
        this.commentsWeek = commentsWeek;
    }


    public Short getCommentsDay() {
        return commentsDay;
    }


    public void setCommentsDay(Short commentsDay) {
        this.commentsDay = commentsDay;
    }


    public Integer getDownloads() {
        return downloads;
    }


    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }


    public Integer getDownloadsMonth() {
        return downloadsMonth;
    }

    public void setDownloadsMonth(Integer downloadsMonth) {
        this.downloadsMonth = downloadsMonth;
    }


    public Short getDownloadsWeek() {
        return downloadsWeek;
    }

    public void setDownloadsWeek(Short downloadsWeek) {
        this.downloadsWeek = downloadsWeek;
    }


    public Short getDownloadsDay() {
        return downloadsDay;
    }


    public void setDownloadsDay(Short downloadsDay) {
        this.downloadsDay = downloadsDay;
    }

    public Integer getUps() {
        return ups;
    }

    public void setUps(Integer ups) {
        this.ups = ups;
    }


    public Integer getUpsMonth() {
        return upsMonth;
    }


    public void setUpsMonth(Integer upsMonth) {
        this.upsMonth = upsMonth;
    }


    public Short getUpsWeek() {
        return upsWeek;
    }

    public void setUpsWeek(Short upsWeek) {
        this.upsWeek = upsWeek;
    }


    public Short getUpsDay() {
        return upsDay;
    }

    public void setUpsDay(Short upsDay) {
        this.upsDay = upsDay;
    }


    public Integer getDowns() {
        return downs;
    }


    public void setDowns(Integer downs) {
        this.downs = downs;
    }

    public Content getContent() {
        return content;
    }


    public void setContent(Content content) {
        this.content = content;
    }


}
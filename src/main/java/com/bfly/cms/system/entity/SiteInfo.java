package com.bfly.cms.system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * CMS站点基本配置信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 17:13
 */
@Entity
@Table(name = "site_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class SiteInfo implements Serializable {

    private static final long serialVersionUID = 277028697339118850L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 域名
     */
    @Column(name = "web_site")
    @NotBlank(message = "域名不能为空!")
    private String webSite;

    /**
     * 网站名称
     */
    @Column(name = "site_name")
    @NotBlank(message = "网站名称不能为空!")
    private String name;

    /**
     * 简短名称
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * 站点关键字
     */
    @Column(name = "keywords")
    private String keywords;

    /**
     * 站点描述
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 是否开启站点
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 18:12
     */
    @Column(name = "is_open_site")
    private boolean openSite;

    /**
     * 是否打开流量统计开关
     */
    @Column(name = "is_open_flow")
    private boolean openFlow;

    /**
     * 是否开启评论
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 18:13
     */
    @Column(name = "is_open_comment")
    private boolean openComment;

    /**
     * 评论是否登录
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 18:15
     */
    @Column(name = "is_need_login_comment")
    private boolean needLoginComment;

    /**
     * 是否开启留言
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 18:14
     */
    @Column(name = "is_open_guest_book")
    private boolean openGuestBook;

    /**
     * 留言是否登录
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 18:14
     */
    @Column(name = "is_need_login_guest_book")
    private boolean needLoginGuestBook;

    /**
     * 评论日最高限制数
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 18:16
     */
    @Column(name = "max_comment_limit")
    @Min(value = 0, message = "日评论数最小为0!")
    private int maxCommentLimit;

    /**
     * 留言日最高限制数
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 18:17
     */
    @Column(name = "max_guest_book_limit")
    @Min(value = 0, message = "日留言数最小为0!")
    private int maxGuestBookLimit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isOpenSite() {
        return openSite;
    }

    public void setOpenSite(boolean openSite) {
        this.openSite = openSite;
    }

    public boolean isOpenFlow() {
        return openFlow;
    }

    public void setOpenFlow(boolean openFlow) {
        this.openFlow = openFlow;
    }

    public boolean isOpenComment() {
        return openComment;
    }

    public void setOpenComment(boolean openComment) {
        this.openComment = openComment;
    }

    public boolean isNeedLoginComment() {
        return needLoginComment;
    }

    public void setNeedLoginComment(boolean needLoginComment) {
        this.needLoginComment = needLoginComment;
    }

    public boolean isOpenGuestBook() {
        return openGuestBook;
    }

    public void setOpenGuestBook(boolean openGuestBook) {
        this.openGuestBook = openGuestBook;
    }

    public boolean isNeedLoginGuestBook() {
        return needLoginGuestBook;
    }

    public void setNeedLoginGuestBook(boolean needLoginGuestBook) {
        this.needLoginGuestBook = needLoginGuestBook;
    }

    public int getMaxCommentLimit() {
        return maxCommentLimit;
    }

    public void setMaxCommentLimit(int maxCommentLimit) {
        this.maxCommentLimit = maxCommentLimit;
    }

    public int getMaxGuestBookLimit() {
        return maxGuestBookLimit;
    }

    public void setMaxGuestBookLimit(int maxGuestBookLimit) {
        this.maxGuestBookLimit = maxGuestBookLimit;
    }
}
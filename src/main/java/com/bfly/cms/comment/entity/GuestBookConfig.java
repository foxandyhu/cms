package com.bfly.cms.comment.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * 留言配置
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/20 9:55
 */
@Entity
@Table(name = "guestbook_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class GuestBookConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 是否开启留言
     */
    @Column(name = "is_open_guestbook")
    private boolean openGuestBook;

    /**
     * 是否登录才能留言
     */
    @Column(name = "is_allow_login_guestbook")
    private boolean allowLoginGuestBook;

    /**
     * 日留言数限制 0则不限制
     */
    @Column(name = "day_guestbook_limit")
    @Min(value = 0, message = "日留言数不正确!")
    private int dayGuestBookLimit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAllowLoginGuestBook() {
        return allowLoginGuestBook;
    }

    public void setAllowLoginGuestBook(boolean allowLoginGuestBook) {
        this.allowLoginGuestBook = allowLoginGuestBook;
    }

    public int getDayGuestBookLimit() {
        return dayGuestBookLimit;
    }

    public void setDayGuestBookLimit(int dayGuestBookLimit) {
        this.dayGuestBookLimit = dayGuestBookLimit;
    }

    public boolean isOpenGuestBook() {
        return openGuestBook;
    }

    public void setOpenGuestBook(boolean openGuestBook) {
        this.openGuestBook = openGuestBook;
    }
}
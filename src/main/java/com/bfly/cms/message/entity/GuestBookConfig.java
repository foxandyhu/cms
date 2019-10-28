package com.bfly.cms.message.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 留言配置
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/15 13:26
 */
@Entity
@Table(name = "guestbook_config")
public class GuestBookConfig implements Serializable {


    private static final long serialVersionUID = -7962667048689066559L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    public int getMaxGuestBookLimit() {
        return maxGuestBookLimit;
    }

    public void setMaxGuestBookLimit(int maxGuestBookLimit) {
        this.maxGuestBookLimit = maxGuestBookLimit;
    }
}
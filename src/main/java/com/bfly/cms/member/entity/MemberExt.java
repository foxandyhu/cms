package com.bfly.cms.member.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 会员扩展信息类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 22:26
 */
@Entity
@Table(name = "member_ext")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class MemberExt implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    /**
     * 真实姓名
     */
    @Column(name = "realname")
    private String realname;

    /**
     * 性别
     */
    @Column(name = "gender")
    private boolean gender;

    /**
     * 出生日期
     */
    @Column(name = "birthday")
    private Date birthday;

    /**
     * 个人介绍
     */
    @Column(name = "intro")
    private String intro;

    /**
     * 来自
     */
    @Column(name = "comefrom")
    private String comefrom;

    /**
     * QQ
     */
    @Column(name = "qq")
    private String qq;

    /**
     * MSN
     */
    @Column(name = "msn")
    private String msn;

    /**
     * 电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 手机
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 用户头像
     */
    @Column(name = "user_img")
    private String userImg;

    /**
     * 用户个性签名
     */
    @Column(name = "user_signature")
    private String userSignature;

    /**
     * 今日留言数
     */
    @Column(name = "today_guestbook_total")
    private int todayGuestbookTotal;

    /**
     * 今日评论数
     */
    @Column(name = "today_comment_total")
    private int todayCommentTotal;

    @OneToOne
    @MapsId
    @JoinColumn(name = "member_id")
    private Member user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getComefrom() {
        return comefrom;
    }

    public void setComefrom(String comefrom) {
        this.comefrom = comefrom;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    public int getTodayGuestbookTotal() {
        return todayGuestbookTotal;
    }

    public void setTodayGuestbookTotal(int todayGuestbookTotal) {
        this.todayGuestbookTotal = todayGuestbookTotal;
    }

    public int getTodayCommentTotal() {
        return todayCommentTotal;
    }

    public void setTodayCommentTotal(int todayCommentTotal) {
        this.todayCommentTotal = todayCommentTotal;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }
}
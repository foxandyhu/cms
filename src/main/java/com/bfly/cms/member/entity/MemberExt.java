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
@Cache(region = "beanCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class MemberExt implements Serializable {

    private static final long serialVersionUID = -5389448150141085150L;
    @Id
    @Column(name = "member_id")
    private int id;

    @MapsId
    @OneToOne
    private Member member;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 性别
     */
    @Column(name = "is_girl")
    private boolean girl;

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
    @Column(name = "come_from")
    private String comeFrom;

    /**
     * QQ
     */
    @Column(name = "qq")
    private String qq;

    /**
     * 微信号
     */
    @Column(name = "wei_xin")
    private String weiXin;

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
     * 电子邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 用户头像
     */
    @Column(name = "face")
    private String face;

    /**
     * 用户个性签名
     */
    @Column(name = "signature")
    private String signature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public boolean isGirl() {
        return girl;
    }

    public void setGirl(boolean girl) {
        this.girl = girl;
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

    public String getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeiXin() {
        return weiXin;
    }

    public void setWeiXin(String weiXin) {
        this.weiXin = weiXin;
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

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
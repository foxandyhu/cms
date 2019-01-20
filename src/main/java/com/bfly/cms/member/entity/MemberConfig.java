package com.bfly.cms.member.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * 会员设置
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 16:43
 */
@Entity
@Table(name = "member_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class MemberConfig {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 是否开启会员功能
     */
    @Column(name = "is_open_member")
    private boolean openMember;

    /**
     * 是否开启注册功能
     */
    @Column(name = "is_open_registe")
    private boolean openRegiste;

    /**
     * 注册后是否需要审核
     */
    @Column(name = "is_need_check_registed")
    private boolean needCheckRegisted;

    /**
     * 找回密码标题
     */
    @Column(name = "retrieve_pwd_title")
    @NotBlank(message = "找回密码标题不能为空!")
    @Max(value = 50, message = "找回密码标题最大50个字符!")
    private String retrievePwdTitle;

    /**
     * 找回密码内容
     */
    @Column(name = "retrieve_pwd_text")
    @NotBlank(message = "找回密码内容不能为空!")
    @Max(value = 500, message = "找回密码内容最大500个字符!")
    private String retrievePwdText;

    /**
     * 会员注册标题
     */
    @Column(name = "registe_title")
    @NotBlank(message = "会员注册标题不能为空!")
    @Max(value = 50, message = "会员注册标题最大50个字符!")
    private String registeTitle;

    /**
     * 会员注册内容
     */
    @Column(name = "registe_text")
    @NotBlank(message = "会员注册内容不能为空!")
    @Max(value = 500, message = "会员注册内容最大500个字符!")
    private String registeText;

    /**
     * 是否开启QQ登录
     */
    @Column(name = "is_open_qq")
    private boolean openQq;

    /**
     * QQ APPID
     */
    @Column(name = "qq_app_id")
    private String qqAppId;

    /**
     * QQ APP Key
     */
    @Column(name = "qq_app_key")
    private String qqAppKey;

    /**
     * 是否开启微信登录
     */
    @Column(name = "is_open_weixin")
    private boolean openWeiXin;

    /**
     * weiXin APPID
     */
    @Column(name = "weixin_app_id")
    private String weiXinAppId;

    /**
     * weiXin APP Key
     */
    @Column(name = "weixin_app_key")
    private String weiXinAppKey;

    /**
     * 是否开启新浪微博登录
     */
    @Column(name = "is_open_sina")
    private boolean openSina;

    /**
     * 新浪微博 APPID
     */
    @Column(name = "sina_app_id")
    private String sinaAppId;

    /**
     * 新浪微博 APP Key
     */
    @Column(name = "sina_app_key")
    private String sinaAppKey;

    public boolean isOpenQq() {
        return openQq;
    }

    public void setOpenQq(boolean openQq) {
        this.openQq = openQq;
    }

    public String getQqAppId() {
        return qqAppId;
    }

    public void setQqAppId(String qqAppId) {
        this.qqAppId = qqAppId;
    }

    public String getQqAppKey() {
        return qqAppKey;
    }

    public void setQqAppKey(String qqAppKey) {
        this.qqAppKey = qqAppKey;
    }

    public boolean isOpenWeiXin() {
        return openWeiXin;
    }

    public void setOpenWeiXin(boolean openWeiXin) {
        this.openWeiXin = openWeiXin;
    }

    public String getWeiXinAppId() {
        return weiXinAppId;
    }

    public void setWeiXinAppId(String weiXinAppId) {
        this.weiXinAppId = weiXinAppId;
    }

    public String getWeiXinAppKey() {
        return weiXinAppKey;
    }

    public void setWeiXinAppKey(String weiXinAppKey) {
        this.weiXinAppKey = weiXinAppKey;
    }

    public boolean isOpenSina() {
        return openSina;
    }

    public void setOpenSina(boolean openSina) {
        this.openSina = openSina;
    }

    public String getSinaAppId() {
        return sinaAppId;
    }

    public void setSinaAppId(String sinaAppId) {
        this.sinaAppId = sinaAppId;
    }

    public String getSinaAppKey() {
        return sinaAppKey;
    }

    public void setSinaAppKey(String sinaAppKey) {
        this.sinaAppKey = sinaAppKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRetrievePwdTitle() {
        return retrievePwdTitle;
    }

    public void setRetrievePwdTitle(String retrievePwdTitle) {
        this.retrievePwdTitle = retrievePwdTitle;
    }

    public String getRetrievePwdText() {
        return retrievePwdText;
    }

    public void setRetrievePwdText(String retrievePwdText) {
        this.retrievePwdText = retrievePwdText;
    }

    public String getRegisteTitle() {
        return registeTitle;
    }

    public void setRegisteTitle(String registeTitle) {
        this.registeTitle = registeTitle;
    }

    public String getRegisteText() {
        return registeText;
    }

    public void setRegisteText(String registeText) {
        this.registeText = registeText;
    }

    public boolean isNeedCheckRegisted() {
        return needCheckRegisted;
    }

    public void setNeedCheckRegisted(boolean needCheckRegisted) {
        this.needCheckRegisted = needCheckRegisted;
    }

    public boolean isOpenMember() {
        return openMember;
    }

    public void setOpenMember(boolean openMember) {
        this.openMember = openMember;
    }

    public boolean isOpenRegiste() {
        return openRegiste;
    }

    public void setOpenRegiste(boolean openRegiste) {
        this.openRegiste = openRegiste;
    }

}

package com.bfly.cms.system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
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
public class SiteConfig implements Serializable {

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
     * 指定首页PC模板
     */
    @Column(name = "tpl_pc")
    private String tplPc;

    /**
     * 手机首页模板
     */
    @Column(name = "tpl_mobile")
    private String tplMobile;

    /**
     * 备案号
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/4 15:25
     */
    @Column(name = "filling_code")
    private String filingCode;

    /**
     * 版权说明
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/4 15:25
     */
    @Column(name = "copy_right")
    private String copyRight;

    /**
     * 版权所有
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/4 15:27
     */
    @Column(name = "copy_right_owner")
    private String copyRightOwner;

    /**
     * 主办单位
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/4 15:26
     */
    @Column(name = "organizer")
    private String organizer;

    public String getFilingCode() {
        return filingCode;
    }

    public void setFilingCode(String filingCode) {
        this.filingCode = filingCode;
    }

    public String getCopyRight() {
        return copyRight;
    }

    public void setCopyRight(String copyRight) {
        this.copyRight = copyRight;
    }

    public String getCopyRightOwner() {
        return copyRightOwner;
    }

    public void setCopyRightOwner(String copyRightOwner) {
        this.copyRightOwner = copyRightOwner;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getTplPc() {
        return tplPc;
    }

    public void setTplPc(String tplPc) {
        this.tplPc = tplPc;
    }

    public String getTplMobile() {
        return tplMobile;
    }

    public void setTplMobile(String tplMobile) {
        this.tplMobile = tplMobile;
    }

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
}
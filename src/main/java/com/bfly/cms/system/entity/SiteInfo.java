package com.bfly.cms.system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

/**
 * CMS站点基本信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 17:13
 */
@Entity
@Table(name = "site_info")
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
    @Column(name = "domain")
    @NotBlank(message = "域名不能为空!")
    private String domain;

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
     * 是否静态化首页
     */
    @Column(name = "is_static_index")
    private boolean staticIndex;


    /**
     * 模板方案
     */
    @Column(name = "tpl_solution")
    private String tplSolution;

    /**
     * 手机站模板方案
     */
    @Column(name = "tpl_mobile_solution")
    private String tplMobileSolution;

    /**
     * 终审级别
     */
    @Column(name = "final_step")
    private int finalStep;

    /**
     * 审核后(1:不能修改删除;2:修改后退回;3:修改后不变)
     */
    @Column(name = "after_check")
    private int afterCheck;

    /**
     * 域名重定向
     */
    @Column(name = "domain_redirect")
    private String domainRedirect;

    /**
     * 站点关键字
     */
    @Column(name = "keywords")
    private String keywords;

    /**
     * 站点描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 站点其他属性
     */
    @ElementCollection
    @CollectionTable(name = "site_attr", joinColumns = @JoinColumn(name = "site_id"))
    @MapKeyColumn(name = "attr_name")
    @Column(name = "attr_value")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private Map<String, String> attr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
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

    public boolean isStaticIndex() {
        return staticIndex;
    }

    public void setStaticIndex(boolean staticIndex) {
        this.staticIndex = staticIndex;
    }

    public String getTplSolution() {
        return tplSolution;
    }

    public void setTplSolution(String tplSolution) {
        this.tplSolution = tplSolution;
    }

    public String getTplMobileSolution() {
        return tplMobileSolution;
    }

    public void setTplMobileSolution(String tplMobileSolution) {
        this.tplMobileSolution = tplMobileSolution;
    }

    public int getFinalStep() {
        return finalStep;
    }

    public void setFinalStep(int finalStep) {
        this.finalStep = finalStep;
    }

    public int getAfterCheck() {
        return afterCheck;
    }

    public void setAfterCheck(int afterCheck) {
        this.afterCheck = afterCheck;
    }

    public String getDomainRedirect() {
        return domainRedirect;
    }

    public void setDomainRedirect(String domainRedirect) {
        this.domainRedirect = domainRedirect;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
}
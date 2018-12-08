package com.bfly.cms.siteconfig.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * oss云存储配置类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 16:51
 */
@Entity
@Table(name = "d_oss")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class Oss implements Serializable {

    private static final long serialVersionUID = -5585874006953613249L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "app_id")
    private String ossAppId;

    @Column(name = "secret_id")
    private String secretId;

    /**
     * secret key
     */
    @Column(name = "app_key")
    private String appKey;

    /**
     * bucket名
     */
    @Column(name = "bucket_name")
    private String bucketName;

    /**
     * 地区码
     */
    @Column(name = "bucket_area")
    private String bucketArea;

    @Column(name = "end_point")
    private String endPoint;

    /**
     * 访问域名
     */
    @Column(name = "access_domain")
    private String accessDomain;

    /**
     * 名称
     */
    @Column(name = "oss_name")
    private String name;

    /**
     * 存储类型(1腾讯云cos  2阿里云oss  3七牛云)
     */
    @Column(name = "oss_type")
    private int ossType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOssAppId() {
        return ossAppId;
    }

    public void setOssAppId(String ossAppId) {
        this.ossAppId = ossAppId;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketArea() {
        return bucketArea;
    }

    public void setBucketArea(String bucketArea) {
        this.bucketArea = bucketArea;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getAccessDomain() {
        return accessDomain;
    }

    public void setAccessDomain(String accessDomain) {
        this.accessDomain = accessDomain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOssType() {
        return ossType;
    }

    public void setOssType(int ossType) {
        this.ossType = ossType;
    }
}
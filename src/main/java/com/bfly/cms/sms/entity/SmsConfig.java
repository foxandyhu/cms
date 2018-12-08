package com.bfly.cms.sms.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * SMS短信服务配置类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 17:56
 */
@Entity
@Table(name = "sms_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class SmsConfig implements Serializable {

    /**
     * 阿里固定参数 短信API产品名称
     */
    public static final String product = "Dysmsapi";

    /**
     * 短信API产品域名
     */
    public static final String domain = "dysmsapi.aliyuncs.com";

    /**
     * 初始化ascClient,暂时不支持多region
     */
    public static final String regionId = "cn-hangzhou";
    public static final String endpointName = "cn-hangzhou";

    private static final long serialVersionUID = 900667721702404931L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 消息服务名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 发送账号安全认证的Access Key ID/appId 注：不同接口参数类型可能不同
     */
    @Column(name = "access_key_id")
    private String accessKeyId;

    /**
     * 发送账号安全认证的Secret Access Key/appKey
     */
    @Column(name = "access_key_secret")
    private String accessKeySecret;

    /**
     * 本次发送使用的模板Code 注：不同接口参数类型可能不同
     */
    @Column(name = "template_code")
    private String templateCode;

    /**
     * 模板参数 注：不同接口参数类型可能不同
     */
    @Column(name = "template_param")
    private String templateParam;

    /**
     * 每次发送时间间隔
     */
    @Column(name = "interval_time")
    private int intervalTime;

    /**
     * 间隔时间单位 0秒 1分 2时
     */
    @Column(name = "interval_unit")
    private int intervalUnit;

    /**
     * 二维码有效时间
     */
    @Column(name = "effective_time")
    private int effectiveTime;

    /**
     * 有效时间单位 0秒 1分 2时
     */
    @Column(name = "effective_unit")
    private int effectiveUnit;

    /**
     * 短信签名(阿里)
     */
    @Column(name = "sign_name")
    private String signName;

    /**
     * 上行短信扩展码(阿里)
     */
    @Column(name = "sms_up_extend_code")
    private String smsUpExtendCode;

    /**
     * 提供给业务方扩展字段(阿里)
     */
    @Column(name = "out_id")
    private String outId;

    /**
     * 区域码(腾讯)
     */
    @Column(name = "nation_code")
    private String nationCode;

    /**
     * SMS服务域名，可根据环境选择具体域名(百度)
     */
    @Column(name = "end_point")
    private String endPoint;

    /**
     * 发送使用签名的调用ID(百度)
     */
    @Column(name = "invoke_id")
    private String invokeId;

    /**
     * SMS服务平台1阿里 2腾讯 3百度
     */
    @Column(name = "sms_source")
    private int source;

    /**
     * 是否为验证码模板
     */
    @Column(name = "is_code")
    private boolean isCode;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 验证码位数
     */
    @Column(name = "random_num")
    private int randomNum;

    /**
     * 短信记录
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "smsConfig")
    private Set<SmsRecord> smsRecords;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getIntervalUnit() {
        return intervalUnit;
    }

    public void setIntervalUnit(int intervalUnit) {
        this.intervalUnit = intervalUnit;
    }

    public int getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(int effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public int getEffectiveUnit() {
        return effectiveUnit;
    }

    public void setEffectiveUnit(int effectiveUnit) {
        this.effectiveUnit = effectiveUnit;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getSmsUpExtendCode() {
        return smsUpExtendCode;
    }

    public void setSmsUpExtendCode(String smsUpExtendCode) {
        this.smsUpExtendCode = smsUpExtendCode;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(String invokeId) {
        this.invokeId = invokeId;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public boolean isCode() {
        return isCode;
    }

    public void setCode(boolean code) {
        isCode = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(int randomNum) {
        this.randomNum = randomNum;
    }

    public Set<SmsRecord> getSmsRecords() {
        return smsRecords;
    }

    public void setSmsRecords(Set<SmsRecord> smsRecords) {
        this.smsRecords = smsRecords;
    }
}

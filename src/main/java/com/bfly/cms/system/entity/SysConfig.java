package com.bfly.cms.system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * CMS系统配置类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 15:30
 */
@Entity
@Table(name = "sys_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class SysConfig implements Serializable {
    public static final String REWARD_FIX_PREFIX = "reward_fix_";
    private static final long serialVersionUID = 7497189296788079788L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 图片不存在时默认图片
     */
    @Column(name = "def_img")
    private String defImg;

    /**
     * 下载防盗链md5混淆码
     */
    @Column(name = "download_code")
    private String downloadCode;

    /**
     * 下载有效时间（小时）
     */
    @Column(name = "download_time")
    private int downloadTime;

    /**
     * 验证类型：0:无  1：邮件验证  2：SMS验证
     */
    @Column(name = "validate_type")
    private int validateType;

    /**
     * 流量统计清除时间
     */
    @Column(name = "flow_clear_time")
    private Date flowClearTime;

    /**
     * 栏目发布内容计数器清除时间
     */
    @Column(name = "channel_count_clear_time")
    private Date channelCountClearTime;

    /**
     * 短信验证 每日验证次数限制
     */
    @Column(name = "day_count")
    private int dayCount;

    /**
     * 配置了的短信运营商
     */
    @Column(name = "sms_config")
    private int smsCfgId;

    /**
     * 水印配置
     */
    @Embedded
    MarkConfig m_markConfig;

    /**
     * 邮件配置
     */
    @Embedded
    EmailConfig m_emailConfig;

    /**
     * 系统配置其他属性
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @CollectionTable(name = "sys_config_attr", joinColumns = @JoinColumn(name = "config_id"))
    @MapKeyColumn(name = "attr_name")
    @Column(name = "attr_value")
    private Map<String, String> attr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDefImg() {
        return defImg;
    }

    public void setDefImg(String defImg) {
        this.defImg = defImg;
    }

    public String getDownloadCode() {
        return downloadCode;
    }

    public void setDownloadCode(String downloadCode) {
        this.downloadCode = downloadCode;
    }

    public int getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(int downloadTime) {
        this.downloadTime = downloadTime;
    }

    public int getValidateType() {
        return validateType;
    }

    public void setValidateType(int validateType) {
        this.validateType = validateType;
    }

    public Date getFlowClearTime() {
        return flowClearTime;
    }

    public void setFlowClearTime(Date flowClearTime) {
        this.flowClearTime = flowClearTime;
    }

    public Date getChannelCountClearTime() {
        return channelCountClearTime;
    }

    public void setChannelCountClearTime(Date channelCountClearTime) {
        this.channelCountClearTime = channelCountClearTime;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    public int getSmsCfgId() {
        return smsCfgId;
    }

    public void setSmsCfgId(int smsCfgId) {
        this.smsCfgId = smsCfgId;
    }

    public MarkConfig getM_markConfig() {
        return m_markConfig;
    }

    public void setM_markConfig(MarkConfig m_markConfig) {
        this.m_markConfig = m_markConfig;
    }

    public EmailConfig getM_emailConfig() {
        return m_emailConfig;
    }

    public void setM_emailConfig(EmailConfig m_emailConfig) {
        this.m_emailConfig = m_emailConfig;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
}
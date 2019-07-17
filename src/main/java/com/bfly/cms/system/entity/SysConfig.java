package com.bfly.cms.system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
     * 是否打开流量统计开关
     */
    @Column(name = "is_open_flow")
    private boolean openFlow;

    /**
     * 流量统计清除时间
     */
    @Column(name = "flow_clear_time")
    private Date flowClearTime;

    /**
     * 验证类型：0:无  1：邮件验证  2：SMS验证
     */
    @Column(name = "validate_type")
    private int validateType;

    /**
     * 短信验证 每日验证次数限制
     */
    @Column(name = "msg_verify_day_count")
    private int msgVerifyDayCount;

    /**
     * 短信运营商
     */
    @JoinColumn(name = "sms_config_id")
    @ManyToOne
    private SmsProvider sms;

    /**
     * 邮件服务商
     */
    @JoinColumn(name = "email_config_id")
    @ManyToOne
    private EmailProvider sysEmail;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOpenFlow() {
        return openFlow;
    }

    public void setOpenFlow(boolean openFlow) {
        this.openFlow = openFlow;
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

    public Date getFlowClearTime() {
        return flowClearTime;
    }

    public void setFlowClearTime(Date flowClearTime) {
        this.flowClearTime = flowClearTime;
    }

    public int getValidateType() {
        return validateType;
    }

    public void setValidateType(int validateType) {
        this.validateType = validateType;
    }

    public int getMsgVerifyDayCount() {
        return msgVerifyDayCount;
    }

    public void setMsgVerifyDayCount(int msgVerifyDayCount) {
        this.msgVerifyDayCount = msgVerifyDayCount;
    }

    public SmsProvider getSms() {
        return sms;
    }

    public void setSms(SmsProvider sms) {
        this.sms = sms;
    }

    public EmailProvider getSysEmail() {
        return sysEmail;
    }

    public void setSysEmail(EmailProvider emailProvider) {
        this.sysEmail = emailProvider;
    }

}
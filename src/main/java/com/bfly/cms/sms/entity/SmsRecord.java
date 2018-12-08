package com.bfly.cms.sms.entity;

import com.bfly.cms.member.entity.Member;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * SMS短信服务记录类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 20:43
 */
@Entity
@Table(name = "sms_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class SmsRecord implements Serializable {

    private static final long serialVersionUID = 7701008436692805236L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 电话号码
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 发送时间
     */
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 发送内容
     */
    @Column(name = "send_content")
    private String sendContent;

    /**
     * 验证类型  0：未知 1 : 注册验证 2 : 找回密码验证
     */
    @Column(name = "validate_type")
    private int validateType;

    /**
     * 所属短信配置
     */
    @ManyToOne
    @JoinColumn(name = "sms_id")
    private SmsConfig smsConfig;

    /**
     * 短信接收者
     */
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public int getValidateType() {
        return validateType;
    }

    public void setValidateType(int validateType) {
        this.validateType = validateType;
    }

    public SmsConfig getSmsConfig() {
        return smsConfig;
    }

    public void setSmsConfig(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }
}

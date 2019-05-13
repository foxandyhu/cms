package com.bfly.cms.system.entity;

import com.bfly.cms.member.entity.Member;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * SMS短信服务记录类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 20:43
 */
@Entity
@Table(name = "sys_sms_record")
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
    @NotBlank(message = "电话号码不能为空!")
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
     * 发送次数
     */
    @Column(name = "send_count")
    private int sendCount;

    /**
     * 短信状态 0 草稿 1 待发送 2 发送成功 3 发送失败
     *
     * @see com.bfly.core.enums.SmsStatus
     */
    @Column(name = "status")
    private int status;

    /**
     * 短信类型  0：未知 1 : 注册验证 2 : 找回密码验证
     *
     * @see com.bfly.core.enums.SmsType
     */
    @Column(name = "type")
    private int type;

    /**
     * 所属短信商
     */
    @ManyToOne
    @JoinColumn(name = "sms_id")
    private Sms sms;

    /**
     * 短信接收者
     */
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

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

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Sms getSms() {
        return sms;
    }

    public void setSms(Sms sms) {
        this.sms = sms;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}

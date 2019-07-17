package com.bfly.cms.system.entity;

import com.bfly.core.enums.SmsStatus;
import com.bfly.core.enums.SmsType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
    @Column(name = "content")
    private String content;

    /**
     * 发送次数
     */
    @Column(name = "count")
    private int count;

    /**
     * 短信状态 1 待发送 2 发送成功 3 发送失败
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
    @JoinColumn(name = "provider_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private SmsProvider provider;

    /**
     * 短信接收者
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 状态名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 12:48
     */
    public String getStatusName() {
        SmsStatus status = SmsStatus.getStatus(getStatus());
        return status == null ? "" : status.getName();
    }

    /**
     * 类型名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 12:48
     */
    public String getTypeName() {
        SmsType type = SmsType.getType(getType());
        return type == null ? "" : type.getName();
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public SmsProvider getProvider() {
        return provider;
    }

    public void setProvider(SmsProvider provider) {
        this.provider = provider;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

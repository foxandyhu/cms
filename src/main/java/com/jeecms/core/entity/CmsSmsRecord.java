package com.jeecms.core.entity;

import com.jeecms.common.util.DateUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

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
@Table(name = "jc_sms_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region = "beanCache")
public class CmsSmsRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    private Integer validateType;

    @ManyToOne
    @JoinColumn(name = "sms_id")
    private CmsSms sms;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CmsUser user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValidateType() {
        return validateType;
    }

    public void setValidateType(Integer validateType) {
        this.validateType = validateType;
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

    public CmsSms getSms() {
        return sms;
    }

    public void setSms(CmsSms sms) {
        this.sms = sms;
    }

    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }

    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }

    public JSONObject convertToJson() {

        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (getSendTime() != null) {
            json.put("sendTime", DateUtils.parseDateToTimeStr(getSendTime()));
        } else {
            json.put("sendTime", "");
        }

        if (getPhone() != null) {
            json.put("phone", getPhone());
        } else {
            json.put("phone", "");
        }
        if (getSms() != null) {
            CmsSms cmsSms = getSms();
            Byte source = cmsSms.getSource();
            if (source == 1) {//SMS服务平台1阿里 2腾讯 3百度',
                json.put("smsName", "阿里云");
            } else if (source == 2) {
                json.put("smsName", "腾讯云");
            } else if (source == 3) {
                json.put("smsName", "百度云");
            } else {
                json.put("smsName", "未知");
            }
        } else {
            json.put("smsName", "未知");
        }
        if (getUser() != null) {
            CmsUser cmsUser = getUser();
            json.put("username", cmsUser.getUsername());
        } else {
            json.put("username", "");
        }
        if (getSite() != null) {
            CmsSite cmsSite = getSite();
            json.put("siteName", cmsSite.getName());
        } else {
            json.put("siteName", "");
        }
        if (getValidateType() != null) {
            Integer integer = getValidateType();
            if (integer == 1) {
                json.put("validateType", "注册验证");
            } else if (integer == 2) {
                json.put("validateType", "找回密码");
            } else {
                json.put("validateType", "未知验证");
            }
        } else {
            json.put("validateType", "未知验证");
        }
        return json;
    }
}

package com.jeecms.cms.entity.assist;

import com.jeecms.common.util.DateUtils;
import com.jeecms.common.util.StrUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 站内信
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:34
 */
@Entity
@Table(name = "jc_message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "msg_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标题
     */
    @Column(name = "msg_title")
    private String msgTitle;

    /**
     * 站内信息内容
     */
    @Column(name = "msg_content")
    private String msgContent;

    /**
     * 发送时间
     */
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 消息状态0未读，1已读
     */
    @Column(name = "msg_status")
    private Boolean msgStatus;

    /**
     * 消息信箱 0收件箱 1发件箱 2草稿箱 3垃圾箱
     */
    @Column(name = "msg_box")
    private Integer msgBox;

    /**
     * 接收人
     */
    @ManyToOne
    @JoinColumn(name = "msg_receiver_user")
    private CmsUser msgReceiverUser;

    /**
     * 发送人
     */
    @ManyToOne
    @JoinColumn(name = "msg_send_user")
    private CmsUser msgSendUser;

    /**
     * 所属站点
     */
    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;

    @OneToMany(mappedBy = "message")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private Set<CmsReceiverMessage> receiverMsgs;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }


    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getMsgStatus() {
        return msgStatus;
    }


    public void setMsgStatus(Boolean msgStatus) {
        this.msgStatus = msgStatus;
    }


    public Integer getMsgBox() {
        return msgBox;
    }


    public void setMsgBox(Integer msgBox) {
        this.msgBox = msgBox;
    }

    public CmsUser getMsgReceiverUser() {
        return msgReceiverUser;
    }


    public void setMsgReceiverUser(CmsUser msgReceiverUser) {
        this.msgReceiverUser = msgReceiverUser;
    }


    public CmsUser getMsgSendUser() {
        return msgSendUser;
    }

    public void setMsgSendUser(CmsUser msgSendUser) {
        this.msgSendUser = msgSendUser;
    }

    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }

    public Set<CmsReceiverMessage> getReceiverMsgs() {
        return receiverMsgs;
    }

    public void setReceiverMsgs(
            Set<CmsReceiverMessage> receiverMsgs) {
        this.receiverMsgs = receiverMsgs;
    }

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getMsgTitle())) {
            json.put("msgTitle", getMsgTitle());
        } else {
            json.put("msgTitle", "");
        }
        if (StringUtils.isNotBlank(getMsgContent())) {
            json.put("msgContent", getMsgContent());
        } else {
            json.put("msgContent", "");
        }
        if (getSendTime() != null) {
            json.put("sendTime", DateUtils.parseDateToTimeStr(getSendTime()));
        } else {
            json.put("sendTime", "");
        }
        if (getMsgStatus() != null) {
            json.put("msgStatus", getMsgStatus());
        } else {
            json.put("msgStatus", "");
        }
        if (getMsgBox() != null) {
            json.put("msgBox", getMsgBox());
        } else {
            json.put("msgBox", "");
        }
        if (getMsgReceiverUser() != null && StringUtils.isNotBlank(getMsgReceiverUser().getUsername())) {
            json.put("msgReceiverUserName", getMsgReceiverUser().getUsername());
        } else {
            json.put("msgReceiverUserName", "");
        }
        if (getMsgReceiverUser() != null && getMsgReceiverUser().getId() != null) {
            json.put("msgReceiverId", getMsgReceiverUser().getId());
        } else {
            json.put("msgReceiverId", "");
        }
        if (getMsgSendUser() != null && StringUtils.isNotBlank(getMsgSendUser().getUsername())) {
            json.put("msgSendUserUserName", getMsgSendUser().getUsername());
        } else {
            json.put("msgSendUserUserName", "");
        }
        if (getMsgSendUser() != null && getMsgSendUser().getId() != null) {
            json.put("msgSendUserId", getMsgSendUser().getId());
        } else {
            json.put("msgSendUserId", "");
        }
        return json;
    }


    public String getTitleHtml() {
        return StrUtils.txt2htm(getMsgTitle());
    }

    public String getContentHtml() {
        return StrUtils.txt2htm(getMsgContent());
    }
}
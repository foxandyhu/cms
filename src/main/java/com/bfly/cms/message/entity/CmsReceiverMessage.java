package com.bfly.cms.message.entity;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.common.util.DateUtils;
import com.bfly.common.util.StrUtils;
import com.bfly.cms.user.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 站内信收信表
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:46
 */
@Entity
@Table(name = "jc_receiver_message")
public class CmsReceiverMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    public CmsReceiverMessage() {
    }

    public CmsReceiverMessage(Integer id, CmsUser msgReceiverUser, CmsUser msgSendUser, CmsSite site, String msgTitle, String msgContent, Date sendTime, boolean msgStatus, Integer msgBox) {
        this.id = id;
        this.msgReceiverUser = msgReceiverUser;
        this.msgSendUser = msgSendUser;
        this.site = site;
        this.msgTitle = msgTitle;
        this.msgContent = msgContent;
        this.sendTime = sendTime;
        this.msgStatus = msgStatus;
        this.msgBox = msgBox;
    }

    public CmsReceiverMessage(CmsMessage message) {
        this(message.getId(), message.getMsgReceiverUser(), message
                        .getMsgSendUser(), message.getSite(), message.getMsgTitle(),
                message.getMsgContent(), message.getSendTime(), message
                        .getMsgStatus(), message.getMsgBox());
    }

    @Id
    @Column(name = "msg_re_id")
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
    private boolean msgStatus;

    /**
     * 消息信箱 0收件箱 1发件箱 2草稿箱 3垃圾箱
     */
    @Column(name = "msg_box")
    private Integer msgBox;


    @ManyToOne
    @JoinColumn(name = "msg_receiver_user")
    private CmsUser msgReceiverUser;

    @ManyToOne
    @JoinColumn(name = "msg_send_user")
    private CmsUser msgSendUser;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;

    @ManyToOne
    @JoinColumn(name = "msg_id")
    private CmsMessage message;

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


    public boolean isMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(boolean msgStatus) {
        this.msgStatus = msgStatus;
    }

    public Integer getMsgBox() {
        return msgBox;
    }


    public void setMsgBox(Integer msgBox) {
        this.msgBox = msgBox;
    }

    public CmsMessage getMessage() {
        return message;
    }


    public void setMessage(CmsMessage message) {
        this.message = message;
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
        json.put("msgStatus", isMsgStatus());
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
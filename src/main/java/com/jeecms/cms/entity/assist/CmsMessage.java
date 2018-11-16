package com.jeecms.cms.entity.assist;

import com.jeecms.common.util.DateUtils;
import com.jeecms.common.util.StrUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


public class CmsMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    // primary key
    private Integer id;

    // fields
    private String msgTitle;
    private String msgContent;
    private Date sendTime;
    private Boolean msgStatus;
    private Integer msgBox;

    @ManyToOne
    @JoinColumn(name = "msg_receiver_user")
    private CmsUser msgReceiverUser;

    @ManyToOne
    @JoinColumn(name = "msg_send_user")
    private CmsUser msgSendUser;
    private CmsSite site;

    // collections
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
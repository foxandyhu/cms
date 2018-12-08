package com.bfly.cms.message.entity;

import com.bfly.cms.member.entity.Member;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 站内信
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:34
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class Message implements Serializable {

    private static final long serialVersionUID = -5170317442780762695L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 标题
     */
    @Column(name = "title")
    private String msgTitle;

    /**
     * 站内信息内容
     */
    @Column(name = "content")
    private String msgContent;

    /**
     * 发送时间
     */
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 消息状态0未读，1已读
     */
    @Column(name = "is_read")
    private boolean read;

    /**
     * 消息信箱 0收件箱 1发件箱 2草稿箱 3垃圾箱
     */
    @Column(name = "msg_box")
    private int msgBox;

    /**
     * 接收人
     */
    @ManyToOne
    @JoinColumn(name = "receiver_user")
    private Member msgReceiverUser;

    /**
     * 发送人
     */
    @ManyToOne
    @JoinColumn(name = "send_user")
    private Member msgSendUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int getMsgBox() {
        return msgBox;
    }

    public void setMsgBox(int msgBox) {
        this.msgBox = msgBox;
    }

    public Member getMsgReceiverUser() {
        return msgReceiverUser;
    }

    public void setMsgReceiverUser(Member msgReceiverUser) {
        this.msgReceiverUser = msgReceiverUser;
    }

    public Member getMsgSendUser() {
        return msgSendUser;
    }

    public void setMsgSendUser(Member msgSendUser) {
        this.msgSendUser = msgSendUser;
    }
}
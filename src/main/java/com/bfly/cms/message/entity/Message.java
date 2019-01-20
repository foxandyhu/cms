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
    private String title;

    /**
     * 站内信息内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 发送时间
     */
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 阅读时间
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 15:52
     */
    @Column(name = "read_time")
    private Date readTime;


    /**
     * 消息状态0未读，1已读
     */
    @Column(name = "is_read")
    private boolean read;

    /**
     * 消息信箱 0收件箱 1发件箱 2草稿箱 3垃圾箱
     */
    @Column(name = "msg_box")
    private int box;

    /**
     * 接收人
     */
    @ManyToOne
    @JoinColumn(name = "receiver_user")
    private Member receiver;

    /**
     * 发送人
     */
    @ManyToOne
    @JoinColumn(name = "send_user")
    private Member sender;

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }

    public Member getReceiver() {
        return receiver;
    }

    public void setReceiver(Member receiver) {
        this.receiver = receiver;
    }

    public Member getSender() {
        return sender;
    }

    public void setSender(Member sender) {
        this.sender = sender;
    }
}
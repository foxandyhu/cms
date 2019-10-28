package com.bfly.cms.message.entity;

import com.bfly.core.enums.LetterBox;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 站内信
 * 针对群发的站内信,当前台用户登录的时候去查询
 * 是否存在未读的群发站内信,如果存在则新增一条发送记录
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:34
 */
@Entity
@Table(name = "msg_letter")
public class Letter implements Serializable {

    private static final long serialVersionUID = -5170317442780762695L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
     * 接收人
     */
    @Column(name = "receiver")
    private String receiver;

    /**
     * 发送人
     */
    @Column(name = "send")
    private String sender;

    /**
     * 是否是后台管理员发送的站内信
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/31 15:24
     */
    @Column(name = "is_from_admin")
    private boolean fromAdmin;

    /**
     * 信箱来源
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/31 16:11
     * @see com.bfly.core.enums.LetterBox
     */
    @Column(name = "box")
    private int box;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "txt_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private LetterTxt letterTxt;

    /**
     * 信箱名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/31 17:49
     */
    public String getBoxName() {
        LetterBox box = LetterBox.get(getBox());
        return box == null ? "" : box.getName();
    }

    public LetterTxt getLetterTxt() {
        return letterTxt;
    }

    public void setLetterTxt(LetterTxt letterTxt) {
        this.letterTxt = letterTxt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isFromAdmin() {
        return fromAdmin;
    }

    public void setFromAdmin(boolean fromAdmin) {
        this.fromAdmin = fromAdmin;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }
}
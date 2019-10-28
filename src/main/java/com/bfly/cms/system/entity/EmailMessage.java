package com.bfly.cms.system.entity;

import java.io.Serializable;

/**
 * 邮件消息封装类
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/23 20:14
 */
public class EmailMessage implements Serializable {

    private static final long serialVersionUID = -1366717656489372614L;

    /**
     * 邮件主题
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 20:15
     */
    private String subject;

    /**
     * 邮件内容
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 20:15
     */
    private String content;

    /**
     * 邮件优先级
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 20:15
     */
    private int priority;

    /**
     * 附件地址 例如:C:\\aa.txt
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 20:16
     */
    private String[] attachments;

    /**
     * 发件人名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 20:20
     */
    private String senderName;

    /**
     * 发件人地址
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 20:21
     */
    private String senderAddress;

    /**
     * 接收人地址
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 20:22
     */
    private String[] receiverAddress;

    /**
     * 回复地址
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 20:22
     */
    private String replyAddress;

    /**
     * 抄送邮件地址
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 20:25
     */
    private String[] ccAddress;

    /**
     * 暗送邮件地址
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 20:25
     */
    private String[] bccAddress;

    /**
     * 发送次数
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 21:59
     */
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSubject() {
        return subject;
    }

    public String[] getAttachments() {
        return attachments;
    }

    public void setAttachments(String[] attachments) {
        this.attachments = attachments;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String[] getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String[] receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReplyAddress() {
        return replyAddress;
    }

    public void setReplyAddress(String replyAddress) {
        this.replyAddress = replyAddress;
    }

    public String[] getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(String[] ccAddress) {
        this.ccAddress = ccAddress;
    }

    public String[] getBccAddress() {
        return bccAddress;
    }

    public void setBccAddress(String[] bccAddress) {
        this.bccAddress = bccAddress;
    }
}

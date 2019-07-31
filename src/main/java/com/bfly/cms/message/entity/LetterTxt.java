package com.bfly.cms.message.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 站内信内容
 * 点对点发送,和群发
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:34
 */
@Entity
@Table(name = "msg_letter_txt")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class LetterTxt implements Serializable {

    private static final long serialVersionUID = -5170317442780762695L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 标题
     */
    @Column(name = "title")
    @NotBlank(message = "标题不能为空!")
    @Size(min = 1, max = 100, message = "标题长度是1-100个字符!")
    private String title;

    /**
     * 站内信息内容
     */
    @Column(name = "content")
    @NotBlank(message = "内容不能为空!")
    private String content;

    /**
     * 会员组ID,如果为0 则是点对点发送,反之群发
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/31 15:28
     */
    @Column(name = "group_id")
    private int groupId;


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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
package com.bfly.cms.vote.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 投票记录
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:31
 */
@Entity
@Table(name = "vote_record")
public class VoteRecord implements Serializable {

    private static final long serialVersionUID = 532019969124825612L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 投票时间
     */
    @Column(name = "vote_time")
    private Date time;

    /**
     * 投票IP
     */
    @Column(name = "vote_ip")
    private String ip;

    /**
     * 投票COOKIE
     */
    @Column(name = "vote_cookie")
    private String cookie;

    /**
     * 投票者
     */
    @Column(name = "member_id")
    private Integer memberId;

    /**
     * 所属投票主题ID
     */
    @Column(name = "vote_topic_id")
    private int voteTopicId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getVoteTopicId() {
        return voteTopicId;
    }

    public void setVoteTopicId(int voteTopicId) {
        this.voteTopicId = voteTopicId;
    }
}
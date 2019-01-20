package com.bfly.cms.vote.entity;

import com.bfly.cms.member.entity.Member;
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
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
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
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member user;

    /**
     * 投票主题
     */
    @ManyToOne
    @JoinColumn(name = "votetopic_id")
    private VoteTopic topic;


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

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }

    public VoteTopic getTopic() {
        return topic;
    }

    public void setTopic(VoteTopic topic) {
        this.topic = topic;
    }
}
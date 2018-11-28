package com.bfly.cms.vote.entity;

import com.bfly.cms.user.entity.CmsUser;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * CMS投票记录
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:31
 */
@Entity
@Table(name = "jc_vote_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsVoteRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "voterecored_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *投票时间
     */
    @Column(name = "vote_time")
    private Date time;

    /**
     *投票IP
     */
    @Column(name = "vote_ip")
    private String ip;

    /**
     *投票COOKIE
     */
    @Column(name = "vote_cookie")
    private String cookie;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CmsUser user;

    @ManyToOne
    @JoinColumn(name = "votetopic_id")
    private CmsVoteTopic topic;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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


    public CmsUser getUser() {
        return user;
    }


    public void setUser(CmsUser user) {
        this.user = user;
    }


    public CmsVoteTopic getTopic() {
        return topic;
    }

    public void setTopic(CmsVoteTopic topic) {
        this.topic = topic;
    }


}
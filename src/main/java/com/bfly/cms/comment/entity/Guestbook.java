package com.bfly.cms.comment.entity;

import com.bfly.cms.user.entity.User;
import com.bfly.cms.member.entity.Member;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 留言
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:18
 */
@Entity
@Table(name = "guestbook")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class Guestbook implements Serializable {

    private static final long serialVersionUID = 2355667069373530538L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 留言IP
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 留言时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 回复时间
     */
    @Column(name = "replay_time")
    private Date replayTime;

    /**
     * 是否审核
     */
    @Column(name = "is_checked")
    private boolean checked;

    /**
     * 是否推荐
     */
    @Column(name = "is_recommend")
    private boolean recommend;

    /**
     * 留言扩展信息
     */
    @OneToOne(mappedBy = "guestbook", cascade = CascadeType.REMOVE)
    private GuestbookExt ext;

    /**
     * 留言者ID
     */
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * 回复留言的管理员
     */
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    /**
     * 留言所属类型
     */
    @ManyToOne
    @JoinColumn(name = "type_id")
    private GuestbookType ctg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReplayTime() {
        return replayTime;
    }

    public void setReplayTime(Date replayTime) {
        this.replayTime = replayTime;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public GuestbookExt getExt() {
        return ext;
    }

    public void setExt(GuestbookExt ext) {
        this.ext = ext;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public GuestbookType getCtg() {
        return ctg;
    }

    public void setCtg(GuestbookType ctg) {
        this.ctg = ctg;
    }
}
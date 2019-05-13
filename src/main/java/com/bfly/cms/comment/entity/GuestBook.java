package com.bfly.cms.comment.entity;

import com.bfly.cms.member.entity.Member;
import com.bfly.cms.user.entity.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 留言
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:18
 */
@Entity
@Table(name = "guestbook")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class GuestBook implements Serializable {

    private static final long serialVersionUID = 2355667069373530538L;

    /**
     * 等待审核
     */
    public static final int WAIT_CHECK = 0;
    /**
     * 审核不通过
     */
    public static final int UNPASSED = 1;
    /**
     * 审核通过
     */
    public static final int PASSED = 2;

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
     * 留言/回复时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否审核
     */
    @Column(name = "statuc")
    private int status;

    /**
     * 是否推荐
     */
    @Column(name = "is_recommend")
    private boolean recommend;

    /**
     * 留言扩展信息
     */
    @OneToOne(mappedBy = "guestbook", cascade = CascadeType.ALL)
    @NotNull(message = "留言信息不能为空!")
    private GuestBookExt ext;

    /**
     * 回复/留言会员ID
     */
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * 回复/留言的管理员
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 留言所属类型
     */
    @ManyToOne
    @JoinColumn(name = "type_id")
    @NotNull(message = "留言类型不能为空!")
    private GuestBookType type;

    /**
     * 主评论
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private GuestBook parent;

    /**
     * 引用的回复子评论
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private Set<GuestBook> child;

    public Set<GuestBook> getChild() {
        return child;
    }

    public void setChild(Set<GuestBook> child) {
        this.child = child;
    }

    public GuestBook getParent() {
        return parent;
    }

    public void setParent(GuestBook parent) {
        this.parent = parent;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public GuestBookExt getExt() {
        return ext;
    }

    public void setExt(GuestBookExt ext) {
        this.ext = ext;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GuestBookType getType() {
        return type;
    }

    public void setType(GuestBookType type) {
        this.type = type;
    }
}
package com.bfly.cms.member.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 第三方登录平台账号
 * @author andy_hulibo@163.com
 * @date 2018/11/16 11:46
 */
@Entity
@Table(name = "member_third_account")
public class MemberThirdAccount implements Serializable {

    public static final String QQ_KEY = "openId";
    public static final String SINA_KEY = "uid";
    public static final String QQ_PLAT = "QQ";
    public static final String SINA_PLAT = "SINA";
    public static final String WEIXIN_PLAT = "WEIXIN";
    public static final String QQ_WEBO_KEY = "weboOpenId";
    public static final String QQ_WEBO_PLAT = "QQWEBO";
    public static final String WEIXIN_KEY = "weixinOpenId";
    private static final long serialVersionUID = 5727805921543675051L;

    @Id
    @Column(name = "int")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 第三方账号key
     */
    @Column(name = "account_key")
    private String accountKey;

    /**
     * 关联用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 第三方账号平台(QQ、新浪微博等)
     */
    @Column(name = "source")
    private String source;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }
}
package com.bfly.cms.member.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 会员设置
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 16:43
 */
@Entity
@Table(name = "member_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class MemberConfig implements Serializable {

    private static final long serialVersionUID = 8516488054145033722L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    private LoginConfig loginConfig;

    @Embedded
    private RegistConfig registConfig;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LoginConfig getLoginConfig() {
        return loginConfig;
    }

    public void setLoginConfig(LoginConfig loginConfig) {
        this.loginConfig = loginConfig;
    }

    public RegistConfig getRegistConfig() {
        return registConfig;
    }

    public void setRegistConfig(RegistConfig registConfig) {
        this.registConfig = registConfig;
    }
}
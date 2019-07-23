package com.bfly.cms.system.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 系统防火墙设置
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/22 10:09
 */
@Entity
@Table(name = "sys_firewall")
public class SysFirewall implements Serializable {

    private static final long serialVersionUID = -2293217511771407417L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 是否开启防火墙
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 10:12
     */
    @Column(name = "is_open_firewall")
    private boolean openFirewall;

    /**
     * 拦截的IP
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 10:12
     */
    @Column(name = "deny_ips")
    private String denyIps;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOpenFirewall() {
        return openFirewall;
    }

    public void setOpenFirewall(boolean openFirewall) {
        this.openFirewall = openFirewall;
    }

    public String getDenyIps() {
        return denyIps;
    }

    public void setDenyIps(String denyIps) {
        this.denyIps = denyIps;
    }
}

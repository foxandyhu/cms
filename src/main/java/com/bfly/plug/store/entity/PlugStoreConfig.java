package com.bfly.plug.store.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "jc_plug_store_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class PlugStoreConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="config_id")
    private Integer id;

    /**
     * 接口根地址
     */
    @Column(name = "server_url")
    private String serverUrl;

    /**
     * 应用商店密码
     */
    @Column(name = "password")
    private String password;

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
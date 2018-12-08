package com.bfly.cms.siteconfig.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * FTP配置类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 22:59
 */
@Entity
@Table(name = "d_ftp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class Ftp implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 名称
     */
    @Column(name = "ftp_name")
    private String name;

    /**
     * IP
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 端口
     */
    @Column(name = "port")
    private int port;

    /**
     * 登录名
     */
    @Column(name = "username")
    private String username;

    /**
     * 登陆密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 编码
     */
    @Column(name = "encoding")
    private String encoding;

    /**
     * 超时时间
     */
    @Column(name = "timeout")
    private int timeout;

    /**
     * 路径
     */
    @Column(name = "ftp_path")
    private String path;

    /**
     * 访问URL
     */
    @Column(name = "url")
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
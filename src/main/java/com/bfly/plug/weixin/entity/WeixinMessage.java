package com.bfly.plug.weixin.entity;

import com.bfly.core.entity.CmsSite;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 微信消息
 * @author andy_hulibo@163.com
 * @date 2018/11/15 15:11
 */
@Entity
@Table(name = "jg_weixinmessage")
public class WeixinMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int CONTENT_ONLY = 2;
    public static final int CONTENT_WITH_KEY = 1;
    public static final int CONTENT_WITH_IMG = 0;

    @Id
    @Column(name = "wm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 序号
     */
    @Column(name = "wm_number")
    private String number;
    
    /**
     * 标题
     */
    @Column(name = "wm_title")
    private String title;

    /**
     * 图片地址
     */
    @Column(name = "wm_path")
    private String path;

    /**
     * 链接地址
     */
    @Column(name = "wm_url")
    private String url;

    /**
     * 内容
     */
    @Column(name = "wm_content")
    private String content;

    /**
     * 是否欢迎页
     */
    @Column(name = "is_welcome")
    private Boolean welcome;

    /**
     * 消息类型(0带图文链接 1内容加关键字提示 2仅有内容)
     */
    @Column(name = "wm_msg_type")
    private Integer type;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Boolean isWelcome() {
        return welcome;
    }


    public void setWelcome(Boolean welcome) {
        this.welcome = welcome;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }

    public Boolean getWelcome() {
        return isWelcome();
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getNumber())) {
            json.put("number", getNumber());
        } else {
            json.put("number", "");
        }
        if (StringUtils.isNotBlank(getTitle())) {
            json.put("title", getTitle());
        } else {
            json.put("title", "");
        }
        if (StringUtils.isNotBlank(getPath())) {
            json.put("path", getPath());
        } else {
            json.put("path", "");
        }
        if (StringUtils.isNotBlank(getUrl())) {
            json.put("url", getUrl());
        } else {
            json.put("url", "");
        }
        if (StringUtils.isNotBlank(getContent())) {
            json.put("content", getContent());
        } else {
            json.put("content", "");
        }
        if (getWelcome() != null) {
            json.put("welcome", getWelcome());
        } else {
            json.put("welcome", "");
        }
        if (getType() != null) {
            json.put("type", getType());
        } else {
            json.put("type", "");
        }
        return json;
    }
}
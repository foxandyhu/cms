package com.bfly.cms.entity.main;

import com.bfly.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 第三方登录平台账号
 * @author andy_hulibo@163.com
 * @date 2018/11/16 11:46
 */
@Entity
@Table(name = "jc_third_account")
public class CmsThirdAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String QQ_KEY = "openId";
    public static final String SINA_KEY = "uid";
    public static final String QQ_PLAT = "QQ";
    public static final String SINA_PLAT = "SINA";
    public static final String WEIXIN_PLAT = "WEIXIN";
    public static final String QQ_WEBO_KEY = "weboOpenId";
    public static final String QQ_WEBO_PLAT = "QQWEBO";
    public static final String WEIXIN_KEY = "weixinOpenId";

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @JoinColumn(name = "user_id")
    private CmsUser user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getAccountKey())) {
            json.put("accountKey", getAccountKey());
        } else {
            json.put("accountKey", "");
        }
        if (StringUtils.isNotBlank(getUsername())) {
            json.put("username", getUsername());
        } else {
            json.put("username", "");
        }
        if (StringUtils.isNotBlank(getSource())) {
            json.put("source", getSource());
        } else {
            json.put("source", "");
        }
        if (getUser() != null && StringUtils.isNotBlank(getUser().getRealname())) {
            json.put("userRealName", getUser().getRealname());
        } else {
            json.put("userRealName", "");
        }
        if (getUser() != null && StringUtils.isNotBlank(getUser().getIntro())) {
            json.put("userIntro", getUser().getIntro());
        } else {
            json.put("userIntro", "");
        }
        if (getUser() != null && getUser().getGender() != null) {
            json.put("userGender", getUser().getGender());
        } else {
            json.put("userGender", "");
        }
        return json;
    }
}
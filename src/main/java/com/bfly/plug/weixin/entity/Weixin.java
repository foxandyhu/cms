package com.bfly.plug.weixin.entity;

import com.bfly.core.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "jg_weixin")
public class Weixin implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Integer TENCENT_WX_SUCCESS = 200;
    public static final Integer TENCENT_WX_GET_TOKEN_ERROR = 400;
    public static final Integer TENCENT_WX_UPLOAD_CONTENT_ERROR = 403;
    public static final Integer TENCENT_WX_UPLOAD_CONTENT_LESS = 405;
    public static final Integer TENCENT_WX_SUCCESS_RETURN_CODE = 0;

    /**
     * 站点ID
     */
    @Id
    @Column(name = "site_id",unique = true,nullable = false)
    private Integer id;

    /**
     * 关注欢迎语
     */
    @Column(name = "wx_welcome")
    private String welcome;

    /**
     * 微信二维码
     */
    @Column(name = "wx_pic")
    private String pic;

    @OneToOne
    @MapsId
    @JoinColumn(name = "site_id")
    private CmsSite site;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }


    public String getPic() {
        return pic;
    }


    public void setPic(String pic) {
        this.pic = pic;
    }


    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }


    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getWelcome())) {
            json.put("welcome", getWelcome());
        } else {
            json.put("welcome", "");
        }
        if (StringUtils.isNotBlank(getPic())) {
            json.put("pic", getPic());
        } else {
            json.put("pic", "");
        }
        json.put("wxAppSecret", "");
        json.put("wxToken", "");
        return json;
    }
}
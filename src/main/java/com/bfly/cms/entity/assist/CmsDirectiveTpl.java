package com.bfly.cms.entity.assist;

import com.bfly.core.entity.CmsUser;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * FComment
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:07
 */
@Entity
@Table(name = "jc_directive_tpl")
public class CmsDirectiveTpl implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getDescription())) {
            json.put("description", getDescription());
        } else {
            json.put("description", "");
        }
        if (StringUtils.isNotBlank(getCode())) {
            json.put("code", getCode());
        } else {
            json.put("code", "");
        }
        if (getUser() != null && StringUtils.isNotBlank(getUser().getUsername())) {
            json.put("username", getUser().getUsername());
        } else {
            json.put("username", "");
        }

        return json;
    }

    @Id
    @Column(name = "tpl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标签名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 标签描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 标签代码
     */
    @Column(name = "code")
    private String code;

    /**
     * 标签创建者
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private CmsUser user;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }

}
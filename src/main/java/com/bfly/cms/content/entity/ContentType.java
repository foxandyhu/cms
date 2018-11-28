package com.bfly.cms.content.entity;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 内容类型实体类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 10:23
 */
@Entity
@Table(name = "jc_content_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ContentType implements Serializable {
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
        if (getImgWidth() != null) {
            json.put("imgWidth", getImgWidth());
        } else {
            json.put("imgWidth", "");
        }
        if (getImgHeight() != null) {
            json.put("imgHeight", getImgHeight());
        } else {
            json.put("imgHeight", "");
        }
        if (getHasImage() != null) {
            json.put("hasImage", getHasImage());
        } else {
            json.put("hasImage", "");
        }
        if (getDisabled() != null) {
            json.put("disabled", getDisabled());
        } else {
            json.put("disabled", "");
        }
        return json;
    }

    public void init() {
        if (getHasImage() == null) {
            setHasImage(false);
        }
        if (getDisabled() == null) {
            setDisabled(false);
        }
        if (getImgHeight() == null) {
            setImgHeight(139);
        }
        if (getImgWidth() == null) {
            setImgWidth(139);
        }
    }

    @Id
    @Column(name = "type_id")
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "type_name")
    private String name;

    /**
     * 图片宽
     */
    @Column(name = "img_width")
    private Integer imgWidth;

    /**
     * 图片高
     */
    @Column(name = "img_height")
    private Integer imgHeight;

    /**
     * 是否有图片
     */
    @Column(name = "has_image")
    private Boolean hasImage;

    /**
     * 是否禁用
     */
    @Column(name = "is_disabled")
    private Boolean disabled;

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

    public Integer getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(Integer imgWidth) {
        this.imgWidth = imgWidth;
    }

    public Integer getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(Integer imgHeight) {
        this.imgHeight = imgHeight;
    }

    public Boolean getHasImage() {
        return hasImage;
    }

    public void setHasImage(Boolean hasImage) {
        this.hasImage = hasImage;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
package com.bfly.cms.content.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@Table(name = "content_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ContentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private int id;

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
    private boolean disabled;

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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
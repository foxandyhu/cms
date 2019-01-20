package com.bfly.cms.content.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 名称
     */
    @Column(name = "name")
    @NotBlank(message = "名称不能为空!")
    private String name;

    /**
     * 图片宽
     */
    @Column(name = "img_width")
    @Min(value = 1, message = "图片宽度不正确!")
    private Integer imgWidth;

    /**
     * 图片高
     */
    @Column(name = "img_height")
    @Min(value = 1, message = "图片高度不正确!")
    private Integer imgHeight;

    /**
     * 是否有图片
     */
    @Column(name = "has_image")
    private Boolean hasImage;

    /**
     * 是否禁用
     */
    @Column(name = "is_enabled")
    private boolean enabled;

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
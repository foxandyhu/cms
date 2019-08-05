package com.bfly.cms.content.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 文章内容图片集
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/5 15:56
 */
@Embeddable
public class ContentPicture implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 图片地址
     */
    @Column(name = "img_path")
    private String imgPath;

    /**
     * 图片描述
     */
    @Column(name = "remark")
    private String remark;


    public String getImgPath() {
        return imgPath;
    }


    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
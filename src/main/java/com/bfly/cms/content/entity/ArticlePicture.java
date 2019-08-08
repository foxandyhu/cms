package com.bfly.cms.content.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 文章内容图片集
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/5 15:56
 */
@Entity
@Table(name = "article_picture")
public class ArticlePicture implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
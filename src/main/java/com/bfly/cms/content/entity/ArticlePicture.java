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
public class ArticlePicture implements Serializable, Comparable<ArticlePicture> {
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

    /**
     * 自定义图片集属性 属性名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/12 15:41
     */
    @Column(name = "field")
    private String field;

    /**
     * 排序
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/12 19:09
     */
    @Column(name = "seq")
    private int seq;

    @Override
    public int compareTo(ArticlePicture o) {
        return this.getSeq() - o.getSeq();
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

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
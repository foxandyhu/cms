package com.bfly.cms.content.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 文章内容附件
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/27 11:28
 */
@Entity
@Table(name = "article_attachment")
public class ArticleAttachment implements Serializable {

    private static final long serialVersionUID = 89441064569935173L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 附件路径
     */
    @Column(name = "attachment_path")
    private String path;

    /**
     * 附件名称
     */
    @Column(name = "attachment_name")
    private String name;

    /**
     * 下载次数
     */
    @Column(name = "download_count")
    private int count;

    /**
     * 附件描述
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 自定义附件集属性 属性名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/12 15:41
     */
    @Column(name = "field")
    private String field;

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

    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
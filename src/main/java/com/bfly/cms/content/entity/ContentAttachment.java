package com.bfly.cms.content.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 文章内容附件
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/27 11:28
 */
@Embeddable
public class ContentAttachment implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 文件名
     */
    @Column(name = "filename")
    private String filename;

    /**
     * 下载次数
     */
    @Column(name = "download_count")
    private int count;

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


    public int getCount() {
        return count;
    }


    public void setCount(int count) {
        this.count = count;
    }


}
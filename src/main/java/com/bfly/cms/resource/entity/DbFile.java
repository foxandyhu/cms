package com.bfly.cms.resource.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 上传附件类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 22:49
 */
@Entity
@Table(name = "d_upload")
public class DbFile implements Serializable {

    private static final long serialVersionUID = 5788503427134579932L;

    /**
     * 文件名
     */
    @Id
    @Column(name = "filename")
    private String filename;

    /**
     * 文件大小(字节)
     */
    @Column(name = "length")
    private int length;

    /**
     * 最后修改时间
     */
    @Column(name = "last_modified")
    private long lastModified;

    /**
     * 内容
     */
    @Column(name = "content")
    private byte[] content;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
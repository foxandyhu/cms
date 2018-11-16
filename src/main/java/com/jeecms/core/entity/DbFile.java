package com.jeecms.core.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 上传附件类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 22:49
 */
@Entity
@Table(name = "jo_upload")
public class DbFile implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    @Id
    @Column(name = "filename")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    /**
     * 文件大小(字节)
     */
    @Column(name = "length")
    private Integer length;

    /**
     * 最后修改时间
     */
    @Column(name = "last_modified")
    private Long lastModified;

    /**
     * 内容
     */
    @Column(name = "content")
    private byte[] content;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public Integer getLength() {
        return length;
    }


    public void setLength(Integer length) {
        this.length = length;
    }


    public Long getLastModified() {
        return lastModified;
    }


    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public byte[] getContent() {
        return content;
    }


    public void setContent(byte[] content) {
        this.content = content;
    }


}
package com.bfly.cms.entity.main;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ContentAttachment implements Serializable {
    private static final long serialVersionUID = 1L;

    public ContentAttachment(){ }

    public ContentAttachment(String path, String name, Integer count) {
        this.path = path;
        this.name = name;
        this.count = count;
    }

    @Column(name = "attachment_path")
    private String path;

    @Column(name = "attachment_name")
    private String name;

    @Column(name = "filename")
    private String filename;

    @Column(name = "download_count")
    private Integer count;

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


    public Integer getCount() {
        return count;
    }

    
    public void setCount(Integer count) {
        this.count = count;
    }


}
package com.bfly.cms.resource.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 模板类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 22:54
 */
@Entity
@Table(name = "d_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class DbTpl implements Serializable {

    private static final long serialVersionUID = 1329708564010579793L;

    /**
     * 模板名称
     */
    @Id
    @Column(name = "tpl_name")
    private String id;

    /**
     * 模板内容
     */
    @Column(name = "tpl_source")
    private String source;

    /**
     * 最后修改时间
     */
    @Column(name = "last_modified")
    private long lastModified;

    /**
     * 是否目录
     */
    @Column(name = "is_directory")
    private boolean directory;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public long getLastModified() {
        return lastModified;
    }


    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }


    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }


}
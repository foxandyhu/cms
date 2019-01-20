package com.bfly.cms.words.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * CMS内容关键词
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:31
 */
@Entity
@Table(name = "d_keyword")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class Keywords implements Serializable {

    private static final long serialVersionUID = -8472333146371666775L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 名称
     */
    @Column(name = "keyword_name")
    @NotBlank(message = "关键字名称不能为空!")
    private String name;

    /**
     * 链接
     */
    @Column(name = "url")
    @NotBlank(message = "链接地址不能为空!")
    private String url;

    /**
     * 是否禁用
     */
    @Column(name = "is_disabled")
    private boolean disabled;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsUser;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户常用菜单
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:23
 */
@Entity
@Table(name = "jc_user_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsUserMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String name;

    /**
     * 菜单地址
     */
    @Column(name = "menu_url")
    private String url;

    @Column(name = "priority")
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CmsUser user;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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


    public Integer getPriority() {
        return priority;
    }


    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public CmsUser getUser() {
        return user;
    }


    public void setUser(CmsUser user) {
        this.user = user;
    }


}
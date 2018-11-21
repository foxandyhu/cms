package com.bfly.core.entity;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * CMS角色类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 16:57
 */
@Entity
@Table(name = "jc_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsRole implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String name;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private Integer priority;

    /**
     * 角色等级
     */
    @Column(name = "role_level")
    private Integer level;

    /**
     * 拥有所有权限
     */
    @Column(name = "is_super")
    private Boolean all;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @ElementCollection
    @CollectionTable(name = "jc_role_permission", joinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "uri")
    private Set<String> perms;

    @ManyToMany
    @JoinTable(name = "jc_user_role", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private Set<CmsUser> users;


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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getAll() {
        return all;
    }

    public void setAll(Boolean all) {
        this.all = all;
    }

    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }

    public Set<String> getPerms() {
        return perms;
    }


    public void setPerms(Set<String> perms) {
        this.perms = perms;
    }

    public Set<CmsUser> getUsers() {
        return users;
    }

    public void setUsers(Set<CmsUser> users) {
        this.users = users;
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (getPriority() != null) {
            json.put("priority", getPriority());
        } else {
            json.put("priority", "");
        }
        if (getLevel() != null) {
            json.put("level", getLevel());
        } else {
            json.put("level", "");
        }
        if (getAll() != null) {
            json.put("all", getAll());
        } else {
            json.put("all", "");
        }
        JSONArray jsonArray = new JSONArray();
        if (getPerms() != null && getPerms().size() > 0) {
            Set<String> set = getPerms();
            int index = 0;
            for (String s : set) {
                jsonArray.put(index, s);
                index++;
            }
        }
        json.put("perms", jsonArray);
        return json;
    }

    public void init() {
        if (getLevel() == null) {
            setLevel(10);
        }
        if (getPriority() == null) {
            setPriority(10);
        }
        if (getAll() == null) {
            setAll(false);
        }
    }

    public static Integer[] fetchIds(Collection<CmsRole> roles) {
        if (roles == null) {
            return null;
        }
        Integer[] ids = new Integer[roles.size()];
        int i = 0;
        for (CmsRole r : roles) {
            ids[i++] = r.getId();
        }
        return ids;
    }

    public void delFromUsers(CmsUser user) {
        if (user == null) {
            return;
        }
        Set<CmsUser> set = getUsers();
        if (set == null) {
            return;
        }
        set.remove(user);
    }
}
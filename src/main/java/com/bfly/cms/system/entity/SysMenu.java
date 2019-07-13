package com.bfly.cms.system.entity;

import com.bfly.cms.user.entity.UserRole;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 系统菜单
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/12 18:30
 */
@Entity
@Table(name = "sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 9161811857617910658L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 菜单名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:35
     */
    @Column(name = "name")
    @NotBlank(message = "菜单名称不能为空!")
    private String name;

    /**
     * 菜单链接地址
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:35
     */
    @NotBlank(message = "链接地址不能为空!")
    @Column(name = "url")
    private String url;

    /**
     * 菜单层级
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:35
     */
    @Column(name = "level")
    private int level;

    /**
     * 菜单排序
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:36
     */
    @Column(name = "seq")
    private int seq;

    /**
     * 父节点
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:44
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private SysMenu parent;

    /**
     * 子节点
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:44
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<SysMenu> children;

    /**
     * 角色菜单关系维护
     */
    @ManyToMany(mappedBy = "menus")
    private List<UserRole> roles;

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public SysMenu getParent() {
        return parent;
    }

    public void setParent(SysMenu parent) {
        this.parent = parent;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}

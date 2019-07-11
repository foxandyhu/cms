package com.bfly.cms.user.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * 管理员角色类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 16:57
 */
@Entity
@Table(name = "user_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 3196950629688102618L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    @NotBlank(message = "角色名称不能为空!")
    private String name;

    /**
     * 用户角色关系维护
     */
    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private Set<User> users;

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
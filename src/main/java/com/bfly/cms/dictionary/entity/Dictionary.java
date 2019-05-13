package com.bfly.cms.dictionary.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 数据字典
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 16:18
 */
@Entity
@Table(name = "d_dictionary")
public class Dictionary implements Serializable {

    private static final long serialVersionUID = -5870692912576937801L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotBlank(message = "数据名称不能为空!")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "type")
    @NotBlank(message = "数据分类不能为空!")
    private String type;


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


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }
}
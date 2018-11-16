package com.jeecms.core.entity;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 数据字典
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 16:18
 */
@Entity
@Table(name = "jc_dictionary")
public class CmsDictionary implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "type")
    private String type;


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


    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getValue())) {
            json.put("value", getValue());
        } else {
            json.put("value", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getType())) {
            json.put("type", getType());
        } else {
            json.put("type", "");
        }
        return json;
    }
}
package com.bfly.cms.system.entity;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/27 11:24
 */
public class CmsField implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String fieldType;
    private String fieldDefault;
    private String fieldProperty;
    private String comment;
    private String nullable;
    private String extra;
    private String length;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldDefault() {
        return fieldDefault;
    }

    public void setFieldDefault(String fieldDefault) {
        this.fieldDefault = fieldDefault;
    }

    public String getFieldProperty() {
        return fieldProperty;
    }

    public void setFieldProperty(String fieldProperty) {
        this.fieldProperty = fieldProperty;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getFieldType())) {
            json.put("fieldType", getFieldType());
        } else {
            json.put("fieldType", "");
        }
        if (StringUtils.isNotBlank(getFieldDefault())) {
            json.put("fieldDefault", getFieldDefault());
        } else {
            json.put("fieldDefault", "");
        }
        if (StringUtils.isNotBlank(getFieldProperty())) {
            json.put("fieldProperty", getFieldProperty());
        } else {
            json.put("fieldProperty", "");
        }
        if (StringUtils.isNotBlank(getComment())) {
            json.put("comment", getComment());
        } else {
            json.put("comment", "");
        }
        if (StringUtils.isNotBlank(getNullable())) {
            json.put("nullable", getNullable());
        } else {
            json.put("nullable", "");
        }
        if (StringUtils.isNotBlank(getExtra())) {
            json.put("extra", getExtra());
        } else {
            json.put("extra", "");
        }
        if (StringUtils.isNotBlank(getLength())) {
            json.put("length", getLength());
        } else {
            json.put("length", "");
        }
        return json;
    }

}
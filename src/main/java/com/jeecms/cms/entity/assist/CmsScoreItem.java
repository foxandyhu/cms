package com.jeecms.cms.entity.assist;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Set;


public class CmsScoreItem implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (getScore() != null) {
            json.put("score", getScore());
        } else {
            json.put("score", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getImagePath())) {
            json.put("imagePath", getImagePath());
        } else {
            json.put("imagePath", "");
        }
        if (getPriority() != null) {
            json.put("priority", getPriority());
        } else {
            json.put("priority", "");
        }
        if (getGroup() != null && StringUtils.isNotBlank(getGroup().getName())) {
            json.put("groupName", getGroup().getName());
        } else {
            json.put("groupName", "");
        }
        if (getGroup() != null && getGroup().getId() != null) {
            json.put("groupId", getGroup().getId());
        } else {
            json.put("groupId", "");
        }
        return json;
    }

    public void init() {
        if (getPriority() == null) {
            setPriority(10);
        }
    }

    // primary key
    private Integer id;

    // fields
    private String name;
    private Integer score;
    private String imagePath;
    private Integer priority;

    // many to one
    private CmsScoreGroup group;

    // collections
    private Set<CmsScoreRecord> records;


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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }


    public CmsScoreGroup getGroup() {
        return group;
    }


    public void setGroup(CmsScoreGroup group) {
        this.group = group;
    }

    public Set<CmsScoreRecord> getRecords() {
        return records;
    }


    public void setRecords(Set<CmsScoreRecord> records) {
        this.records = records;
    }


}
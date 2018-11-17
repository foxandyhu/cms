package com.jeecms.cms.entity.assist;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 评分项
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 9:45
 */
@Entity
@Table(name = "jc_score_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
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

    @Id
    @Column(name = "score_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 评分名
     */
    @Column(name = "name")
    private String name;

    /**
     * 分值
     */
    @Column(name = "score")
    private Integer score;

    /**
     * 评分图标路径
     */
    @Column(name = "image_path")
    private String imagePath;

    /**
     * 排序
     */
    @Column(name = "priority")
    private Integer priority;

    /**
     * 评分组
     */
    @ManyToOne
    @JoinColumn(name = "score_group_id")
    private CmsScoreGroup group;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
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
package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 评分组
 * @author andy_hulibo@163.com
 * @date 2018/11/17 9:45
 */
@Entity
@Table(name = "jc_score_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsScoreGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "score_group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分组名
     */
    @Column(name = "name")
    private String name;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 是否启用
     */
    @Column(name = "enable")
    private boolean enable;

    /**
     * 是否默认
     */
    @Column(name = "def")
    private boolean def;

    /**
     * 所属站点
     */
    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Set<CmsScoreItem> items;


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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }


    public Set<CmsScoreItem> getItems() {
        return items;
    }


    public void setItems(Set<CmsScoreItem> items) {
        this.items = items;
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
        if (StringUtils.isNotBlank(getDescription())) {
            json.put("description", getDescription());
        } else {
            json.put("description", "");
        }
        if (getEnable() != null) {
            json.put("enable", getEnable());
        } else {
            json.put("enable", "");
        }
        if (getDef() != null) {
            json.put("def", getDef());
        } else {
            json.put("def", "");
        }
        return json;
    }

    public void init() {
        if (getEnable() == null) {
            setEnable(false);
        }
        if (getDef() == null) {
            setDef(false);
        }
    }

    public List<CmsScoreItem> getOrderItems() {
        Set<CmsScoreItem> items = getItems();
        Iterator<CmsScoreItem> it = items.iterator();
        List<CmsScoreItem> list = new ArrayList<>();
        while (it.hasNext()) {
            list.add(it.next());
        }
        Collections.sort(list, new ItemComparator());
        return list;
    }

    private class ItemComparator implements Comparator<CmsScoreItem> {
        @Override
        public int compare(CmsScoreItem o1, CmsScoreItem o2) {
            return o1.getPriority() - o2.getPriority();
        }
    }

    public Boolean getEnable() {
        return isEnable();
    }

    public Boolean getDef() {
        return isDef();
    }
}
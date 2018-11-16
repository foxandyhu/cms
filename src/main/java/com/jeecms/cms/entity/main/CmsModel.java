package com.jeecms.cms.entity.main;

import com.jeecms.core.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.jeecms.cms.Constants.*;
import static com.jeecms.common.web.Constants.DEFAULT;

/**
 * CMS模型类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 11:29
 */
@Entity
@Table(name = "jc_model")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Integer TITLE_IMG_WIDTH = 139;
    private static final Integer TITLE_IMG_HEIGHT = 139;
    private static final Integer CONTENT_IMG_WIDTH = 310;
    private static final Integer CONTENT_IMG_HEIGHT = 310;

    @Id
    @Column(name = "model_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "model_name")
    private String name;

    /**
     * 路径
     */
    @Column(name = "model_path")
    private String path;

    /**
     * 栏目模板前缀
     */
    @Column(name = "tpl_channel_prefix")
    private String tplChannelPrefix;

    /**
     * 内容模板前缀
     */
    @Column(name = "tpl_content_prefix")
    private String tplContentPrefix;

    /**
     * 栏目标题图宽度
     */
    @Column(name = "title_img_width")
    private Integer titleImgWidth;

    /**
     * 栏目标题图高度
     */
    @Column(name = "title_img_height")
    private Integer titleImgHeight;

    /**
     * 栏目内容图宽度
     */
    @Column(name = "content_img_width")
    private Integer contentImgWidth;

    /**
     * 栏目内容图高度
     */
    @Column(name = "content_img_height")
    private Integer contentImgHeight;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private Integer priority;

    /**
     * 是否有内容
     */
    @Column(name = "has_content")
    private Boolean hasContent;

    /**
     * 是否禁用
     */
    @Column(name = "is_disabled")
    private Boolean disabled;

    /**
     * 是否默认模型
     */
    @Column(name = "is_def")
    private Boolean def;

    /**
     * 是否全站模型
     */
    @Column(name = "is_global")
    private Boolean global;


    /**
     * 非全站模型所属站点
     */
    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "model")
    private Set<CmsModelItem> items;


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


    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }


    public String getTplChannelPrefix() {
        return tplChannelPrefix;
    }


    public void setTplChannelPrefix(String tplChannelPrefix) {
        this.tplChannelPrefix = tplChannelPrefix;
    }

    public String getTplContentPrefix() {
        return tplContentPrefix;
    }


    public void setTplContentPrefix(String tplContentPrefix) {
        this.tplContentPrefix = tplContentPrefix;
    }


    public Integer getTitleImgWidth() {
        return titleImgWidth;
    }


    public void setTitleImgWidth(Integer titleImgWidth) {
        this.titleImgWidth = titleImgWidth;
    }


    public Integer getTitleImgHeight() {
        return titleImgHeight;
    }


    public void setTitleImgHeight(Integer titleImgHeight) {
        this.titleImgHeight = titleImgHeight;
    }

    public Integer getContentImgWidth() {
        return contentImgWidth;
    }


    public void setContentImgWidth(Integer contentImgWidth) {
        this.contentImgWidth = contentImgWidth;
    }


    public Integer getContentImgHeight() {
        return contentImgHeight;
    }

    public void setContentImgHeight(Integer contentImgHeight) {
        this.contentImgHeight = contentImgHeight;
    }


    public Integer getPriority() {
        return priority;
    }


    public void setPriority(Integer priority) {
        this.priority = priority;
    }


    public Boolean getHasContent() {
        return hasContent;
    }


    public void setHasContent(Boolean hasContent) {
        this.hasContent = hasContent;
    }

    public Boolean getDisabled() {
        return disabled;
    }


    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getDef() {
        return def;
    }


    public void setDef(Boolean def) {
        this.def = def;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }


    public Set<CmsModelItem> getItems() {
        return items;
    }

    public void setItems(Set<CmsModelItem> items) {
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
        if (StringUtils.isNotBlank(getPath())) {
            json.put("path", getPath());
        } else {
            json.put("path", "");
        }
        if (StringUtils.isNotBlank(getTplChannelPrefix())) {
            json.put("tplChannelPrefix", getTplChannelPrefix());
        } else {
            json.put("tplChannelPrefix", "");
        }
        if (StringUtils.isNotBlank(getTplContentPrefix())) {
            json.put("tplContentPrefix", getTplContentPrefix());
        } else {
            json.put("tplContentPrefix", "");
        }
        if (getTitleImgWidth() != null) {
            json.put("titleImgWidth", getTitleImgWidth());
        } else {
            json.put("titleImgWidth", "");
        }
        if (getTitleImgHeight() != null) {
            json.put("titleImgHeight", getTitleImgHeight());
        } else {
            json.put("titleImgHeight", "");
        }
        if (getContentImgWidth() != null) {
            json.put("contentImgWidth", getContentImgWidth());
        } else {
            json.put("contentImgWidth", "");
        }
        if (getContentImgHeight() != null) {
            json.put("contentImgHeight", getContentImgHeight());
        } else {
            json.put("contentImgHeight", "");
        }
        if (getPriority() != null) {
            json.put("priority", getPriority());
        } else {
            json.put("priority", "");
        }
        if (getHasContent() != null) {
            json.put("hasContent", getHasContent());
        } else {
            json.put("hasContent", "");
        }
        if (getDisabled() != null) {
            json.put("disabled", getDisabled());
        } else {
            json.put("disabled", "");
        }
        if (getDef() != null) {
            json.put("def", getDef());
        } else {
            json.put("def", "");
        }
        if (getGlobal() != null) {
            json.put("global", getGlobal());
        } else {
            json.put("global", "");
        }
        return json;
    }

    public String getTplChannel(String solution, boolean def) {
        StringBuilder t = new StringBuilder();
        t.append(solution).append("/");
        if (getHasContent()) {
            t.append(TPLDIR_CHANNEL);
        } else {
            t.append(TPLDIR_ALONE);
        }
        t.append("/");
        String prefix = getTplChannelPrefix();
        if (def) {
            if (!StringUtils.isBlank(prefix)) {
                t.append(prefix);
            } else {
                t.append(DEFAULT);
            }
            t.append(TPL_SUFFIX);
        } else {
            if (!StringUtils.isBlank(prefix)) {
                t.append(prefix);
            }
        }
        return t.toString();
    }

    public String getTplContent(String solution, boolean def) {
        StringBuilder t = new StringBuilder();
        t.append(solution).append("/");
        t.append(TPLDIR_CONTENT);
        t.append("/");
        String prefix = getTplContentPrefix();
        if (def) {
            if (!StringUtils.isBlank(prefix)) {
                t.append(prefix);
            } else {
                t.append(DEFAULT);
            }
            t.append(TPL_SUFFIX);
        } else {
            if (!StringUtils.isBlank(prefix)) {
                t.append(prefix);
            }
        }
        return t.toString();

    }


    public List<String> getModelItems() {
        Set<CmsModelItem> items = getItems();
        List<String> fileList = new ArrayList<>();
        Iterator<CmsModelItem> it = items.iterator();
        while (it.hasNext()) {
            fileList.add(it.next().getField());
        }
        return fileList;
    }

    public CmsModelItem findModelItem(String field, boolean isChannel) {
        CmsModelItem item = null;
        Set<CmsModelItem> items = getItems();
        for (CmsModelItem modelItem : items) {
            if (modelItem.getChannel().equals(isChannel) && modelItem.getField().equals(field)) {
                item = modelItem;
                break;
            }
        }
        return item;
    }

    public void init() {
        if (getDisabled() == null) {
            setDisabled(false);
        }
        if (getDef() == null) {
            setDef(false);
        }
        if (getContentImgWidth() == null) {
            setContentImgHeight(CONTENT_IMG_HEIGHT);
        }
        if (getContentImgWidth() == null) {
            setContentImgWidth(CONTENT_IMG_WIDTH);
        }
        if (getTitleImgWidth() == null) {
            setTitleImgWidth(TITLE_IMG_WIDTH);
        }
        if (getTitleImgHeight() == null) {
            setTitleImgHeight(TITLE_IMG_HEIGHT);
        }
        if (getPriority() == null) {
            setPriority(10);
        }
    }
}
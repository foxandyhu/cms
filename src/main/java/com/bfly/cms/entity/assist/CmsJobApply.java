package com.bfly.cms.entity.assist;

import com.bfly.cms.entity.main.Content;
import com.bfly.common.util.DateUtils;
import com.bfly.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 职位申请表
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:29
 */
@Entity
@Table(name = "jc_job_apply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region = "beanCache")
public class CmsJobApply implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (getApplyTime() != null) {
            json.put("applyTime", DateUtils.parseDateToTimeStr(getApplyTime()));
        } else {
            json.put("applyTime", "");
        }
        if (getContent() != null && getContent().getId() != null) {
            json.put("contentId", getContent().getId());
        } else {
            json.put("contentId", "");
        }
        if (getContent() != null && StringUtils.isNotBlank(getContent().getTitle())) {
            json.put("contentTitle", getContent().getTitle());
        } else {
            json.put("contentTitle", "");
        }
        if (getContent() != null && StringUtils.isNotBlank(getContent().getUrl())) {
            json.put("contentURL", getContent().getUrl());
        } else {
            json.put("contentURL", "");
        }
        if (getUser() != null && getUser().getId() != null) {
            json.put("userId", getUser().getId());
        } else {
            json.put("userId", "");
        }
        if (getUser() != null && StringUtils.isNotBlank(getUser().getUsername())) {
            json.put("username", getUser().getUsername());
        } else {
            json.put("username", "");
        }
        return json;
    }

    @Id
    @Column(name = "job_apply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *申请时间
     */
    @Column(name = "apply_time")
    private Date applyTime;

    /**
     * 职位信息
     */
    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    /**
     * 所属用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private CmsUser user;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }


    public Content getContent() {
        return content;
    }


    public void setContent(Content content) {
        this.content = content;
    }

    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }


}
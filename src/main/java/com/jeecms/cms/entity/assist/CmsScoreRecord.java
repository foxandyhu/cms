package com.jeecms.cms.entity.assist;

import com.jeecms.cms.entity.main.Content;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;


public class CmsScoreRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    // primary key
    private Integer id;

    // fields
    private Integer count;

    // many to one
    private CmsScoreItem item;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public CmsScoreItem getItem() {
        return item;
    }

    public void setItem(CmsScoreItem item) {
        this.item = item;
    }


    public Content getContent() {
        return content;
    }


    public void setContent(Content content) {
        this.content = content;
    }

}
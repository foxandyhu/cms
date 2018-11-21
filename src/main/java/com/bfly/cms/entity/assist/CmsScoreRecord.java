package com.bfly.cms.entity.assist;

import com.bfly.cms.entity.main.Content;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 评分纪录
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 9:49
 */
@Entity
@Table(name = "jc_score_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsScoreRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "score_record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 评分次数
     */
    @Column(name = "score_count")
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "score_item_id")
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
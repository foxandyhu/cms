package com.jeecms.cms.entity.main;

import com.jeecms.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * CMS内容审核信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 13:08
 */
@Entity
@Table(name = "jc_content_check")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ContentCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 草稿
     */
    public static final byte DRAFT = 0;
    /**
     * 审核中
     */
    public static final byte CHECKING = 1;
    /**
     * 退回
     */
    public static final byte REJECT = -1;
    /**
     * 已审核
     */
    public static final byte CHECKED = 2;
    /**
     * 回收站
     */
    public static final byte RECYCLE = 3;
    /**
     * 投稿
     */
    public static final byte CONTRIBUTE = 4;
    /**
     * 归档
     */
    public static final byte PIGEONHOLE = 5;

    public void init() {
        byte zero = 0;
        if (getCheckStep() == null) {
            setCheckStep(zero);
        }
        if (getRejected() == null) {
            setRejected(false);
        }
    }

    public void blankToNull() {
        if (StringUtils.isBlank(getCheckOpinion())) {
            setCheckOpinion(null);
        }
    }

    @Column(name = "content_id")
    private Integer id;

    /**
     * 审核步数
     */
    @Column(name = "check_step")
    private Byte checkStep;

    /**
     * 审核意见
     */
    @Column(name = "check_opinion")
    private String checkOpinion;

    /**
     * 是否退回
     */
    @Column(name = "is_rejected")
    private Boolean rejected;

    /**
     * 终审时间
     */
    @Column(name = "check_date")
    private Date checkDate;

    /**
     * 终审者
     */
    @ManyToOne
    @JoinColumn(name = "reviewer")
    private CmsUser reviewer;

    @Id
    @OneToOne
    @JoinColumn(name = "content_id")
    private Content content;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getCheckStep() {
        return checkStep;
    }

    public void setCheckStep(Byte checkStep) {
        this.checkStep = checkStep;
    }


    public String getCheckOpinion() {
        return checkOpinion;
    }

    public void setCheckOpinion(String checkOpinion) {
        this.checkOpinion = checkOpinion;
    }


    public Boolean getRejected() {
        return rejected;
    }


    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }


    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public CmsUser getReviewer() {
        return reviewer;
    }

    public void setReviewer(CmsUser reviewer) {
        this.reviewer = reviewer;
    }

    public Content getContent() {
        return content;
    }


    public void setContent(Content content) {
        this.content = content;
    }

}
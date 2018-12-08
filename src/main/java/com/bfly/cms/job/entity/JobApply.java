package com.bfly.cms.job.entity;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.member.entity.Member;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 职位申请表
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:29
 */
@Entity
@Table(name = "job_apply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region = "beanCache")
public class JobApply implements Serializable {

    private static final long serialVersionUID = 2166370538443637214L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     *申请时间
     */
    @Column(name = "apply_time")
    private Date applyTime;

    /**
     * 所属文章
     */
    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    /**
     * 所属用户
     */
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
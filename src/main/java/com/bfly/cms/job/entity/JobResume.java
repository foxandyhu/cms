package com.bfly.cms.job.entity;

import com.bfly.cms.member.entity.Member;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户简历
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 22:32
 */
@Entity
@Table(name = "job_resume")
public class JobResume implements Serializable {

    private static final long serialVersionUID = 2819972390662386634L;

    @Id
    @Column(name = "member_id", unique = true, nullable = false)
    private int id;

    /**
     * 简历名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 期望工作性质
     */
    @Column(name = "expect_work_nature")
    private String expectWorkNature;

    /**
     * 期望工作地点
     */
    @Column(name = "expect_work_place")
    private String expectWorkPlace;

    /**
     * 期望职位类别
     */
    @Column(name = "expect_category")
    private String expectCategory;

    /**
     * 期望月薪
     */
    @Column(name = "expect_salary")
    private String expectSalary;

    /**
     * 毕业学校
     */
    @Column(name = "school")
    private String school;

    /**
     * 毕业时间
     */
    @Column(name = "graduation")
    private Date graduation;

    /**
     * 学历
     */
    @Column(name = "education")
    private String education;

    /**
     * 专业
     */
    @Column(name = "profession")
    private String profession;

    /**
     * 最近工作公司名称
     */
    @Column(name = "recent_company")
    private String recentCompany;

    /**
     * 最近公司所属行业
     */
    @Column(name = "recent_industry")
    private String recentIndustry;

    /**
     * 公司规模
     */
    @Column(name = "recent_scale")
    private String recentScale;

    /**
     * 职位名称
     */
    @Column(name = "recent_job_name")
    private String recentJobName;

    /**
     * 职位类别
     */
    @Column(name = "recent_job_category")
    private String recentJobCategory;

    /**
     * 工作起始时间
     */
    @Column(name = "recent_job_start")
    private Date recentJobStart;

    /**
     * 工作起始时间
     */
    @Column(name = "recent_job_end")
    private Date recentJobEnd;

    /**
     * 下属人数
     */
    @Column(name = "recent_subordinates")
    private String recentSubordinates;

    /**
     * 工作描述
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 自我评价
     */
    @Column(name = "self_introduction")
    private String selfIntroduction;

    /**
     * 所属用户
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "member_id")
    private Member member;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpectWorkNature() {
        return expectWorkNature;
    }

    public void setExpectWorkNature(String expectWorkNature) {
        this.expectWorkNature = expectWorkNature;
    }

    public String getExpectWorkPlace() {
        return expectWorkPlace;
    }

    public void setExpectWorkPlace(String expectWorkPlace) {
        this.expectWorkPlace = expectWorkPlace;
    }

    public String getExpectCategory() {
        return expectCategory;
    }

    public void setExpectCategory(String expectCategory) {
        this.expectCategory = expectCategory;
    }

    public String getExpectSalary() {
        return expectSalary;
    }

    public void setExpectSalary(String expectSalary) {
        this.expectSalary = expectSalary;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Date getGraduation() {
        return graduation;
    }

    public void setGraduation(Date graduation) {
        this.graduation = graduation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRecentCompany() {
        return recentCompany;
    }

    public void setRecentCompany(String recentCompany) {
        this.recentCompany = recentCompany;
    }

    public String getRecentIndustry() {
        return recentIndustry;
    }

    public void setRecentIndustry(String recentIndustry) {
        this.recentIndustry = recentIndustry;
    }

    public String getRecentScale() {
        return recentScale;
    }

    public void setRecentScale(String recentScale) {
        this.recentScale = recentScale;
    }

    public String getRecentJobName() {
        return recentJobName;
    }

    public void setRecentJobName(String recentJobName) {
        this.recentJobName = recentJobName;
    }

    public String getRecentJobCategory() {
        return recentJobCategory;
    }

    public void setRecentJobCategory(String recentJobCategory) {
        this.recentJobCategory = recentJobCategory;
    }

    public Date getRecentJobStart() {
        return recentJobStart;
    }

    public void setRecentJobStart(Date recentJobStart) {
        this.recentJobStart = recentJobStart;
    }

    public String getRecentSubordinates() {
        return recentSubordinates;
    }

    public void setRecentSubordinates(String recentSubordinates) {
        this.recentSubordinates = recentSubordinates;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRecentJobEnd() {
        return recentJobEnd;
    }

    public void setRecentJobEnd(Date recentJobEnd) {
        this.recentJobEnd = recentJobEnd;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
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
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class JobResume implements Serializable {

    private static final long serialVersionUID = 2819972390662386634L;

    @Id
    @Column(name = "member_id", unique = true, nullable = false)
    private int id;

    /**
     * 简历名称
     */
    @Column(name = "resume_name")
    private String resumeName;

    /**
     * 期望工作性质
     */
    @Column(name = "target_worknature")
    private String targetWorknature;

    /**
     * 期望工作地点
     */
    @Column(name = "target_workplace")
    private String targetWorkplace;

    /**
     * 期望职位类别
     */
    @Column(name = "target_category")
    private String targetCategory;

    /**
     * 期望月薪
     */
    @Column(name = "target_salary")
    private String targetSalary;

    /**
     * 毕业学校
     */
    @Column(name = "edu_school")
    private String eduSchool;

    /**
     * 毕业时间
     */
    @Column(name = "edu_graduation")
    private Date eduGraduation;

    /**
     * 学历
     */
    @Column(name = "edu_back")
    private String eduBack;

    /**
     * 专业
     */
    @Column(name = "edu_discipline")
    private String eduDiscipline;

    /**
     * 最近工作公司名称
     */
    @Column(name = "recent_company")
    private String recentCompany;

    /**
     * 最近公司所属行业
     */
    @Column(name = "company_industry")
    private String companyIndustry;

    /**
     * 公司规模
     */
    @Column(name = "company_scale")
    private String companyScale;

    /**
     * 职位名称
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * 职位类别
     */
    @Column(name = "job_category")
    private String jobCategory;

    /**
     * 工作起始时间
     */
    @Column(name = "job_start")
    private Date jobStart;

    /**
     * 下属人数
     */
    @Column(name = "subordinates")
    private String subordinates;

    /**
     * 工作描述
     */
    @Column(name = "job_description")
    private String jobDescription;

    /**
     * 自我评价
     */
    @Column(name = "self_evaluation")
    private String selfEvaluation;

    /**
     * 所属用户
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "member_id")
    private Member member;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public String getTargetWorknature() {
        return targetWorknature;
    }

    public void setTargetWorknature(String targetWorknature) {
        this.targetWorknature = targetWorknature;
    }

    public String getTargetWorkplace() {
        return targetWorkplace;
    }

    public void setTargetWorkplace(String targetWorkplace) {
        this.targetWorkplace = targetWorkplace;
    }

    public String getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(String targetCategory) {
        this.targetCategory = targetCategory;
    }

    public String getTargetSalary() {
        return targetSalary;
    }

    public void setTargetSalary(String targetSalary) {
        this.targetSalary = targetSalary;
    }

    public String getEduSchool() {
        return eduSchool;
    }

    public void setEduSchool(String eduSchool) {
        this.eduSchool = eduSchool;
    }

    public Date getEduGraduation() {
        return eduGraduation;
    }

    public void setEduGraduation(Date eduGraduation) {
        this.eduGraduation = eduGraduation;
    }

    public String getEduBack() {
        return eduBack;
    }

    public void setEduBack(String eduBack) {
        this.eduBack = eduBack;
    }

    public String getEduDiscipline() {
        return eduDiscipline;
    }

    public void setEduDiscipline(String eduDiscipline) {
        this.eduDiscipline = eduDiscipline;
    }

    public String getRecentCompany() {
        return recentCompany;
    }

    public void setRecentCompany(String recentCompany) {
        this.recentCompany = recentCompany;
    }

    public String getCompanyIndustry() {
        return companyIndustry;
    }

    public void setCompanyIndustry(String companyIndustry) {
        this.companyIndustry = companyIndustry;
    }

    public String getCompanyScale() {
        return companyScale;
    }

    public void setCompanyScale(String companyScale) {
        this.companyScale = companyScale;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public Date getJobStart() {
        return jobStart;
    }

    public void setJobStart(Date jobStart) {
        this.jobStart = jobStart;
    }

    public String getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(String subordinates) {
        this.subordinates = subordinates;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getSelfEvaluation() {
        return selfEvaluation;
    }

    public void setSelfEvaluation(String selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
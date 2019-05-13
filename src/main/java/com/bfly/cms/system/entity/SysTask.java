package com.bfly.cms.system.entity;

import com.bfly.cms.user.entity.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 系统任务
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:59
 */
@Entity
@Table(name = "sys_task")
public class SysTask implements Serializable {

    private static final long serialVersionUID = 7129971735226965086L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 任务执行代码
     */
    @Column(name = "task_code")
    private String taskCode;

    /**
     * 任务类型(1首页静态化、2栏目页静态化、3内容页静态化、4采集、5分发)
     */
    @Column(name = "task_type")
    @Min(value = 1, message = "任务类型不能为空!")
    private int type;

    /**
     * 任务名称
     */
    @Column(name = "task_name")
    @NotBlank(message = "任务名称不能为空!")
    private String name;

    /**
     * 任务类
     */
    @Column(name = "job_class")
    private String jobClass;

    /**
     * 执行周期分类(1非表达式 2 cron表达式)
     */
    @Column(name = "execycle")
    private int execycle;

    /**
     * 每月的哪天
     */
    @Column(name = "day_of_month")
    private int dayOfMonth;

    /**
     * 周几
     */
    @Column(name = "day_of_week")
    private int dayOfWeek;

    /**
     * 小时
     */
    @Column(name = "hour")
    private int hour;

    /**
     * 分钟
     */
    @Column(name = "minute")
    private int minute;

    /**
     * 间隔小时
     */
    @Column(name = "interval_hour")
    private int intervalHour;

    /**
     * 间隔分钟
     */
    @Column(name = "interval_minute")
    private int intervalMinute;

    /**
     * 1分钟、2小时、3日、4周、5月
     */
    @Column(name = "task_interval_unit")
    private int intervalUnit;

    /**
     * 规则表达式
     */
    @Column(name = "cron_expression")
    private String cronExpression;

    /**
     * 是否启用
     */
    @Column(name = "is_enable")
    private boolean enable;

    /**
     * 任务说明
     */
    @Column(name = "task_remark")
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建者
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    /**
     * 系统任务其他属性
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @CollectionTable(name = "sys_task_attr", joinColumns = @JoinColumn(name = "task_id"))
    @MapKeyColumn(name = "param_name")
    @Column(name = "param_value")
    private Map<String, String> attr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public int getExecycle() {
        return execycle;
    }

    public void setExecycle(int execycle) {
        this.execycle = execycle;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getIntervalHour() {
        return intervalHour;
    }

    public void setIntervalHour(int intervalHour) {
        this.intervalHour = intervalHour;
    }

    public int getIntervalMinute() {
        return intervalMinute;
    }

    public void setIntervalMinute(int intervalMinute) {
        this.intervalMinute = intervalMinute;
    }

    public int getIntervalUnit() {
        return intervalUnit;
    }

    public void setIntervalUnit(int intervalUnit) {
        this.intervalUnit = intervalUnit;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
}
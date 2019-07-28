package com.bfly.cms.system.entity;

import com.bfly.core.enums.TaskStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
     * 任务名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 12:21
     */
    @Column(name = "name")
    private String name;

    /**
     * 上一次执行时间
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 12:22
     */
    @Column(name = "pre_exec_time")
    private Date preExecTime;

    /**
     * 下一次执行时间
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 12:22
     */
    @Column(name = "next_exec_time")
    private Date nextExecTime;

    /**
     * 执行次数
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 12:23
     */
    @Column(name = "count")
    private int count;

    /**
     * 执行状态
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 12:23
     * @see com.bfly.core.enums.TaskStatus
     */
    @Column(name = "status")
    private int status;

    /**
     * 执行周期
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 12:24
     */
    @Column(name = "period")
    private String period;

    /**
     * 任务说明
     * @author andy_hulibo@163.com
     * @date 2019/7/27 21:01
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 上一次执行结果
     * @author andy_hulibo@163.com
     * @date 2019/7/28 9:00
     */
    @Column(name="pre_exec_result")
    private String preExecResult;

    /**
     * 状态名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 12:40
     */
    public String getStatusName() {
        TaskStatus status = TaskStatus.getStatus(getStatus());
        return status == null ? null : status.getName();
    }

    public String getRemark() {
        return remark;
    }

    public String getPreExecResult() {
        return preExecResult;
    }

    public void setPreExecResult(String preExecResult) {
        this.preExecResult = preExecResult;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public Date getPreExecTime() {
        return preExecTime;
    }

    public void setPreExecTime(Date preExecTime) {
        this.preExecTime = preExecTime;
    }

    public Date getNextExecTime() {
        return nextExecTime;
    }

    public void setNextExecTime(Date nextExecTime) {
        this.nextExecTime = nextExecTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
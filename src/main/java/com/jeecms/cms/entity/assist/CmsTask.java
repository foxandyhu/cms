package com.jeecms.cms.entity.assist;

import com.jeecms.common.util.DateUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class CmsTask implements Serializable {
    /**
     * 任务执行周期cron表达式
     */
    public static int EXECYCLE_CRON = 2;
    /**
     * 任务执行周期自定义
     */
    public static int EXECYCLE_DEFINE = 1;
    /**
     * 执行周期-分钟
     */
    public static int EXECYCLE_MINUTE = 1;
    /**
     * 执行周期-小时
     */
    public static int EXECYCLE_HOUR = 2;
    /**
     * 执行周期-日
     */
    public static int EXECYCLE_DAY = 3;
    /**
     * 执行周期-月
     */
    public static int EXECYCLE_WEEK = 4;
    /**
     * 执行周期-月
     */
    public static int EXECYCLE_MONTH = 5;
    /**
     * 首页静态任务
     */
    public static int TASK_STATIC_INDEX = 1;
    /**
     * 栏目页静态化任务
     */
    public static int TASK_STATIC_CHANNEL = 2;
    /**
     * 内容页静态化任务
     */
    public static int TASK_STATIC_CONTENT = 3;
    /**
     * 采集类任务
     */
    public static int TASK_ACQU = 4;
    /**
     * 分发类任务
     */
    public static int TASK_DISTRIBUTE = 5;
    /**
     * 采集源ID
     */
    public static String TASK_PARAM_ACQU_ID = "acqu_id";
    /**
     * 分发FTP ID
     */
    public static String TASK_PARAM_FTP_ID = "ftp_id";
    /**
     * 站点 ID
     */
    public static String TASK_PARAM_SITE_ID = "site_id";
    /**
     * 栏目 ID
     */
    public static String TASK_PARAM_CHANNEL_ID = "channel_id";
    /**
     * 分发到FTP 的文件夹参数前缀
     */
    public static String TASK_PARAM_FOLDER_PREFIX = "floder_";

    private static final long serialVersionUID = 1L;

    public void init(CmsSite site, CmsUser user) {
        if (getCreateTime() == null) {
            setCreateTime(Calendar.getInstance().getTime());
        }
        if (getUser() == null) {
            setUser(user);
        }
        if (getSite() == null) {
            setSite(site);
        }
    }

    public boolean getEnable() {
        return isEnable();
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getTaskCode())) {
            json.put("taskCode", getTaskCode());
        } else {
            json.put("taskCode", "");
        }
        if (getType() != null) {
            json.put("type", getType());
        } else {
            json.put("type", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getJobClass())) {
            json.put("jobClass", getJobClass());
        } else {
            json.put("jobClass", "");
        }
        if (getExecycle() != null) {
            json.put("execycle", getExecycle());
        } else {
            json.put("execycle", "");
        }
        if (getDayOfMonth() != null) {
            json.put("dayOfMonth", getDayOfMonth());
        } else {
            json.put("dayOfMonth", "");
        }
        if (getDayOfWeek() != null) {
            json.put("dayOfWeek", getDayOfWeek());
        } else {
            json.put("dayOfWeek", "");
        }
        if (getHour() != null) {
            json.put("hour", getHour());
        } else {
            json.put("hour", "");
        }
        if (getMinute() != null) {
            json.put("minute", getMinute());
        } else {
            json.put("minute", "");
        }
        if (getIntervalHour() != null) {
            json.put("intervalHour", getIntervalHour());
        } else {
            json.put("intervalHour", "");
        }
        if (getIntervalMinute() != null) {
            json.put("intervalMinute", getIntervalMinute());
        } else {
            json.put("intervalMinute", "");
        }
        if (getIntervalUnit() != null) {
            json.put("intervalUnit", getIntervalUnit());
        } else {
            json.put("intervalUnit", "");
        }
        if (StringUtils.isNotBlank(getCronExpression())) {
            json.put("cronExpression", getCronExpression());
        } else {
            json.put("cronExpression", "");
        }
        json.put("enable", getEnable());
        if (StringUtils.isNotBlank(getRemark())) {
            json.put("remark", getRemark());
        } else {
            json.put("remark", "");
        }
        if (getCreateTime() != null) {
            json.put("createTime", DateUtils.parseDateToTimeStr(getCreateTime()));
        } else {
            json.put("createTime", "");
        }
        if (getAttr() != null) {
            Map<String, String> map = getAttr();
            for (String key : map.keySet()) {
                json.put("attr_" + key, map.get(key));
            }
        }
        if (getUser() != null && StringUtils.isNotBlank(getUser().getUsername())) {
            json.put("username", getUser().getUsername());
        } else {
            json.put("username", "");
        }
        if (getUser() != null && getUser().getId() != null) {
            json.put("userId", getUser().getId());
        } else {
            json.put("userId", "");
        }
        return json;
    }

    // primary key
    private Integer id;

    // fields
    private String taskCode;
    private Integer type;
    private String name;
    private String jobClass;
    private Integer execycle;
    private Integer dayOfMonth;
    private Integer dayOfWeek;
    private Integer hour;
    private Integer minute;
    private Integer intervalHour;
    private Integer intervalMinute;
    private Integer intervalUnit;
    private String cronExpression;
    private boolean enable;
    private String remark;
    private Date createTime;

    // many to one
    private CmsUser user;
    private CmsSite site;

    // collections
    private Map<String, String> attr;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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

    public Integer getExecycle() {
        return execycle;
    }


    public void setExecycle(Integer execycle) {
        this.execycle = execycle;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }


    public Integer getDayOfWeek() {
        return dayOfWeek;
    }


    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }


    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }


    public Integer getIntervalHour() {
        return intervalHour;
    }

    public void setIntervalHour(Integer intervalHour) {
        this.intervalHour = intervalHour;
    }

    public Integer getIntervalMinute() {
        return intervalMinute;
    }


    public void setIntervalMinute(Integer intervalMinute) {
        this.intervalMinute = intervalMinute;
    }

    public Integer getIntervalUnit() {
        return intervalUnit;
    }

    public void setIntervalUnit(Integer intervalUnit) {
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


    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }


    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }


    public Map<String, String> getAttr() {
        return attr;
    }


    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }


}
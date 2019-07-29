package com.bfly.core.tasks;

import com.bfly.cms.statistic.service.ISiteAccessService;
import com.bfly.cms.statistic.service.ISiteAccessStatisticService;
import com.bfly.cms.system.entity.SysTask;
import com.bfly.cms.system.service.ISysTaskService;
import com.bfly.core.enums.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;

/**
 * 统计定时任务类
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/27 12:40
 */
@Configuration
public class StatisticTask implements IScheduled {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private ISiteAccessService accessService;
    @Autowired
    private ISiteAccessStatisticService statisticService;
    @Autowired
    private ISysTaskService taskService;

    private static final String ACCESS_STATISTIC_CACHE_TO_DB = "access_statistic_cache_to_db";
    private static final String ACCESS_STATISTIC_REPORT_PRE_DAYS = "access_statistic_report_pre_days";

    /**
     * 每10分钟一次刷新流量访问缓存
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 13:56
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    @ScheduledInfo(name = ACCESS_STATISTIC_CACHE_TO_DB, remark = "访问流量缓存数据每10分钟执行同步到数据库")
    public void accessStatisticFlushCacheToDB() {
        //开启状态的任务才能执行
        if (!allowRun(ACCESS_STATISTIC_CACHE_TO_DB)) {
            return;
        }
        String message = "执行成功!";
        try {
            accessService.cacheToDb();
        } catch (Exception e) {
            message = e.getMessage();
        }
        ScheduledTaskExecResult result = new ScheduledTaskExecResult(ACCESS_STATISTIC_CACHE_TO_DB, "0 0/10 * * * ?", Calendar.getInstance().getTime(), message);
        context.publishEvent(new ScheduledTaskExecCompleteEvent(result));
    }

    /**
     * 每天凌晨10分统计昨日的所有流量报表
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 13:56
     */
    @Scheduled(cron = "0 10 0 * * ?")
    @ScheduledInfo(name = ACCESS_STATISTIC_REPORT_PRE_DAYS, remark = "每天凌晨10分根据不同维度统计今日之前的流量报表")
    public void accessStatisticReportForPreDays() {
        if (!allowRun(ACCESS_STATISTIC_REPORT_PRE_DAYS)) {
            return;
        }
        String message = "执行成功!";
        try {
            statisticService.statisticToDb();
        } catch (Exception e) {
            message = e.getMessage();
        }
        ScheduledTaskExecResult result = new ScheduledTaskExecResult(ACCESS_STATISTIC_REPORT_PRE_DAYS, "0 10 0 * * ?", Calendar.getInstance().getTime(), message);
        context.publishEvent(new ScheduledTaskExecCompleteEvent(result));
    }

    /**
     * 判断任务是否允许运行
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/28 15:06
     */
    private boolean allowRun(String taskName) {
        SysTask task = taskService.getTask(taskName);
        return task.getStatus() == TaskStatus.START.getId();
    }
}

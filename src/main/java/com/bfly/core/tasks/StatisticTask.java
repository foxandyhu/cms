package com.bfly.core.tasks;

import com.bfly.cms.statistic.service.ISiteAccessService;
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

    /**
     * 每10分钟一次刷新流量访问缓存
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 13:56
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    @ScheduledInfo(name = "access_statistic_cache_to_db", remark = "访问流量缓存数据每10分钟执行同步到数据库")
    public void accessStatisticFlushCacheToDB() {
        accessService.cacheToDb();
        context.publishEvent(new ScheduledTaskExecCompleteEvent("access_statistic_cache_to_db", "0 0/10 * * * ?", Calendar.getInstance().getTime()));
    }

    /**
     * 每天凌晨10分统计昨日的所有流量报表
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 13:56
     */
    @Scheduled(cron = "0 10 0 * * ?")
    @ScheduledInfo(name = "access_statistic_report_pre_days", remark = "每天凌晨10分根据不同维度统计今日之前的流量报表")
    public void accessStatisticReportForPreDays() {
        accessService.statisticToDb();
        context.publishEvent(new ScheduledTaskExecCompleteEvent("access_statistic_report_pre_days", "0 10 0 * * ?", Calendar.getInstance().getTime()));
    }
}

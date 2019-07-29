package com.bfly.cms.statistic.service;

import com.bfly.cms.statistic.entity.SiteAccessStatistic;
import com.bfly.cms.statistic.entity.StatisticDataDTO;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.enums.StatisticType;

import java.util.Date;
import java.util.List;

/**
 * 统计
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/24 9:31
 */
public interface ISiteAccessStatisticService extends IBaseService<SiteAccessStatistic, Integer> {

    /**
     * 根据日期统计
     *
     * @param end   结束时间
     * @param begin 开始时间
     * @return 统计结果
     * @author andy_hulibo@163.com
     * @date 2019/7/24 12:17
     */
    List<StatisticDataDTO> statistic(Date begin, Date end);

    /**
     * 根据日期和统计类型统计
     *
     * @param type  统计类型
     * @param end   结束时间
     * @param begin 开始时间
     * @return 统计结果
     * @author andy_hulibo@163.com
     * @date 2019/7/24 12:21
     */
    List<StatisticDataDTO> statistic(Date begin, Date end, StatisticType type);

    /**
     * 分析数据并写入数据库该方法是定时任务每天凌晨时间调用
     * 根据不同唯独统计数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 8:04
     */
    void statisticToDb();
}

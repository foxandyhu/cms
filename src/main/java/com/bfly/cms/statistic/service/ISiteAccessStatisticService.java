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
     * @author andy_hulibo@163.com
     * @date 2019/7/24 12:17
     */
    List<StatisticDataDTO> statistic(Date begin, Date end);

    /**
     * 根据日期和统计类型统计
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 12:21
     */
    List<StatisticDataDTO> statistic(Date begin, Date end, StatisticType type);

}

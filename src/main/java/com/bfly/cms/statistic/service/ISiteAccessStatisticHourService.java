package com.bfly.cms.statistic.service;

import com.bfly.cms.statistic.entity.SiteAccessStatisticHour;
import com.bfly.core.base.service.IBaseService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 小时维度统计
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/24 9:31
 */
public interface ISiteAccessStatisticHourService extends IBaseService<SiteAccessStatisticHour, Integer> {

    /**
     * 根据日期查询小时段统计数据
     * @author andy_hulibo@163.com
     * @date 2019/7/24 12:56
     */
    List<Map<String,Object>> getSiteAccessStatisticHourByDate(Date date);
}

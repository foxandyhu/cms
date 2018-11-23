package com.bfly.cms.dao.assist;

import java.util.Date;
import java.util.List;

import com.bfly.cms.entity.assist.CmsSiteAccessStatistic;

/**
 * @author Tom
 */
public interface CmsSiteAccessStatisticDao {
    CmsSiteAccessStatistic save(CmsSiteAccessStatistic statistic);

    List<Object[]> statistic(Date begin, Date end, Integer siteId, String statisticType, String statisticValue);

    List<Object[]> statisticTotal(Date begin, Date end, Integer siteId, String statisticType, String statisticValue, Integer orderBy);

    List<Object[]> statisticByTarget(Date begin, Date end, Integer siteId, Integer target, String statisticType, String statisticValue);

    List<String> findStatisticColumnValues(Date begin, Date end, Integer siteId, String statisticType);

    List<Object[]> statisticByYear(Integer year, Integer siteId, String statisticType, String statisticValue, boolean groupByMonth, Integer orderBy);

    List<Object[]> statisticByYearByTarget(Integer year, Integer siteId, Integer target, String statisticType, String statisticValue);

}
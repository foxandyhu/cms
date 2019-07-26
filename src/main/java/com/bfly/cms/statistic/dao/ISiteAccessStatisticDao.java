package com.bfly.cms.statistic.dao;

import com.bfly.cms.statistic.entity.SiteAccessStatistic;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2019/7/24 9:30
 */
public interface ISiteAccessStatisticDao extends IBaseDao<SiteAccessStatistic, Integer> {

    /**
     * 根据时间和统计类型获得统计数据
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @param type  类型
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/24 13:27
     */
    @Query(value = "select sum(ip) as ip,sum(visitors) as uv,sum(pv) as pv,statistic_date as time,statistic_value as value from site_access_statistic where statistic_key=:type AND statistic_date >= DATE_FORMAT(:begin,'%Y-%m-%d') and statistic_date<=DATE_FORMAT(:end,'%Y-%m-%d') group by statistic_date,statistic_value", nativeQuery = true)
    List<Map<String, Object>> getSiteAccessStatisticByDateAndType(@Param("begin") Date begin, @Param("end") Date end, @Param("type") String type);


    /**
     * 统计年份数据
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @param type  类型
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/24 23:00
     */
    @Query(value = "select sum(ip) as ip,sum(visitors) as uv,sum(pv) as pv,DATE_FORMAT(statistic_date,'%m月') as time,statistic_value as value from site_access_statistic where statistic_key=:type AND statistic_date >= DATE_FORMAT(:begin,'%Y-%m-%d') and statistic_date<=DATE_FORMAT(:end,'%Y-%m-%d') group by time,statistic_value", nativeQuery = true)
    List<Map<String, Object>> getSiteAccessStatisticByYearAndType(@Param("begin") Date begin, @Param("end") Date end, @Param("type") String type);

}

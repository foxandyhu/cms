package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsSiteAccess;
import com.bfly.common.page.Pagination;

import java.util.Date;
import java.util.List;

/**
 * @author Tom
 */
public interface CmsSiteAccessDao {

    CmsSiteAccess saveOrUpdate(CmsSiteAccess access);

    Pagination findEnterPages(Integer siteId, Integer orderBy, Integer pageNo, Integer pageSize);

    CmsSiteAccess findAccessBySessionId(String sessionId);

    /**
     * 查询date之前最近的访问记录
     *
     * @param date
     * @return
     */
    CmsSiteAccess findRecentAccess(Date date, Integer siteId);

    /**
     * 统计日期站点流量
     *
     * @param date
     * @param siteId
     * @return
     */
    List<Object[]> statisticByDay(Date date, Integer siteId);

    /**
     * 统计日期站点流量（按小时分组）
     *
     * @param date
     * @param siteId
     * @return
     */
    List<Object[]> statisticByDayGroupByHour(Date date, Integer siteId);

    /**
     * 统计地区站点流量
     *
     * @param date
     * @param siteId
     * @return
     */
    List<Object[]> statisticByArea(Date date, Integer siteId);

    /**
     * 统计来源站点流量
     *
     * @param date
     * @param siteId
     * @return
     */
    List<Object[]> statisticBySource(Date date, Integer siteId);

    /**
     * 统计搜索引擎站点流量
     *
     * @param date
     * @param siteId
     * @return
     */
    List<Object[]> statisticByEngine(Date date, Integer siteId);

    /**
     * 统计外部链接站点流量
     *
     * @param date
     * @param siteId
     * @return
     */
    List<Object[]> statisticByLink(Date date, Integer siteId);

    /**
     * 统计关键词站点流量
     *
     * @param date
     * @param siteId
     * @return
     */
    List<Object[]> statisticByKeyword(Date date, Integer siteId);

    /**
     * 统计用户访问页数
     *
     * @param date
     * @param siteId
     * @return
     */
    List<Object[]> statisticByPageCount(Date date, Integer siteId);

    /**
     * 查询property列值
     *
     * @param property
     * @param siteId
     * @return
     */
    List<String> findPropertyValues(String property, Integer siteId);

    List<Object[]> statisticToday(Integer siteId, String area);

    List<Object[]> statisticTodayByTarget(Integer siteId, Integer target, String statisticColumn, String statisticValue);

    void clearByDate(Date date);

}

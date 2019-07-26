package com.bfly.cms.statistic.dao;

import com.bfly.cms.statistic.entity.SiteAccess;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2019/7/23 18:05
 */
public interface ISiteAccessDao extends IBaseDao<SiteAccess, Integer> {

    /**
     * 根据SessionID查找会话记录
     *
     * @param sessionId SessionID
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/23 18:06
     */
    @Query("select access from SiteAccess as access where access.sessionId=:sessionId")
    SiteAccess findSiteAccessBySessionId(@Param("sessionId") String sessionId);

    /**
     * 查找站点访问指定日期之前的日期集合
     *
     * @param date 日期
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/24 8:08
     */
    @Query("select access.accessDate from SiteAccess as access where access.accessDate<:date")
    List<Date> findSiteAccessDateBefore(@Param("date") Date date);

    /**
     * 根据日期统计所有
     *
     * @param date 日期
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/24 8:22
     */
    @Query(value = "select count(DISTINCT access_ip) as ip,count(DISTINCT session_id) as uv,sum(visit_page_count) as pv,sum(visit_second) as st,'all' as value from site_access where access_date=:date", nativeQuery = true)
    List<Map<String, Object>> statisticByAll(@Param("date") Date date);

    /**
     * 根据日期和区域统计
     *
     * @param date 日期
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/24 8:25
     */
    @Query(value = "select count(DISTINCT access_ip) as ip,count(DISTINCT session_id) as uv,sum(visit_page_count) as pv,sum(visit_second) as st,access_area as value from site_access where access_country='中国' and LENGTH(access_area)>0 and access_date=:date GROUP BY access_area", nativeQuery = true)
    List<Map<String, Object>> statisticByArea(@Param("date") Date date);

    /**
     * 根据日期和访问来源统计
     *
     * @param date 日期
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/24 8:25
     */
    @Query(value = "select count(DISTINCT access_ip) as ip,count(DISTINCT session_id) as uv,sum(visit_page_count) as pv,sum(visit_second) as st,access_source as value from site_access where LENGTH(access_source)>0 and access_date=:date GROUP BY access_source", nativeQuery = true)
    List<Map<String, Object>> statisticBySources(@Param("date") Date date);

    /**
     * 根据日期和不同的访问来源统计
     *
     * @param accessSource 来源
     * @param date         日期
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/24 8:25
     */
    @Query(value = "select count(DISTINCT access_ip) as ip,count(DISTINCT session_id) as uv,sum(visit_page_count) as pv,sum(visit_second) as st,access_value as value from site_access where access_source=:accessSource and access_date=:date GROUP BY access_value", nativeQuery = true)
    List<Map<String, Object>> statisticBySource(@Param("date") Date date, @Param("accessSource") String accessSource);

    /**
     * 根据小时段统计
     *
     * @param date 日期
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/24 9:42
     */
    @Query(value = "select count(DISTINCT access_ip) as ip,count(DISTINCT session_id) as uv,sum(visit_page_count) as pv,HOUR(access_time) as value from site_access where access_date=:date GROUP BY value", nativeQuery = true)
    List<Map<String, Object>> statisticByHour(@Param("date") Date date);

    /**
     * 清空指定日期的站点访问记录
     *
     * @param
     * @param date 日期
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/24 10:00
     */
    @Modifying
    @Query("delete from SiteAccess where accessDate=:date")
    int removeSiteAccessByAccessDate(@Param("date") Date date);

    /**
     * 统计当天的时实数据
     *
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/24 12:43
     */
    @Query(value = "select count(DISTINCT access_ip) as ip,count(DISTINCT session_id) as uv,sum(visit_page_count) as pv,DATE_FORMAT(access_time,'%H:00:-%H:59') as time,access_date as value from site_access where DATE_FORMAT(access_date,'%Y-%m-%d')=DATE_FORMAT(now(),'%Y-%m-%d') GROUP BY time order by access_time asc", nativeQuery = true)
    List<Map<String, Object>> statisticAccessToday();

    /**
     * 统计今天的所有访问来源
     *
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/25 12:36
     */
    @Query(value = "select count(DISTINCT access_ip) as ip,count(DISTINCT session_id) as uv,sum(visit_page_count) as pv,DATE_FORMAT(access_time,'%H:00:-%H:59') as time,access_source as value from site_access where DATE_FORMAT(access_date,'%Y-%m-%d')=DATE_FORMAT(now(),'%Y-%m-%d') GROUP BY access_source,time order by access_time asc;", nativeQuery = true)
    List<Map<String, Object>> statisticAccessBySourcesToday();


    /**
     * 统计今天的某一个访问来源
     *
     * @param type 类型
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/25 12:36
     */
    @Query(value = "select count(DISTINCT access_ip) as ip,count(DISTINCT session_id) as uv,sum(visit_page_count) as pv,DATE_FORMAT(access_time,'%H:00:-%H:59') as time,access_value as value from site_access where access_source=:type and DATE_FORMAT(access_date,'%Y-%m-%d')=DATE_FORMAT(now(),'%Y-%m-%d') GROUP BY access_value,time order by access_time asc;", nativeQuery = true)
    List<Map<String, Object>> statisticAccessBySourceToday(@Param("type") String type);

    /**
     * 统计今日不同浏览器访问的数据
     *
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/25 18:39
     */
    @Query(value = "select count(DISTINCT access_ip) as ip,count(DISTINCT session_id) as uv,sum(visit_page_count) as pv,DATE_FORMAT(access_time,'%H:00:-%H:59') as time,browser_name as value from site_access where DATE_FORMAT(access_date,'%Y-%m-%d')=DATE_FORMAT(now(),'%Y-%m-%d') GROUP BY browser_name,time order by access_time asc;", nativeQuery = true)
    List<Map<String, Object>> statisticAccessByBrowserToday();

    /**
     * 统计今日来访地区的数据
     *
     * @return Map对象
     * @author andy_hulibo@163.com
     * @date 2019/7/25 18:46
     */
    @Query(value = "select count(DISTINCT access_ip) as ip,count(DISTINCT session_id) as uv,sum(visit_page_count) as pv,access_country as time,access_area as value from site_access where access_country='中国' and DATE_FORMAT(access_date,'%Y-%m-%d')=DATE_FORMAT(now(),'%Y-%m-%d') GROUP BY access_area order by uv desc;", nativeQuery = true)
    List<Map<String, Object>> statisticAccessByAreaToday();
}

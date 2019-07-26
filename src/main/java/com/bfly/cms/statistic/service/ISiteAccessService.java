package com.bfly.cms.statistic.service;

import com.bfly.cms.statistic.entity.SiteAccess;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.enums.SiteAccessSource;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 站点访问
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/23 18:04
 */
public interface ISiteAccessService extends IBaseService<SiteAccess, Integer> {

    /**
     * 根据SessionID查找会话记录
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 18:09
     */
    SiteAccess findSiteAccessBySessionId(String sessionId);

    /**
     * 分析数据并写入数据库该方法是定时任务每天凌晨时间调用
     * 根据不同唯独统计数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 8:04
     */
    void statisticToDb();

    /**
     * 把缓存中的数据写入数据库 定时任务每隔一段时间调用
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 10:08
     */
    void cacheToDb();

    /**
     * 清空指定日期的站点访问记录
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 9:58
     */
    void clearSiteAccessByDate(Date date);


    /**
     * 统计当天的时实流量数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 12:42
     */
    List<Map<String, Object>> statisticAccessFlowToday();

    /**
     * 统计当天的访问来源数据汇总
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/25 12:54
     */
    List<Map<String, Object>> statisticAccessBySourcesToday();

    /**
     * 统计今天的某一个访问来源数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/25 18:30
     */
    List<Map<String, Object>> statisticAccessBySourceToday(SiteAccessSource source);

    /**
     * 统计今天的浏览器访问数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/25 18:51
     */
    List<Map<String, Object>> statisticAccessByBrowserToday();

    /**
     * 统计今天的来访区域数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/25 18:52
     */
    List<Map<String, Object>> statisticAccessByAreaToday();
}

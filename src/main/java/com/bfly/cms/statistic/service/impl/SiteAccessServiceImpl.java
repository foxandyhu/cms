package com.bfly.cms.statistic.service.impl;

import com.bfly.cms.statistic.dao.ISiteAccessDao;
import com.bfly.cms.statistic.entity.SiteAccess;
import com.bfly.cms.statistic.entity.SiteAccessPage;
import com.bfly.cms.statistic.entity.SiteAccessStatistic;
import com.bfly.cms.statistic.entity.SiteAccessStatisticHour;
import com.bfly.cms.statistic.service.ISiteAccessPageService;
import com.bfly.cms.statistic.service.ISiteAccessService;
import com.bfly.cms.statistic.service.ISiteAccessStatisticHourService;
import com.bfly.cms.statistic.service.ISiteAccessStatisticService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.cache.EhCacheUtil;
import com.bfly.core.enums.SiteAccessSource;
import com.bfly.core.enums.StatisticType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author andy_hulibo@163.com
 * @date 2019/7/23 17:31
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SiteAccessServiceImpl extends BaseServiceImpl<SiteAccess, Integer> implements ISiteAccessService {

    @Autowired
    private ISiteAccessDao siteAccessDao;
    @Autowired
    private ISiteAccessStatisticService statisticService;
    @Autowired
    private ISiteAccessStatisticHourService statisticHourService;
    @Autowired
    private ISiteAccessPageService pageService;


    @Override
    public SiteAccess findSiteAccessBySessionId(String sessionId) {
        return siteAccessDao.findSiteAccessBySessionId(sessionId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cacheToDb() {
        EhCacheCache siteCache = (EhCacheCache) EhCacheUtil.getCache(EhCacheUtil.SITE_ACCESS_CACHE);
        EhCacheCache pageCache = (EhCacheCache) EhCacheUtil.getCache(EhCacheUtil.SITE_ACCESS_PAGE_CACHE);

        List<String> siteKeys = siteCache.getNativeCache().getKeys();

        //把缓存数据写入数据库
        Stream<String> siteStream = siteKeys.stream();
        siteStream.forEach(key -> {
            SiteAccess siteAccess = siteCache.get(key, SiteAccess.class);
            if (siteAccess.getId() == 0) {
                save(siteAccess);
            } else {
                edit(siteAccess);
            }
        });

        List<String> pageKeys = pageCache.getNativeCache().getKeys();
        Stream<String> pageStream = pageKeys.stream();
        pageStream.forEach(key -> {
            SiteAccessPage accessPage = pageCache.get(key, SiteAccessPage.class);
            if (accessPage.getId() == 0) {
                pageService.save(accessPage);
            } else {
                pageService.edit(accessPage);
            }
        });

        //清除缓存
        siteStream.forEach(key -> siteCache.evict(key));
        pageStream.forEach(key -> pageCache.evict(key));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void statisticToDb() {
        List<Date> days = siteAccessDao.findSiteAccessDateBefore(new Date());
        for (Date day : days) {
            for (StatisticType type : StatisticType.values()) {
                statisticByType(day, type);
            }
            statisticByHour(day);

            //清空已统计完的历史数据
            clearSiteAccessByDate(day);
            pageService.clearSiteAccessPageByDate(day);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearSiteAccessByDate(Date date) {
        siteAccessDao.removeSiteAccessByAccessDate(date);
    }

    /**
     * 根据指定日期的小时段统计
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 9:41
     */
    private void statisticByHour(Date day) {
        List<Map<String, Object>> result = siteAccessDao.statisticByHour(day);
        for (Map<String, Object> item : result) {
            long ip = (long) item.get("ip");
            long uv = (long) item.get("uv");
            long pv = (long) item.get("pv");
            int value = (int) item.get("value");

            SiteAccessStatisticHour statisticHour = new SiteAccessStatisticHour();
            statisticHour.setAccessDate(day);
            statisticHour.setAccessHour(value);
            statisticHour.setHourIp(ip);
            statisticHour.setHourPv(pv);
            statisticHour.setHourUv(uv);
            statisticHourService.save(statisticHour);
        }
    }

    /**
     * 根据不同维度统计
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 8:12
     */
    private void statisticByType(Date day, StatisticType type) {
        List<Map<String, Object>> result;
        switch (type) {
            case ALL:
                result = siteAccessDao.statisticByAll(day);
                break;
            case AREA:
                result = siteAccessDao.statisticByArea(day);
                break;
            case SOURCE:
                result = siteAccessDao.statisticBySources(day);
                break;
            case ENGINE:
                result = siteAccessDao.statisticBySource(day, SiteAccessSource.ENGINE.getName());
                break;
            case LINK:
                result = siteAccessDao.statisticBySource(day, SiteAccessSource.EXTERNAL.getName());
                break;
            case BROWSER:
                result = siteAccessDao.statisticBySource(day, StatisticType.BROWSER.getName());
                break;
            default:
                throw new RuntimeException("未指定统计类型!");
        }

        for (Map<String, Object> item : result) {
            long ip = (long) item.get("ip");
            long uv = (long) item.get("uv");
            long pv = (long) item.get("pv");
            long st = (long) item.get("st");
            String value = String.valueOf(item.get("value"));

            long pagesAvg = pv / uv;
            long vistSecondAvg = st / uv;

            SiteAccessStatistic statistic = new SiteAccessStatistic();
            statistic.setIp(ip);
            statistic.setPagesAvg(pagesAvg);
            statistic.setPv(pv);
            statistic.setStatisitcKey(type.getName());
            statistic.setStatisticDate(day);
            statistic.setStatisticValue(value);
            statistic.setVisitors(uv);
            statistic.setVisitSecondAvg(vistSecondAvg);
            statisticService.save(statistic);
        }
    }

    @Override
    public List<Map<String, Object>> statisticAccessFlowToday() {
        return siteAccessDao.statisticAccessToday();
    }

    @Override
    public List<Map<String, Object>> statisticAccessBySourcesToday() {
        return siteAccessDao.statisticAccessBySourcesToday();
    }

    @Override
    public List<Map<String, Object>> statisticAccessBySourceToday(SiteAccessSource source) {
        return siteAccessDao.statisticAccessBySourceToday(source.getName());
    }

    @Override
    public List<Map<String, Object>> statisticAccessByBrowserToday() {
        return siteAccessDao.statisticAccessByBrowserToday();
    }

    @Override
    public List<Map<String, Object>> statisticAccessByAreaToday() {
        return siteAccessDao.statisticAccessByAreaToday();
    }
}

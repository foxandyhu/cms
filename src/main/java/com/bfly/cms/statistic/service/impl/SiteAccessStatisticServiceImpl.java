package com.bfly.cms.statistic.service.impl;

import com.bfly.cms.statistic.dao.ISiteAccessDao;
import com.bfly.cms.statistic.dao.ISiteAccessPageDao;
import com.bfly.cms.statistic.dao.ISiteAccessStatisticDao;
import com.bfly.cms.statistic.entity.SiteAccessStatistic;
import com.bfly.cms.statistic.entity.SiteAccessStatisticHour;
import com.bfly.cms.statistic.entity.StatisticDataDTO;
import com.bfly.cms.statistic.service.ISiteAccessStatisticHourService;
import com.bfly.cms.statistic.service.ISiteAccessStatisticService;
import com.bfly.common.DateUtil;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.enums.SiteAccessSource;
import com.bfly.core.enums.StatisticType;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author andy_hulibo@163.com
 * @date 2019/7/24 9:32
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SiteAccessStatisticServiceImpl extends BaseServiceImpl<SiteAccessStatistic, Integer> implements ISiteAccessStatisticService {

    @Autowired
    private ISiteAccessStatisticHourService hourService;
    @Autowired
    private ISiteAccessStatisticDao statisticDao;
    @Autowired
    private ISiteAccessDao siteAccessDao;
    @Autowired
    private ISiteAccessPageDao accessPageDao;
    @Autowired
    private ISiteAccessStatisticHourService statisticHourService;

    @Override
    public List<StatisticDataDTO> statistic(Date begin, Date end) {
        Calendar beginTime = Calendar.getInstance();
        beginTime.setTime(begin);
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(end);
        Calendar today = Calendar.getInstance();

        List<StatisticDataDTO> statisticList;
        if (DateUtils.isSameDay(beginTime, endTime)) {
            //当天
            if (DateUtils.isSameDay(beginTime, today)) {
                return statisticToday(StatisticType.ALL);
            }
            //具体的某一天
            List<Map<String, Object>> hours = hourService.getSiteAccessStatisticHourByDate(begin);
            statisticList = convertToJson(hours);
            return statisticList;
        }
        statisticList = statistic(begin, end, StatisticType.ALL);
        return statisticList;
    }

    @Override
    public List<StatisticDataDTO> statistic(Date begin, Date end, StatisticType type) {
        List<Map<String, Object>> statisticList;
        //当天
        if (DateUtils.isSameDay(begin, begin) && DateUtils.isSameDay(begin, new Date())) {
            return statisticToday(type);
        }
        //年度统计
        if (DateUtils.isSameDay(begin, DateUtil.getCurrentFirstDayOfYear()) && DateUtils.isSameDay(end, DateUtil.getCurrentLastDayOfYear())) {
            statisticList = statisticDao.getSiteAccessStatisticByYearAndType(begin, end, type.getName());
        } else {
            //其他时间段统计
            statisticList = statisticDao.getSiteAccessStatisticByDateAndType(begin, end, type.getName());
        }
        List<StatisticDataDTO> list = convertToJson(statisticList);
        return list;
    }

    /**
     * 统计今天的时实数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/25 13:12
     */
    private List<StatisticDataDTO> statisticToday(StatisticType type) {
        List<Map<String, Object>> list;
        switch (type) {
            case ALL:
                list = siteAccessDao.statisticAccessToday();
                return convertToJson(list);
            case SOURCE:
                list = siteAccessDao.statisticAccessBySourcesToday();
                return convertToJson(list);
            case ENGINE:
                list = siteAccessDao.statisticAccessBySourceToday(SiteAccessSource.ENGINE.getName());
                return convertToJson(list);
            case LINK:
                list = siteAccessDao.statisticAccessBySourceToday(SiteAccessSource.EXTERNAL.getName());
                return convertToJson(list);
            case BROWSER:
                list = siteAccessDao.statisticAccessByBrowserToday();
                return convertToJson(list);
            case AREA:
                list = siteAccessDao.statisticAccessByAreaToday();
                return convertToJson(list);
            default:
                throw new RuntimeException("参数错误!");
        }
    }

    /**
     * 转为JSON
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 16:42
     */
    private List<StatisticDataDTO> convertToJson(List<Map<String, Object>> list) {
        List<StatisticDataDTO> statisticList = new ArrayList<>();
        list.forEach(item -> {
            long ip = ((Number) item.get("ip")).longValue();
            long uv = ((Number) item.get("uv")).longValue();
            long pv = ((Number) item.get("pv")).longValue();
            String value = String.valueOf(item.get("value"));
            String time = String.valueOf(item.get("time"));
            StatisticDataDTO data = new StatisticDataDTO();
            data.setIp(ip);
            data.setUv(uv);
            data.setPv(pv);
            data.setTime(time);
            data.setValue(value);
            statisticList.add(data);
        });
        return statisticList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void statisticToDb() {
        List<Date> days = siteAccessDao.findSiteAccessDateBefore(new Date());
        for (Date day : days) {
            for (StatisticType type : StatisticType.values()) {
                statisticByType(day, type);
            }
            statisticByHour(day);

            //清空已统计完的历史数据
            siteAccessDao.removeSiteAccessByAccessDate(day);
            accessPageDao.removeSiteAccessPageByAccessDate(day);
        }
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
            long ip = ((Number) item.get("ip")).longValue();
            long uv = ((Number) item.get("uv")).longValue();
            long pv = ((Number) item.get("pv")).longValue();
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
            long ip = ((Number) item.get("ip")).longValue();
            long uv = ((Number) item.get("uv")).longValue();
            long pv = ((Number) item.get("pv")).longValue();
            long st = ((Number) item.get("st")).longValue();
            String value = String.valueOf(item.get("value"));

            long pagesAvg = pv / uv;
            long visitSecondAvg = st / uv;

            SiteAccessStatistic statistic = new SiteAccessStatistic();
            statistic.setIp(ip);
            statistic.setPagesAvg(pagesAvg);
            statistic.setPv(pv);
            statistic.setStatisticKey(type.getName());
            statistic.setStatisticDate(day);
            statistic.setStatisticValue(value);
            statistic.setVisitors(uv);
            statistic.setVisitSecondAvg(visitSecondAvg);
            save(statistic);
        }
    }
}

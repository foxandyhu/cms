package com.bfly.cms.statistic.service.impl;

import com.bfly.cms.statistic.dao.ISiteAccessStatisticDao;
import com.bfly.cms.statistic.entity.SiteAccessStatistic;
import com.bfly.cms.statistic.entity.StatisticDataDTO;
import com.bfly.cms.statistic.service.ISiteAccessService;
import com.bfly.cms.statistic.service.ISiteAccessStatisticHourService;
import com.bfly.cms.statistic.service.ISiteAccessStatisticService;
import com.bfly.common.DateUtil;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.enums.SiteAccessSource;
import com.bfly.core.enums.StatisticType;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ISiteAccessService accessService;
    @Autowired
    private ISiteAccessStatisticHourService hourService;
    @Autowired
    private ISiteAccessStatisticDao statisticDao;

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
                list = accessService.statisticAccessFlowToday();
                return convertToJson(list);
            case SOURCE:
                list = accessService.statisticAccessBySourcesToday();
                return convertToJson(list);
            case ENGINE:
                list = accessService.statisticAccessBySourceToday(SiteAccessSource.ENGINE);
                return convertToJson(list);
            case LINK:
                list = accessService.statisticAccessBySourceToday(SiteAccessSource.EXTERNAL);
                return convertToJson(list);
            case BROWSER:
                list = accessService.statisticAccessByBrowserToday();
                return convertToJson(list);
            case AREA:
                list = accessService.statisticAccessByAreaToday();
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
}

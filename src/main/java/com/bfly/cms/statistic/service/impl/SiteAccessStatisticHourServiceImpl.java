package com.bfly.cms.statistic.service.impl;

import com.bfly.cms.statistic.dao.ISiteAccessStatisticHourDao;
import com.bfly.cms.statistic.entity.SiteAccessStatisticHour;
import com.bfly.cms.statistic.service.ISiteAccessStatisticHourService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2019/7/24 9:55
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SiteAccessStatisticHourServiceImpl extends BaseServiceImpl<SiteAccessStatisticHour, Integer> implements ISiteAccessStatisticHourService {

    @Autowired
    private ISiteAccessStatisticHourDao statisticHourDao;

    @Override
    public List<Map<String,Object>> getSiteAccessStatisticHourByDate(Date date) {
        return statisticHourDao.getSiteAccessStatisticHourByDate(date);
    }
}

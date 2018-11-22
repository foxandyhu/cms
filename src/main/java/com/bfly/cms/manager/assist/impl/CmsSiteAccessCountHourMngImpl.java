package com.bfly.cms.manager.assist.impl;

import com.bfly.cms.dao.assist.CmsSiteAccessCountHourDao;
import com.bfly.cms.dao.assist.CmsSiteAccessDao;
import com.bfly.cms.entity.assist.CmsSiteAccessCountHour;
import com.bfly.cms.manager.assist.CmsSiteAccessCountHourMng;
import com.bfly.common.page.Pagination;
import com.bfly.core.entity.CmsSite;
import com.bfly.core.manager.CmsSiteMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CmsSiteAccessCountHourMngImpl implements CmsSiteAccessCountHourMng {
    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = dao.getPage(pageNo, pageSize);
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CmsSiteAccessCountHour> getList(Date date) {
        return dao.getList(date);
    }

    @Override
    public void statisticCount(Date date, Integer siteId) {
        List<Object[]> statisTicData = cmsAccessDao.statisticByDayGroupByHour(date, siteId);
        CmsSite site = siteMng.findById(siteId);
        for (Object[] d : statisTicData) {
            CmsSiteAccessCountHour bean = new CmsSiteAccessCountHour();
            bean.setSite(site);
            bean.setAccessDate(date);
            Long pv = (Long) d[0];
            Long ip = (Long) d[1];
            Long visitor = (Long) d[2];
            Integer hour = (Integer) d[3];
            bean.setHourUv(visitor);
            bean.setHourPv(pv);
            bean.setHourIp(ip);
            bean.setAccessHour(hour);
            save(bean);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CmsSiteAccessCountHour findById(Integer id) {
        CmsSiteAccessCountHour entity = dao.findById(id);
        return entity;
    }

    @Override
    public CmsSiteAccessCountHour save(CmsSiteAccessCountHour bean) {
        dao.save(bean);
        return bean;
    }

    @Autowired
    private CmsSiteAccessCountHourDao dao;
    @Autowired
    private CmsSiteAccessDao cmsAccessDao;
    @Autowired
    private CmsSiteMng siteMng;
}
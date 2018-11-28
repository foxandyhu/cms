package com.bfly.cms.statistic.dao;

import com.bfly.cms.statistic.entity.CmsSiteAccessCountHour;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.Date;
import java.util.List;

public interface CmsSiteAccessCountHourDao {
    Pagination getPage(int pageNo, int pageSize);

    List<CmsSiteAccessCountHour> getList(Date date);

    CmsSiteAccessCountHour findById(Integer id);

    CmsSiteAccessCountHour save(CmsSiteAccessCountHour bean);

    CmsSiteAccessCountHour updateByUpdater(Updater<CmsSiteAccessCountHour> updater);

    CmsSiteAccessCountHour deleteById(Integer id);
}
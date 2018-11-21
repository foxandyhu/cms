package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsSiteAccessCount;
import com.bfly.common.hibernate4.Updater;

import java.util.Date;
import java.util.List;

/**
 * @author Tom
 */
public interface CmsSiteAccessCountDao {

    List<Object[]> statisticVisitorCountByDate(Integer siteId, Date begin, Date end);

    List<Object[]> statisticVisitorCountByYear(Integer siteId, Integer year);

    CmsSiteAccessCount save(CmsSiteAccessCount count);

    CmsSiteAccessCount updateByUpdater(Updater<CmsSiteAccessCount> updater);

}

package com.bfly.cms.statistic.service;

import java.util.Date;
import java.util.List;

import com.bfly.cms.statistic.entity.CmsSiteAccessCount;

/**
 * @author Tom
 */
public interface CmsSiteAccessCountMng {
	 List<Object[]> statisticVisitorCountByDate(Integer siteId,Date begin, Date end);

	 List<Object[]> statisticVisitorCountByYear(Integer siteId,Integer year);

	 CmsSiteAccessCount save(CmsSiteAccessCount count);

	 void statisticCount(Date date, Integer siteId);
}

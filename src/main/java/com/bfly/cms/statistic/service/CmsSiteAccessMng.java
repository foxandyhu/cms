package com.bfly.cms.statistic.service;

import java.util.Date;
import java.util.List;

import com.bfly.cms.statistic.entity.CmsSiteAccess;
import com.bfly.common.page.Pagination;

/**
 * @author Tom
 */
public interface CmsSiteAccessMng {
	 CmsSiteAccess saveOrUpdate(CmsSiteAccess access);
	
	 Pagination findEnterPages(Integer siteId,Integer orderBy,Integer pageNo,Integer pageSize);

	 CmsSiteAccess findAccessBySessionId(String sessionId);

	 /**
	 * 查询date之前最近的访问记录
	 * @param date
	 * @return
	 */
	 CmsSiteAccess findRecentAccess(Date  date,Integer siteId);
	
	/**
	 * 统计站点流量数据
	 * @param property
	 * @param date
	 * @param siteId
	 */
	 void statisticByProperty(String property,Date date,Integer siteId);
	
	 List<String> findPropertyValues(String property,Integer siteId);
	
	 List<Object[]> statisticToday(Integer siteId,String area);
	
	 List<Object[]> statisticVisitorCount(Date date,Integer siteId);
	
	 List<Object[]> statisticTodayByTarget(Integer siteId,Integer target,String statisticColumn,String statisticValue);

	 void clearByDate(Date date);
}

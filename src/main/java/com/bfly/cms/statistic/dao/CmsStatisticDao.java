package com.bfly.cms.statistic.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bfly.cms.statistic.entity.CmsStatistic.TimeRange;

public interface CmsStatisticDao {
	 long memberStatistic(TimeRange timeRange);
	
	 List<Object[]> statisticMemberByTarget(Integer target,
			Date timeBegin,Date timeEnd);

	 long contentStatistic(TimeRange timeRange,
			Map<String, Object> restrictions);
	
	 List<Object[]> statisticContentByTarget(Integer target,
			Date timeBegin,Date timeEnd,Map<String, Object> restrictions);
	
	 List<Object[]> statisticCommentByTarget(
			Integer target,Integer siteId,Boolean isReplyed,Date timeBegin,Date timeEnd);


	 List<Object[]> statisticGuestbookByTarget(Integer target,Integer siteId,
			 Boolean isReplyed,Date timeBegin,Date timeEnd);

	 long commentStatistic(TimeRange timeRange,
			Map<String, Object> restrictions);

	 long guestbookStatistic(TimeRange timeRange,
			Map<String, Object> restrictions);
	
	
}

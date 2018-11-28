package com.bfly.cms.statistic.dao;

import java.util.Date;
import java.util.List;

import com.bfly.cms.statistic.entity.CmsWorkLoadStatistic.CmsWorkLoadStatisticDateKind;

public interface CmsWorkLoadStatisticDao {
	
	 Long  statistic(Integer channelId,
			Integer reviewerId, Integer authorId,
			Date beginDate, Date endDate,
			CmsWorkLoadStatisticDateKind dateKind);
	
	 List<Object[]> statisticByTarget(Integer target,
			Integer channelId,Integer reviewerId, 
			Integer authorId, Date beginDate, Date endDate);
}

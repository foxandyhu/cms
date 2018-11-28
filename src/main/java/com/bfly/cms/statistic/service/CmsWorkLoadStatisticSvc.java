package com.bfly.cms.statistic.service;

import java.util.Date;
import java.util.List;

import com.bfly.cms.statistic.entity.CmsWorkLoadStatistic;
import com.bfly.cms.statistic.entity.CmsWorkLoadStatistic.CmsWorkLoadStatisticDateKind;
import com.bfly.cms.statistic.entity.CmsWorkLoadStatistic.CmsWorkLoadStatisticGroup;

public interface CmsWorkLoadStatisticSvc {
	 List<CmsWorkLoadStatistic> statistic(Integer channelId,
										  Integer reviewerId, Integer authorId, Date beginDate, Date endDate,
										  CmsWorkLoadStatisticGroup group, CmsWorkLoadStatisticDateKind kind);
	
	 List<Object[]> statisticByTarget(Integer target,
			Integer channelId,Integer reviewerId, 
			Integer authorId, Date beginDate, Date endDate);
}

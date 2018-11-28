package com.bfly.cms.statistic.service;

import com.bfly.common.page.Pagination;

import java.util.Date;
import java.util.List;

import com.bfly.cms.statistic.entity.CmsSiteAccessCountHour;

public interface CmsSiteAccessCountHourMng {
	 Pagination getPage(int pageNo, int pageSize);
	
	 List<CmsSiteAccessCountHour> getList(Date date);
	
	 void statisticCount(Date date, Integer siteId);

	 CmsSiteAccessCountHour findById(Integer id);

	 CmsSiteAccessCountHour save(CmsSiteAccessCountHour bean);
}
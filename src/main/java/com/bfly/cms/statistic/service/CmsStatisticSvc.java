package com.bfly.cms.statistic.service;

import com.bfly.cms.statistic.entity.CmsStatistic.TimeRange;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CmsStatisticSvc {

    long statistic(int type, TimeRange timeRange,
                   Map<String, Object> restrictions);

    List<Object[]> statisticMemberByTarget(Integer target,
                                           Date timeBegin, Date timeEnd);

    List<Object[]> statisticContentByTarget(Integer target,
                                            Date timeBegin, Date timeEnd, Map<String, Object> restrictions);

    List<Object[]> statisticCommentByTarget(Integer target, Integer siteId,
                                            Boolean isReplyed, Date timeBegin, Date timeEnd);

    List<Object[]> statisticGuestbookByTarget(Integer target, Integer siteId,
                                              Boolean isReplyed, Date timeBegin, Date timeEnd);

}

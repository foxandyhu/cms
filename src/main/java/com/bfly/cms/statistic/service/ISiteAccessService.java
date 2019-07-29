package com.bfly.cms.statistic.service;

import com.bfly.cms.statistic.entity.SiteAccess;
import com.bfly.core.base.service.IBaseService;

/**
 * 站点访问
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/23 18:04
 */
public interface ISiteAccessService extends IBaseService<SiteAccess, Integer> {

    /**
     * 根据SessionID查找会话记录
     *
     * @param sessionId SessionId
     * @return 访问记录
     * @author andy_hulibo@163.com
     * @date 2019/7/23 18:09
     */
    SiteAccess findSiteAccessBySessionId(String sessionId);

    /**
     * 把缓存中的数据写入数据库 定时任务每隔一段时间调用
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 10:08
     */
    void cacheToDb();
}
